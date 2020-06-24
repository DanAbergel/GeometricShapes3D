package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * @author Dan Abergel and Joss Lalou
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    public Sphere(Color emissionLight, Material material, double radius, Point3D center) {
        super(emissionLight, radius);
        this.material = material;
        this._center = center;
    }

    public Sphere(Color emission, double _radius, Point3D _center) {
        this(emission, new Material(0, 0, 0), _radius, _center);
    }

    public Sphere(double radius, Point3D center) {

        this(Color.BLACK, new Material(0, 0, 0), radius, center);
    }

    @Override
    public Vector getNormal(Point3D point) {

        Vector orthogonal = point.subtract(_center);
        return orthogonal.normalized();
    }

    public Point3D get_center() {

        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = _center.subtract(p0); // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getPoint(get_radius())));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(get_radius() * get_radius() - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        double t1MaxDistance = alignZero(max - t1);
        double t2MaxDistance = alignZero(max - t2);

        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
        if (t1 > 0 && t1MaxDistance > 0)//if t1 and distance of t1 positive
            geoPoints.add(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0 && t2MaxDistance > 0)//if t1 and distance of t2 positive
            geoPoints.add(new GeoPoint(this, ray.getPoint(t2)));

        //return geo points if exists or null otherwise
        return geoPoints.size() > 0 ? geoPoints : null;
    }


}

package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * @author Dan Abergel and Joss Lalou
 * class Sphere herit from RadialGeometry class because each sphere has a radius
 * In addition to the radius , Sphere class has also a Point3D - center of the sphere
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * Constructor of Sphere with four parameters: Color, Material, radius and Point3D center <br/>
     * parameters Color, Material and radius are transferred to Constructor of RadialGeometry
     * @param emission is the color of the sphere
     * @param material is the material of the sphere
     * @param radius is the radius of the sphere
     * @param center is the Point3D center of the sphere
     * */
    public Sphere(Color emission, Material material, double radius, Point3D center) {
        super(emission,material, radius);
        this._center = center;
    }

    /**
     * Constructor of Sphere with three parameters: Color, radius and Point3D center<br/>
     * Per default for Material it takes new Material(0,0,0)
     * @param emission is color of the sphere
     * @param radius is the radius of the sphere
     * @param center is the center of the sphere
     * */
    public Sphere(Color emission, double radius, Point3D center) {
        this(emission, new Material(0, 0, 0), radius, center);
    }

    /**
     * Constructor of Sphere with two parameters: radius and Point3D center<br/>
     * Per default for Material it takes new Material(0,0,0)
     * Per default for Color it takes color Black
     * @param radius is the radius of the sphere
     * @param center is the center of the sphere
     * */
    public Sphere(double radius, Point3D center) {
        this(Color.BLACK, new Material(0, 0, 0), radius, center);
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

    /**
     * Function getNormal is override the function which already exists in Geometry class
     * It calculates the normal to the sphere at the given point
     * @param point is the point which is the intersection between the norml and the sphere
     * @return the normal vector to the sphere which the given point is intersection of the sphere and this normal
     * */
    @Override
    public Vector getNormal(Point3D point) {
        Vector orthogonal = point.subtract(_center);
        return orthogonal.normalized();
    }

    /**
     * Function findIntersections implements the function which already exists in Intersectable interface
     * It calculates all points of intersection between a given ray and the actual instance of sphere
     * @param ray is the given ray which may cross the sphere
     * @param max is the max distance between the light and the sphere
     * */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getDirection();
        Vector u;
        try {
            //if the vector will be ZERO throw an exception
            //this will happen only if the center of the sphere will be at point(0,0,0)
            // and the start point of the ray is exactly equals to center point
            u = _center.subtract(p0);
        } catch (IllegalArgumentException e) {
            //if throws exception it's means that there is only one point intersection
            //and it is in the continuation of the ray at end of radius
            //and then it must return only this point
            return List.of(new GeoPoint(this, ray.getPoint(get_radius())));
        }

        //tm is the distance between the first intersection point and the mid of vector between the two intersection points
        double tm = alignZero(v.dotProduct(u));

        //dSquared is the distance between center of the sphere and the given ray in a perpendicular way
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;

        //thSquared is
        double thSquared = alignZero(get_radius() * get_radius() - dSquared);

        if (thSquared <= 0) return null;
        double th = alignZero(Math.sqrt(thSquared));

        //t1 and t2 may be the two intersection points
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        //if t1MaxDistance is negative it means that the point of intersection t1 is behind the camera
        double t1MaxDistance = alignZero(max - t1);
        //if t2MaxDistance is negative it means that the point of intersection t2 is behind the camera
        double t2MaxDistance = alignZero(max - t2);

        //initialize a new list for the intersection points
        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

        if (t1 > 0 && t1MaxDistance > 0)//if t1 and distance of t1 positive
            geoPoints.add(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0 && t2MaxDistance > 0)//if t1 and distance of t2 positive
            geoPoints.add(new GeoPoint(this, ray.getPoint(t2)));

        //return geo points if exists or null otherwise
        return geoPoints.size() > 0 ? geoPoints : null;
    }


}

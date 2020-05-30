package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.alignZero;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public class Sphere extends RadialGeometry {
    Point3D _center;
    public Sphere(Color emission, Material material, double _radius, Point3D _center) {
        super(emission, material, _radius);
        this._center = _center;
    }
    public Sphere(Color emission, double _radius, Point3D _center){
        this(emission,new Material(0,0,0),_radius,_center);
    }
    public Sphere(double _radius, Point3D _center) {
        this(Color.BLACK,new Material(0,0,0),_radius,_center);
    }

    @Override
    public Vector getNormal(Point3D point){

        Vector orthogonal = new Vector(point.subtract(_center));
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
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector L=_center.subtract(ray.getPoint());
        Vector V=ray.getVector();
        double tm=(L.dotProduct(V));
        double d=Math.sqrt(Math.pow(L.getLenght(),2) - Math.pow(tm,2));
        if (d>this.get_radius())
            return null;
        double th=Math.sqrt(Math.pow(this.get_radius(),2) - Math.pow(d,2));
        double t1=alignZero(tm-th);
        double t2=alignZero(tm+th);
        Point3D point1=ray.getTargetPoint(t1);
        Point3D point2=ray.getTargetPoint(t2);
        if (t1>0 && t2>0 ){
            if (ray.getPoint()!=point1 && ray.getPoint()!=point2)
                return List.of(new GeoPoint(this,point1),new GeoPoint(this,point2));
            else
                return null;
        }
        if (t1<=0&&t2<=0)
            return null ;
        if (t1 > 0 )
            if (ray.getPoint()!=point1)
                 return List.of(new GeoPoint(this,(ray.getTargetPoint(t1))));
            else
                return null;
        else
            if (ray.getPoint()!=point2)
                return List.of(new GeoPoint(this,ray.getTargetPoint(t2)));
            else
                return null;
    }
}

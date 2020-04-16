package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;
import java.util.List;
import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    Point3D _center;
    public Sphere(Point3D point, double rayon)
    {
        super(rayon);
        _center=new Point3D(point);
    }
    @Override
    public Vector getNormal(Point3D point){
        return point.subtract(_center).normalize();
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
    public List<Point3D> findIntsersections(Ray ray) {
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
        if (t1>0 && t2>0 )
            if (ray.getPoint()!=point1 && ray.getPoint()!=point2)
                return List.of(point1,point2);
            else
                return null;
        if (t1<=0&&t2<=0)
            return null ;
        if (t1 > 0 )
            if (ray.getPoint()!=point1)
                 return List.of(ray.getTargetPoint(t1));
            else
                return null;
        else
            if (ray.getPoint()!=point2)
                return List.of(ray.getTargetPoint(t2));
            else
                return null;


    }
}

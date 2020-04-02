package geometries;

import primitives.Point3D;
import primitives.Vector;

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
}

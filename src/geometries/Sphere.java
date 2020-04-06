package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        return super.findIntsersections(ray);
    }
}

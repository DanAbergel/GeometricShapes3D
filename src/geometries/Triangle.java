package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class representing a Triangle which is a polygon but have a restriction for having only three points and thats why is herits from polygon class
 * @author yeoshua and Dan
 */
public class Triangle extends Polygon {
    //Constructor of Triangle with parameters : 3 points Point3D
    public Triangle(Point3D point1,Point3D point2,Point3D point3) {
        super(point1,point2,point3);
    }

    //override method for toString method of triange
    @Override
    public String toString() {
        return "Triangle{}"+super.toString();
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return super.findIntsersections(ray);
    }
}

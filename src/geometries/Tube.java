package geometries;

import primitives.*;

import java.util.List;


/**
 * class representing a vector on a Tube
 * @author yeoshua and Dan
 */


public class Tube extends RadialGeometry {
    Ray _axisRay;

    /**
     * builds tube out of a ray
     * @param _axisRay ray
     */


    //Constructor of class Tube with parameters of double and Ray
    public Tube(Color emission, Material material, double _radius, Ray _axisRay) {
        super(emission, material, _radius);
        this._axisRay = _axisRay;
    }
    public Tube(Color emission, double _radius, Ray _ray) {
        this(emission, new Material(0,0,0), _radius, _ray);
    }

    //function which return the ray _axisRay
    public Ray get_axisRay() {
        return _axisRay;
    }


    //override method toString of Tube class
    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay + "_center"+super.toString()+
                '}';
    }
    @Override
    public Vector getNormal(Point3D point)
    {
        //The vector from the point of the cylinder to the given point
        Point3D point1 = _axisRay.getPoint();
        Vector vector1 = _axisRay.getVector();
        Vector vector2 = point.subtract(point1);

        //We need the projection to multiply the _direction unit vector
        double projection = vector2.dotProduct(vector1);
        if(!Util.isZero(projection))
        {
            // projection of P-O on the ray:
            point1.add(vector1.Scale(projection));
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = point.subtract(point1);
        return check.normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray)
    {
        return null;
    }
}

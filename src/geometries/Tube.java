package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


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


    //function getNormal which return a vector perpendicular to given point
    @Override
    public Vector getNormal(Point3D pt) {
        return null;
    }

    //Constructor of class Tube with parameters of double and Ray
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay =new Ray(_axisRay);
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
}

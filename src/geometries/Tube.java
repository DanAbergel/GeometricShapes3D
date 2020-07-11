package geometries;

import primitives.*;

import java.util.List;
/**
 * @author yeoshua and Dan
 * Class Tube represents a Tube in 3D model
 * Because it has a radius this class herits from RadialGeometry as Sphere
 */

public class Tube extends RadialGeometry {
    Ray _axisRay;

    /**
     * Constructor Tube with parameters Color,Material,radius and a Ray
     * @param emission is the color of the Tube
     * @param material is the material of the Tube
     * @param _radius is the radius of the Tube
     * @param _axisRay is the ray which determine the direction of the Tube in the 3D model
     * */
    public Tube(Color emission, Material material, double _radius, Ray _axisRay) {
        super(emission, material, _radius);
        this._axisRay = _axisRay;
    }

    /**
     * Constructor Tube with parameters Color,radius and a ray
     * Per default it takes a new Material(0,0,0)
     * @param emission is the color of the Tube
     * @param radius is the radius of the Tube
     * @param axisRay is the ray which determine the direction of the Tube in the 3D model
     * */
    public Tube(Color emission, double radius, Ray axisRay){
        this(emission,new Material(0,0,0),radius,axisRay);
    }

    /**
     *Constructor Tube with parameters radius and ray
     * @param radius is the radius od the Tube
     * @param ray is the ray which determine the direction of the Tube in 3D model
     *  */
    public Tube(double radius, Ray ray) {
        this(Color.BLACK, new Material(0,0,0), radius, ray);
    }

    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay + "_center"+super.toString()+
                '}';
    }

    /**
     * Function getNormal overrides the function in Geometry class
     * It calculates the rays which is normal to the Tube
     * This normal is perpendicular to the direction of axis ray
     * @param point is the point of intersection between the normal and the Tube
     * @return a vector which is the normal to the Tube
     * */
    @Override
    public Vector getNormal(Point3D point) {

        Point3D p0 = _axisRay.getPoint();
        Vector v = _axisRay.getDirection();
        Vector u = point.subtract(p0);

        //We need the projection to multiply v vector by it for get the point O which is the start point to the normal
        double projection = u.dotProduct(v);

        //only if the projection different than zero multiply v by the projection
        if(!Util.isZero(projection))
        {
            // projection of O-P on the ray:
            p0.add(v.scale(projection));
        }

        //This vector is the normal to the Tube.
        Vector check = point.subtract(p0);
        return check.normalize();
    }

    /**
     * Function findIntersections overrides the function in Intersectable interface
     * It gets the list of all intersection points
     * @param ray is the ray which may cross the Tube
     * @param max is the max distance between the camera and the Tube
     * @return a list of intersection points
     * */
    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max)
    {
        return null;
    }
}

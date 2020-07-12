package geometries;

import primitives.*;

import java.util.List;


/**
 * * @author yeoshua and Dan
 * Class representing a Cylinder which is an extends class of Tube class
 * The difference between Tube and Cylinder is that Tube is an infinite cylinder and because of that Cylinder class has
 * in addition to all attributes of Tube an height
 */


public class Cylinder extends Tube {
    double _height;

    /**
     * Constructor of Cylinder class with five parameters
     * It sends to Tube constructor all parameters except height attribute
     * @param emission is the color of the cylinder
     * @param material is the material of the cylinder
     * @param ray is the axis ray of the cylinder
     * @param radius is the radius of the cylinder
     * @param height is the the height of the finite cylinder
     * */
    public Cylinder(Color emission, Material material, double radius, Ray ray, double height) {
        super(emission, material, radius, ray);
        this._height = height;
    }

    /**
     * Constructor of Cylinder class with five parameters
     * It sends to Tube constructor all parameters except height attribute
     * Per default it sends a new Material(0,0,0)
     * Use DRY principle
     * @param emission is the color of the cylinder
     * @param ray is the axis ray of the cylinder
     * @param radius is the radius of the cylinder
     * @param height is the the height of the finite cylinder
     * */
    public Cylinder(Color emission, double radius, Ray ray, double height){
        this(emission, new Material(0,0,0),radius,ray,height);
    }

    /**
     * Constructor of Cylinder class with five parameters
     * It sends to Tube constructor all parameters except height attribute
     * Per default it sends a new Material(0,0,0) and the color per default is the Black color
     * use DRY principle
     * @param ray is the axis ray of the cylinder
     * @param radius is the radius of the cylinder
     * @param height is the the height of the finite cylinder
     * */
    public Cylinder(double radius, Ray ray, double height){
        this(Color.BLACK,new Material(0,0,0),radius,ray,height);
    }

    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                super.toString()+
                '}';
    }

    /**
     * Function getNormal overrides getNormal in Tube class because there is a difference between both functions
     * In the function in Tube class the normal was perpendicular to axis ray because Tube is infinite and it's impossible to find a
     * normal to the tube in the direction of axis ray
     * In opposite to Tube , Cylinder is finite and we can find a normal to the cylinder which is in the same direction than axis ray
     * */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D o = _axisRay.getPoint();
        Vector v = _axisRay.getDirection();
        // projection of P-O on the ray:
        double t;
        try {
            Vector u=point.subtract(o);
            t = Util.alignZero(u.dotProduct(v));
            //if vector u is Vector ZERO throw exception: P = O
        } catch (IllegalArgumentException e) {
            return v;
        }
        // if the point is at a base
        if (t == 0 || Util.isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;
        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
    }



}

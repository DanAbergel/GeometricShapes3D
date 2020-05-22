package geometries;

import primitives.*;

import java.util.List;


/**
 * class representing a Cylinder which is an extends class of Tube class
 * @author yeoshua and Dan
 */


public class Cylinder extends Tube {
    double _height;
    /**
     * Constructor of Cylinder Class with 3 parameters
     * @param _radius : radius of the cylinder
     * @param _axisRay : ray which the cylinder is based on
     * @param _height : height of the cylinder**/
    public Cylinder(double _radius, Ray _axisRay, double _height) {
       this(Color.BLACK,_radius,_axisRay,_height);
    }
    /**
     * Constructor of Cylinder Class with 4 parameters which also colors the cylinder
     * @param color : color of the cylinder of class
     * @param _radius : radius of the cylinder
     * @param _axisRay : ray which the cylinder is based on
     * @param _height : height of the cylinder**/
    public Cylinder(Color color,double _radius, Ray _axisRay, double _height){
        super(color,_radius,_axisRay);
        this._height=_height;

    }

    /**function which return the height of Cylinder**/
    public double get_height() {
        return _height;
    }

    /**override method of toString for Cylinder class**/
    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                super.toString()+
                '}';
    }

    /**override method for getNormal which return a vector perpendicular to the given point in parameters
     * @param point : the point which we calculate the normal of the cylinder**/
        @Override
        public Vector getNormal(Point3D point) {
            Point3D o = _axisRay.getPoint();
            Vector v = _axisRay.getVector();

            // projection of P-O on the ray:
            double t;
            try {
                t = Util.alignZero(point.subtract(o).dotProduct(v));
            } catch (IllegalArgumentException e) { // P = O
                return v;
            }

            // if the point is at a base
            if (t == 0 || Util.isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
                return v;

            o = o.add(v.Scale(t));
            return point.subtract(o).normalize();
        }


    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}

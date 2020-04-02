package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class representing a Plane which is representated by one point Point3D and one vector Vector
 * @author yeoshua and Dan
 */
public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;

    /**Constructor of Plane class with parameters : 3 points Point3D**/
    public Plane(Point3D point1,Point3D point2,Point3D point3)
    {//02/04
        _p=new Point3D(point1);
        Vector U =new Vector(point1,point2);
        Vector V=new Vector(point1,point3);
        Vector N=U.crossProduct(V);
        N.normalize();
        _normal=N.Scale(-1);
    }
    /**Constructor of Plane class with parameters : one point Point3D and one vector Vector**/
    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal =new Vector( _normal);
    }

    /**override method of function getNormal which return a vector perpendicular to the given point**/
    @Override
    public Vector getNormal(Point3D pt) {
        return _normal;
    }

    //because polygon
    public Vector getNormal() {
        return getNormal(null);
    }

    /**override method of toString function of the Plane class**/
    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }
}

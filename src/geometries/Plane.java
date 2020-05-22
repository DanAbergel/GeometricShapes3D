package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class representing a Plane which is representated by one point Point3D and one vector Vector
 * @author yeoshua and Dan
 */
public class Plane extends Geometry {
    Point3D _p;
    Vector _normal;

    /**Constructor of Plane class with parameters : 3 points Point3D**/
    public Plane(Color color,Point3D point1,Point3D point2,Point3D point3)
    {
        super(color);
        _p=new Point3D(point1);
        Vector U =new Vector(point1,point2);
        Vector V=new Vector(point1,point3);
        Vector N=U.crossProduct(V);
        N.normalize();
        _normal = N;
        _normal = N.Scale(-1);
    }
    public Plane(Point3D point1,Point3D point2,Point3D point3){
        this(Color.BLACK,point1,point2,point3);
    }
    /**Constructor of Plane class with parameters : one point Point3D and one vector Vector**/
    public Plane(Point3D _p, Vector _normal) {
        this(Color.BLACK,_p,_normal);
    }
    /**
     * Constructor of the class Plane with color of the plane**/
    public Plane(Color color, Point3D _p, Vector _normal){
        super(color);
        this._p = new Point3D(_p);
        this._normal =new Vector( _normal);
    }

    /**because polygon needs geNormal without parameter**/
    public Vector getNormal() {
        return getNormal(null);
    }

    /**override method of function getNormal which return a vector perpendicular to the given point**/
    @Override
    public Vector getNormal(Point3D pt)
    {
        return _normal;
    }


    /**override method of toString function of the Plane class**/
    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }
        double nv = _normal.dotProduct(ray.getVector());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;
        double t = alignZero(_normal.dotProduct(p0Q) / nv);
        GeoPoint newPoint=new GeoPoint(this,ray.getTargetPoint(t));
        return t <= 0 || newPoint.point==ray.getPoint() ? null : List.of(newPoint);//if the point of intersection is the same point than the point of ray return null
    }
}

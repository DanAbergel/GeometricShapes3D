package geometries;

import primitives.*;

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
    public Plane(Color emission, Material material,Point3D point1,Point3D point2,Point3D point3) {
        super(emission,material);
        _p=point1;
        Vector U =point1.subtract(point2);
        Vector V=point1.subtract(point3);
        Vector N=U.crossProduct(V);
        N.normalize();
        _normal = N;
    }
    public Plane(Color emission,Point3D point1,Point3D point2,Point3D point3){
        this(emission,new Material(0,0,0),point1,point2,point3);
    }
    public Plane(Point3D point1,Point3D point2,Point3D point3){
        this(Color.BLACK,new Material(0,0,0),point1,point2,point3);
    }
    /**Constructor of Plane class with parameters : one point Point3D and one vector Vector**/
    public Plane(Color color,Material material, Point3D _point, Vector _normal) {
        super(color,material);
        this._p = _point;
        this._normal = _normal.normalize();
    }
    public Plane(Color color, Point3D _point, Vector _normal){

        this(color,new Material(0,0,0),_point,_normal);
    }

    public Plane( Point3D _point, Vector _normal){

        this(Color.BLACK,new Material(0,0,0),_point,_normal);
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

   /*/ @Override
    public List<Point3D> findIntsersections(Ray ray) {
        double t=(_normal.Scale(-1).dotProduct(ray.getPoint().subtract(_p)))/(_normal.dotProduct(ray.getVector()));
        return List.of( ray.getPoint().add(ray.getVector().Scale(t)));
    }/*///de Dan

    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }
        double nv = _normal.dotProduct(ray.getDir());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;
        double t = alignZero(_normal.dotProduct(p0Q) / nv);
        Point3D newPoint=ray.getPoint(t);
        double tMaxDistance=alignZero(t-max);
        return t <= 0 || newPoint==ray.getPoint() || tMaxDistance>0 ? null : List.of(new GeoPoint(this,newPoint));//if the point of intersection is the same point than the point of ray return null
    }
}

package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * @author Dan Abergel and Joss Lalou
 * The class Plane representing a Plane which is representated by one point Point3D and one vector Vector
 * This class herit from Geometry abstract class which herits itself from the Intersectable interface
 * And then have to implement two functions:
 * getNormal which get the normal vector to the plane
 * findIntersections which get intersections points between plane and a given ray
 */
public class Plane extends Geometry {
    Point3D _p;
    Vector _normal;

    /**
     * Constructor of Plane class with parameters : 3 points Point3D
     * @param emission is the color of the plane
     * @param material  is the material of the pane
     * @param point1 first point
     * @param point2 second point
     * @param point3 third point
     * **/
    public Plane(Color emission, Material material,Point3D point1,Point3D point2,Point3D point3) {
        //send color and material to constructor of Geometry class
        super(emission,material);

        //initialize the point of this instance
        _p=point1;

        //create the two vectors for get the normal vector by the cross product between them
        Vector U =point1.subtract(point2);
        Vector V=point1.subtract(point3);

        //calculate the normal vector
        Vector N=U.crossProduct(V);
        N.normalize();

        //initialize the normal of this instance
        _normal = N;
    }

    /**
     * Constructor of Plane with parameters Color and three Point3D
     * per default set a new Material(0,0,0)
     * use Dry principle
     * @param emission is color of plane
     * @param point1 is first point
     * @param point2 is second point
     * @param point3 is third point
     * */
    public Plane(Color emission,Point3D point1,Point3D point2,Point3D point3){
        this(emission,new Material(0,0,0),point1,point2,point3);
    }

    /**
     * Constructor of Plane with three Point3D parameters
     * per default set a new Material(0,0,0) and Color equals per default the Black color
     * use DRY principle
     * @param point1 is first point
     * @param point2 is second point
     * @param point3 is third point
     * */
    public Plane(Point3D point1,Point3D point2,Point3D point3){
        this(Color.BLACK,new Material(0,0,0),point1,point2,point3);
    }

    /**
     * Constructor of Plane with parameters Color ,Material,and three points Point3D
     * It send the parameters Color and Material to Constructor of Geometry
     * @param color is the color of the plane
     * @param material is the material of the plane
     * @param _normal is the normal to this plane
     * @param _point is the point of intersection between the plane and the normal
     * */
    public Plane(Color color,Material material, Point3D _point, Vector _normal) {
        super(color,material);
        this._p = _point;
        this._normal = _normal.normalize();
    }

    /**
     * Constructor of Plane with parameters Color and three points Point3D
     * It send the parameters Color and Material to Constructor of Geometry
     * Per default send a new Material(0,0,0)
     * use DRY principle
     * @param color is the color of the plane
     * @param normal is the normal to this plane
     * @param point is the point of intersection between the plane and the normal
     * */
    public Plane(Color color, Point3D point, Vector normal){
        this(color,new Material(0,0,0),point,normal);
    }

    /**
     * Constructor of Plane with parameters Color and three points Point3D
     * It send the parameters Color and Material to Constructor of Geometry
     * Per default send a new Material(0,0,0) and as Color per default Black color
     * use DRY principle
     * @param normal is the normal to this plane
     * @param point is the point of intersection between the plane and the normal
     * */
    public Plane( Point3D point, Vector normal){
        this(Color.BLACK,new Material(0,0,0),point,normal);
    }

    /**This function is override method of function getNormal which return a vector
     *  perpendicular to the given point
     *  This normal has been already calculated in the Constructor
     *  **/
    @Override
    public Vector getNormal(Point3D pt)
    {
        return _normal;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * Function findIntersections overrides the function of Inteersectable
     * It calculates intersection points between a given ray and this plane
     * @param ray is the given ray which may cross the plane
     * @param max is the max distance between the camera and the plane
     * @return list of intersection points
     * */
    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        Vector p0Q;
        try {
            //if Q is at the same point than p0 which is start point of the ray , subtract operation will result
            //the vector (0,0,0) and then throws an exception
            p0Q = _p.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            // ray starts from point Q - no intersections
            return null;
        }

        double nv = _normal.dotProduct(ray.getDirection());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        //t is the scalar which will be multiply with the direction of given ray
        double t = alignZero(_normal.dotProduct(p0Q) / nv);

        //newPoint is the possible intersection point
        Point3D newPoint=ray.getPoint(t);

        //the plane is behind the camera tMaxDistance will be negative and then the plane will not be seen
        double tMaxDistance=alignZero(max-t);

        //if the scalar ==0 return null
        //if p0 of the given rays is equals to newPoint calculated before return null
        //if the plane is behind the camera this plane will not be seen and return null
        //else return the newPoint which is the intersection point
        return t <= 0 || newPoint==ray.getPoint() || tMaxDistance<0 ? null : List.of(new GeoPoint(this,newPoint));//if the point of intersection is the same point than the point of ray return null
    }
}

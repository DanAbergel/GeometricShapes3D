package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.alignZero;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public class Sphere extends RadialGeometry {
    Point3D _center;
    public Sphere(Color emissionLight, Material material, double radius, Point3D center) {
        super(emissionLight,radius);
        this.material = material;
        this._center = new Point3D(center);
    }
    public Sphere(Color emission, double _radius, Point3D _center){
        this(emission,new Material(0,0,0),_radius,_center);
    }
    public Sphere(double radius, Point3D center) {
        this(Color.BLACK,new Material(0,0,0),radius,center);
    }
    @Override
    public Vector getNormal(Point3D point){

        Vector orthogonal = new Vector(point.subtract(_center));
        return orthogonal.normalized();
    }

    public Point3D get_center() {

        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        Vector L=_center.subtract(ray.getPoint());
        Vector V=ray.getVector();
        double tm=(L.dotProduct(V));
        double d=Math.sqrt(Math.pow(L.getLenght(),2) - Math.pow(tm,2));
        if (d>this.get_radius())
            return null;
        double th=Math.sqrt(Math.pow(this.get_radius(),2) - Math.pow(d,2));
        double t1=alignZero(tm-th);
        double t2=alignZero(tm+th);
        Point3D point1=ray.getTargetPoint(t1);
        Point3D point2=ray.getTargetPoint(t2);
        double t1MaxDistance=alignZero(t1-max);
        double t2MaxDistance=alignZero(t2-max);
        if (t1>0 && t2>0 ){
            if (t1MaxDistance<=0 && t2MaxDistance<=0) { //verify if the t1 and t2 are behind the light for calculate shadow
                if (ray.getPoint() != point1 && ray.getPoint() != point2){
                    return List.of(new GeoPoint(this, point1), new GeoPoint(this, point2));
                }
                else //if t1 and t2 both are behind the light return null
                    return null;
            }
            //if the t1
            else
                return null;
        }
        if (t1<=0&&t2<=0)
            return null ;
        if (t1 > 0)
            if(t1MaxDistance<=0) {
                if (ray.getPoint() != point1)
                    return List.of(new GeoPoint(this, (ray.getTargetPoint(t1))));
                else
                    return null;
            }
            else
                return null;
        else {// t2>0
            if (t2MaxDistance <= 0) {
                if (ray.getPoint() != point2)
                    return List.of(new GeoPoint(this, ray.getTargetPoint(t2)));
                else
                    return null;
            }
            else
                return null;
        }
    }
}

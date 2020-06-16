package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public class Geometries implements Intersectable {

    List<Intersectable> list;
/**
 * Constructor of the class Geometries**/
    public Geometries() {
        list = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        list = new ArrayList<>();
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries)
        {
            list.add(geometry);
        }
    }
    /*
    in first we create a list which will contain the points of intersection with the plane which composed by all these shapes
    2) we will checked at each shape intersections's points
    finally return the list of points resulted by this research
    * */
    /**public List<GeoPoint> findIntersections(Ray ray,double max) {
        List<GeoPoint> intersections = null;

        for (Intersectable geometry : list) {
            List<GeoPoint> tmpIntersections = geometry.findIntersections(ray);
            if (tmpIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<GeoPoint>();
                intersections.addAll(tmpIntersections);
            }
        }
        return intersections;

    }*/
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double f) {
        if(list.size() == 0)
            return null;
        else
        {
            List<GeoPoint> point3DS = new ArrayList<GeoPoint>();
            for(int i = 0; i< list.size(); i++) {
                var points = list.get(i).findIntersections(ray);
                if(points != null) {
                    for (int j = 0; j < points.size(); j++) {
                        point3DS.add(points.get(j));
                    }
                }
            }
            return point3DS.size() == 0 ? null : point3DS;
        }
    }


}

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

    public Geometries() {
        list = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries)
        {
            list.add(geometry);
        }
    }
    @Override
    /*
    in first we create a list which will contain the points of intersection with the plane which composed by all these shapes
    2) we will checked at each shape intersections's points
    finally return the list of points resulted by this research
    * */
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = null;

        for (Intersectable geometry : list) {
            List<Point3D> tempIntersections = geometry.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<Point3D>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;

    }


}

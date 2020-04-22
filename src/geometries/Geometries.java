package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {

    List<Intersectable> list;

    public Geometries() {
        list = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            list.add(geometry);
        }
    }
    @Override
    public List<Point3D> findIntersections (Ray ray)
    {
        return null;
    }


}

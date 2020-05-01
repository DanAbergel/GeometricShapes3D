package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public interface Intersectable {

    List<Point3D> findIntersections(Ray ray);
}

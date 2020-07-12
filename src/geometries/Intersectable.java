package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 *  @author Dan Abergel and Joss Lalou
 * Intersectable interface is used for force all implementations to crate the function findIntersections
 * This function is used for determine at which point in the shape a ray will cross it
 * We had create also within it a new class GeoPoint which contains two variables : Point3D and a geometry
 */
public interface Intersectable {

    /**
     *
     * */
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    List<GeoPoint> findIntersections(Ray ray, double max);

    /**
     * Define an internal class for GeoPoint
     * */
    public static class GeoPoint{
        public Geometry geometry;
        public Point3D point;

        /**
         * Constructor of internal class GeoPoint
         * @param  _geometry for copy from it to the variable geometry
         * @param  _point  for copy from it to the variable point
         * */
        public GeoPoint(Geometry _geometry, Point3D _point) {
            this.geometry = _geometry;
            this.point = _point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) &&
                    point.equals(geoPoint.point);
        }

    }
}

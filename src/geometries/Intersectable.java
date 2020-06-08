package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public interface Intersectable {

    List<GeoPoint> findIntersections(Ray ray);
    public static class GeoPoint{
        public Geometry geometry;
        public Point3D point;

        /**
         * Constructor of the class GeoPoint
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
            return o.equals(geoPoint.point) && o.equals(geoPoint.geometry);
        }
    }
}

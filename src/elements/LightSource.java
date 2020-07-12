package elements;
import primitives.*;//equivalent

import java.util.List;


/**
 * Interface for common actions of light sources
 */

public interface LightSource {
         List<Ray> findBeamRaysLight(List<Ray> allRays,Point3D point, int numOfRays);
                /**
                 * Get light source intensity as it reaches a point
                 *
                 * @param p the lighted point
                 * @return intensity
                 */
         Color getIntensity(Point3D p);

        /**
         * Get normalized vector in the direction from light source
         *
         * @param p the lighted point
         * @return light to point vector
         */
         Vector getL(Point3D p);
        /**
         * @param point from geopoint
         * @return distance from light source
         */
        double getDistance(Point3D point);
        public LightSource setRadiusOfLight(int r);
}

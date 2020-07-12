package elements;
import primitives.*;
import java.util.List;

/**
 * Interface for common actions of light sources
 */

public interface LightSource {
        /**
         * Function findBeamRaysLight is used for define several rays which touch the
         * light for calculate the soft Shadow
         * @param allRays is a list of all the rays which  touch the light , at start it is empty
         * @param point is the point which create all those rays
         * @param numOfRays is the num of rays we want to create
         * @return the list allRays
         * */
         List<Ray> findBeamRaysLight(List<Ray> allRays,Point3D point, int numOfRays);
        /**
         * Get light source intensity as it reaches a point
         * @param p the lighted point
         * @return intensity
         */
         Color getIntensity(Point3D p);

        /**
         * Get normalized vector in the direction from light source toward a geometry
         * @param p the lighted point
         * @return light to point vector
         */
         Vector getL(Point3D p);
        /**
         * Calculate the distance between the light and the point parameter
         * @param point is the point of distance
         * @return distance from light source
         */
        double getDistance(Point3D point);

        /**
         * To set the wanted radius to the light
         * @param r is the wanted radius
         * @return the instance itself
         * */
         LightSource setRadiusOfLight(int r);
}

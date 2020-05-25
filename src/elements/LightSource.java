package elements;
import primitives.*;//equivalent
//import primitives.Color;
//import primitives.Point3D;
//import primitives.Vector;

/**
 * Interface for common actions of light sources
 */

public interface LightSource {
        /**
         * Get light source intensity as it reaches a point
         *
         * @param p the lighted point
         * @return intensity
         */
        public Color getIntensity(Point3D p);

        /**
         * Get normalized vector in the direction from light source
         *
         * @param p the lighted point
         * @return light to point vector
         */
        public Vector getL(Point3D p);
}

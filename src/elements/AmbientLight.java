package elements;

import primitives.Color;

public class AmbientLight extends Light {
    /**
     * the constructor of AmbientLight with (ka*Ia)
     * @param ia color
     * @param ka double
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

}

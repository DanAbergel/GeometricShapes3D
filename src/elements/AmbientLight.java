package elements;
import primitives.Color;

public class AmbientLight extends Light {
    /**
     * the constructor of AmbientLight with (ka*Ia)
     * Ka is the power of the light and is varies between 0 and 1
     * @param ia color of Ambient light
     * @param ka is the power of the light
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

}

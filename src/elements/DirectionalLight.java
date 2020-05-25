package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    public DirectionalLight(Color ColorIntensity, Vector direction) {
        super(ColorIntensity);
        this.direction = direction.normalized();
    }
    /**
     * @param p the lighted point is not used he is mentioned
     *          only for compatibility with LightSource
     * @return fixed intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
        //       return _intensity;
    }

    //instead of getDirection()
    @Override
    public Vector getL(Point3D p) {
        return direction;
    }
}

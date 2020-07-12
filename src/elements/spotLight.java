package elements;

import primitives.*;
public class spotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor
     * @param _intensity the color intensity
     * @param _position the point3D in the space
     * @param _direction the vector direction
     * @param _kC
     * @param _kL
     * @param _kQ
     */


    public spotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = _direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);

        return (pointlightIntensity.scale(factor));
    }


}

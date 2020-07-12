package elements;

import primitives.*;
public class spotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor
     * @param _intensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     * @param _direction
     */

    public spotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = _direction.normalized();
    }
    /**
     * @param p
     * @return the intensity at the point p
     * **/
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

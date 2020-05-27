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
    public spotLight(Color _intensity, double a, Point3D _position, double _kC, double _kL, double _kQ, Vector _direction) {
        super(_intensity, a, _position, _kC, _kL, _kQ);
        this._direction = _direction;
    }

    public spotLight(Color _intensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._direction = new Vector(_direction).normalized();
    }

    /**
     * @param p
     * @return the intensity at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return (super.getIntensity(p)).scale(Math.max(0, _direction.dotProduct(getL(p))));
    }

    /**
     * @param p
     * @return the direction vector from the light source to the point
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }
}

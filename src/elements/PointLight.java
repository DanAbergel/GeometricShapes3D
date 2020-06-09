package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC;
    protected double _kL;
    protected double _kQ;

    /**
     * constructor
     * @param _intensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     */

    public PointLight(Color _intensity, double a, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity, a);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }
    /**
     * @param p
     * @return the intensity at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distance(p);
        double dsquared = p.distanceSquared(_position);
        return  _intensity.reduce(_kC + _kL*distance + _kQ*(dsquared));
    }

    /**
     * @param p
     * @return the direction vector from the light source to the point
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position))
            return null;
        return p.subtract(_position).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return alignZero(_position.distance(point));
    }
}

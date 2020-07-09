package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    private double radiusOfLight=1;

    public DirectionalLight(Color _intensity, double a, Vector direction) {
        super(_intensity, a);
        this.direction = direction;
    }
    /**
     * constructor
     * @param _intensity
     * @param _direction
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this.direction = _direction.normalize();
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

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    public List<Ray> findBeamRaysLight(List<Ray> allRays,Point3D point, int numOfRays) {return null; }
    }

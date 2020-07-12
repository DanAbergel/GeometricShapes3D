package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    private double radiusOfLight=1;

    /**
     * constructor of DirectionalLight
     * @param _intensity the Color intensity
     * @param _direction the vector
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
    }

    /**
     * return the instance of direction
     * @param p the lighted point
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return direction;
    }

    @Override
    public LightSource setRadiusOfLight(int r){
        radiusOfLight=r;
        return this;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * This function contain all rays of lights
     * @param allRays
     * @param point
     * @param numOfRays
     * @return
     */
    public List<Ray> findBeamRaysLight(List<Ray> allRays,Point3D point, int numOfRays) {return null; }

    }

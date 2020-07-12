package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC;
    protected double _kL;
    protected double _kQ;
    double radiusOfLight=10;

    /**
     * -------------Constructors-----------------
     * @param _intensity
     * @param _position
     * @param _kC this is the coefficient kC
     * @param _kL this is the coefficient kL
     * @param _kQ this is the coefficient kQ
     */
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
        double dsquared = Math.pow(distance,2);
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
    public LightSource setRadiusOfLight(int r){
        radiusOfLight=r;
        return this;
    }

    @Override
    public double getDistance(Point3D point) {
        return alignZero(_position.distance(point));
    }

    /**
     * Function findBeamRaysLight is used for define several rays which touch the
     * light for calculate the soft Shadow
     * @param allRays is a list of all the rays which  touch the light , at start it is empty
     * @param point is the point which create all those rays
     * @param numOfRays is the num of rays we want to create
     * @return the list allRays
     * */
    public List<Ray> findBeamRaysLight(List<Ray> allRays,Point3D point,int numOfRays){
         Vector vTo=getL(point);
         Vector vRight=new Vector(-vTo.getHead().getZ().get(),0,vTo.getHead().getX().get()).normalize();
         Vector vUp=vRight.crossProduct(vTo);

        // the parameter to calculate the coefficient of the _vRight and _vUp vectors
        double dX, dY;

       for (int i=numOfRays;i>0;i--){
           Random r=new Random();
           //the interval in a trigonometric Circle
           double cos=-1+2*r.nextDouble();
           double sin=Math.sqrt(1-Math.pow(cos,2));
           //the interval of possibilities i the light
           double d=-radiusOfLight+2*radiusOfLight*r.nextDouble();
           dX=d*cos;
           dY=d*sin;
           //Pc is the center of light
           Point3D pC=new Point3D(_position.getX().get(),_position.getY().get(),_position.getZ().get());
           if (!isZero(dX))
                pC=pC.add(vRight.scale(dX));
           if (!isZero(dY))
               pC=pC.add(vUp.scale(dY));
           //now Pc has changed and it is the new point random selected
           Vector v=pC.subtract(point);
           Ray ray=new Ray(point,v);
           allRays.add(ray);
       }
        return allRays;
    }

}

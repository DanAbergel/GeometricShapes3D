package elements;
import primitives.*;
import static primitives.Util.isZero;
/**
 * this class is to realize the camera to determine how we will show the form
 */
public class Camera {
    Point3D P0;
    Vector Vt0;
    Vector Vup;
    Vector Vright;

    /**
     * its the constructor that create a new camera with the position choosed
     * @param _p0:center of the camera
     * @param _vTo:axe toward
     * @param _vUp:axe in up
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException ("the vectors must be orthogonal");
        this.P0 =  new Point3D(_p0);
        this.Vt0 = _vTo.normalized();
        this.Vup = _vUp.normalized();
        Vright = this.Vt0.crossProduct(this.Vup).normalize();
    }

    /**
     *construct the view plane and will return the ray that past against the view plane
     * @param nX:int
     * @param nY:int
     * @param j:int
     * @param i:int
     * @param screenDistance:distance between the view plane and the center of the camera
     * @param screenWidth:widht of the view plane
     * @param screenHeight:height of the view plane
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        Point3D Pc = P0.add(Vt0.Scale(screenDistance));
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;
        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);
        Point3D Pij = Pc;
        if (!isZero(xj))
        {
            Pij = Pij.add(Vright.Scale(xj));
        }
        if (!isZero(yi))
        {
            Pij = Pij.subtract(Vup.Scale(yi).getPoint()).getPoint(); // Pij.add(_vUp.scale(-yi))
        }
        Vector Vij = Pij.subtract(P0);
        return new Ray(P0,Vij);
    }
    public Point3D get_p0() {
        return new Point3D(P0);
    }
    public Vector get_vTo() {
        return new Vector(Vt0);
    }
    public Vector get_vUp() {
        return new Vector(Vup);
    }
    public Vector get_vRight() {
        return new Vector(Vright);
    }
}
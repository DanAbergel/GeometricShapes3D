package elements;
import primitives.*;
import static primitives.Util.isZero;
/**
 * this class is to realize the camera to determine how we will show the form
 */

public class Camera {
    private Point3D place;
    private Vector vto;
    private Vector vup;
    private Vector vright;

    // ****************************** Getters *****************************/
    /**
     * Camera getter
     *
     * @return place
     */
    public Point3D getPlace() {
        return place;
    }

    /**
     * Camera getter
     *
     * @return vto
     */
    public Vector getVto() {
        return vto;
    }

    /**
     * Camera getter
     *
     * @return vup
     */
    public Vector getVup() {
        return vup;
    }

    /**
     * Camera getter
     *
     * @return vright
     */
    public Vector getVright() {
        return vright;
    }

    // ****************************** Constructors *****************************/
    /**
     * constructor for camera with params
     *
     * @param _place place of camera
     * @param _vto   forward vector
     * @param _vup   up vector
     */
    public Camera(Point3D _place, Vector _vto, Vector _vup) {
        if (isZero(_vto.dotProduct(_vup))) {
            place = _place;
            vto = _vto.normalized();
            vup = _vup.normalized();
            vright = new Vector(_vto.crossProduct(_vup));
        } else
            throw new IllegalArgumentException("Illegal args");
    }

    /**
     * func constructRayThroughPixel
     *
     * @param nX             pixels on width
     * @param nY             pixels on height
     * @param j              Pixel column
     * @param i              pixel row
     * @param screenDistance dst from view plane
     * @param screenWidth    screen width
     * @param screenHeight   screen height
     * @return ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i,
                                        double screenDistance, double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance from cam cannot be 0");
        }
        // pixel of image center
        Point3D Pc = place.add(vto.Scale(screenDistance));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);
        Point3D Pij = Pc;
        if (!isZero(xj))
            Pij = Pij.add(vright.Scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(vup.Scale(-yi));
        Vector Vij = Pij.subtract(place);
        return new Ray(place, Vij.normalize());
    }
}
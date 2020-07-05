package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Dan Abergel and Joss lalou
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
            vright = _vto.crossProduct(_vup);
        } else
            throw new IllegalArgumentException("Illegal args");
    }

    /**
     * func constructRayThroughPixel
     * this func is only used in tests because i replaced it by the beam function
     * @param nX             pixels on width
     * @param nY             pixels on height
     * @param j              Pixel column
     * @param i              pixel row
     * @param screenDistance dst from view plane
     * @param screenWidth    screen width
     * @param screenHeight   screen height
     * @return Ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i,
                                        double screenDistance, double screenWidth, double screenHeight, boolean isRandom) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance from cam cannot be 0");
        }
        // pixel of image center
        Point3D Pc = place.add(vto.scale(screenDistance));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);
        Point3D Pij = Pc;
        if (!isZero(xj))
            Pij = Pij.add(vright.scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(vup.scale(-yi));
        Vector Vij = Pij.subtract(place);
        return new Ray(place, Vij.normalize());
    }

    public List<Ray> constructRayBeamThroughPixel(int x, int y, int numOfRays, int Nx, int Ny, double screenWidth, double screenHeight, double screenDistance) {
        //the list of rays that we create
        List<Ray> beam = new LinkedList<>();

        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance from cam cannot be 0");
        }
        // pixel of image center
        Point3D Pc = place.add(vto.scale(screenDistance));
        double Ry = screenHeight / Ny;
        double Rx = screenWidth / Nx;
        double yi = ((y - Ny / 2d) * Ry + Ry / 2d);
        double xj = ((x - Nx / 2d) * Rx + Rx / 2d);
        // Pixel[i,j] center:
        Point3D Pij = Pc;
        if (!isZero(xj))
            Pij = Pij.add(vright.scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(vup.scale((-yi)));
        Vector Vij = Pij.subtract(place);
        //the first ray is the ray from camera toward pixel(i,j) center
        beam.add(new Ray(place, Vij));
        numOfRays--;
        if(numOfRays>0) {
            //// the parameter to calculate the coefficient of the _vRight and _vUp vectors
            double dX, dY;
            //the coefficient to calculate in which quadrant is random point on this pixel
            double k, h;
            // the number of random point in each quadrant
            int sum = numOfRays / 4;
            // divide the random points evenly within the four quadrants
            for (int t = 0; t < 4; t++) {
                k = Rx / 2d * (t != 1 && t != 2 ? 1 : -1);
                h = Ry / 2d * (t != 2 && t != 3 ? 1 : -1);
                numOfRays -= sum;
                for (int u = 0; u < sum; u++) {
                    dX = Math.random() * k;
                    dY = Math.random() * h;
                    // find random point on this pixel to create new ray from camera
                    Point3D randomPoint = Pij;
                    if (!isZero(dX)) randomPoint = randomPoint.add(vright.scale(dX));
                    if (!isZero(dY)) randomPoint = randomPoint.subtract(vup.scale(dY));
                    // the other Rays
                    beam.add(new Ray(place, randomPoint.subtract(place)));
                }
            }
        }
        return beam;
    }



}

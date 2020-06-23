package elements;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
                                        double screenDistance, double screenWidth, double screenHeight,boolean isRandom) {
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

    public List<Ray> constructRayBeamThroughPixel(int j, int i ,int numOfRays, int Nx,int Ny, double screenWidth, double screenHeight) {
        //the list of rays that we create
        List<Ray> beam = new LinkedList<>();

        double Ry = screenHeight / Ny;
        double Rx = screenWidth / Nx;
        double yi = ((i - Ny / 2d) * Ry + Ry / 2d);
        double xj = ((j - Nx / 2d) * Rx + Rx / 2d);
        // Pixel[i,j] center:
        Point3D Pij = new Point3D(place);
        if (!isZero(xj)) {
            Pij = Pij.add(vright.Scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.add(vup.Scale((-yi)));
        }
        Vector Vij = Pij.subtract(place);
        //the first ray is the ray from camera toward pixel(i,j) center
        beam.add(new Ray(place,Vij));
        numOfRays--;

        Random r = new Random();

        //// the parameter to calculate the coefficient of the _vRight and _vUp vectors
        double dX ,dY;
        //the coefficient to calculate in which quadrant is random point on this pixel
        int k, h ;
        // the number of random point in each quadrant
        int sum = numOfRays / 4;
        // divide the random points evenly within the four quadrants
        for (int t = 0; t < 4; t++) {
            k = t != 1 && t != 2 ? 1 : -1;
            h = t != 2 && t != 3 ? 1 : -1;
            numOfRays -= sum;
            for (int u = 0; u < sum; u++) {
                dX = r.nextDouble() * Rx / 2d;
                dY = r.nextDouble() * Ry / 2d;
                // find random point on this pixel to create new ray from camera
                Point3D randomPoint = new Point3D(Pij.add(new Vector
                        (vright.Scale(k * dX).substract(vup.Scale(h * dY)))));
                // the other Rays
                beam.add(new Ray(place,new Vector(randomPoint.subtract(place)).normalize()));
            }
        }
        //If the number of Rays requested by a customer - 1 does not divide by 4 without a remainder then
        // we will find some more random points that need
        for (; numOfRays > 0; numOfRays--) {
            dX = -1 + (2 * r.nextDouble() * Rx / 2d);
            dY = -1 + (2 * r.nextDouble() * Ry / 2d);
            // find random point on this pixel to create new ray from camera
            Point3D randomPoint = new Point3D(Pij.add(new Vector
                    (vright.Scale(dX).substract(vup.Scale(dY)))));
            // the other Rays
            beam.add(new Ray(place,new Vector(randomPoint.subtract(place)).normalize()));
        }
        return beam;
    }
}

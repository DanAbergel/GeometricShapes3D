package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * @author Dan Abergel and Joss lalou
 * This class is to realize the camera to determine how we will showes the shapes
 * The camera instances is represented by :
 * place : the place of the camera in a 3D graphical model
 * vTo : the directon vector of face of the camera
 * vUp : the vertical direction vector of the camera
 * vRight : the right direction vector of the camera
 */

public class Camera {

    private Point3D place;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    /**
     * Constructor of the Camera class which takes parameters for all variables of the instance <br/>
     * The constructor don't needs vRight because it can calculate it by a crossProduct operation<br/>
     * If the parameters vUp and vTo are not perpendicular there is a problem with parameters and it throws an exception
     * @param place place of camera
     * @param vto forward vector
     * @param vup up vector
     */
    public Camera(Point3D place, Vector vto, Vector vup) {
        if (isZero(vto.dotProduct(vup))) {
            this.place = place;
            vTo = vto.normalized();
            vUp = vup.normalized();
            vRight = vto.crossProduct(vup);
        }
        else
            throw new IllegalArgumentException("Illegal args");
    }

    public Point3D getPlace() {
        return place;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }




    /**
     * Function constructRayThroughPixel calculates for some i and j - indexes of view plane - the ray which starts at camera
     * and cross the view plane at indexes i and j
     * If the screen distance is 0 throws an exception
     * this func is only used in tests because i replaced it by the beam function
     * @param nX pixels on width
     * @param nY pixels on height
     * @param j Pixel column
     * @param i pixel row
     * @param screenDistance dst from view plane
     * @param screenWidth  screen width
     * @param screenHeight screen height
     * @return new instance of Ray which cross the pixel j,i
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i,
                                        double screenDistance, double screenWidth, double screenHeight, boolean isRandom) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance from cam cannot be 0");
        }
        // Pc is the pixel of view plane center
        Point3D Pc = place.add(vTo.scale(screenDistance));
        //Ry is the height size of one pixel
        double Ry = screenHeight / nY;
        //Rx is the width size of one pixel
        double Rx = screenWidth / nX;
        //yi is the wanted distance in direction of vRight
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);
        Point3D Pij = Pc;
        if (!isZero(xj))
            Pij = Pij.add(vRight.scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(vUp.scale(-yi));
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
        Point3D Pc = place.add(vTo.scale(screenDistance));
        double Ry = screenHeight / Ny;
        double Rx = screenWidth / Nx;
        double yi = ((y - Ny / 2d) * Ry + Ry / 2d);
        double xj = ((x - Nx / 2d) * Rx + Rx / 2d);
        // Pixel[i,j] center:
        Point3D Pij = Pc;
        if (!isZero(xj))
            Pij = Pij.add(vRight.scale(xj));
        if (!isZero(yi))
            Pij = Pij.add(vUp.scale((-yi)));
        Vector Vij = Pij.subtract(place);
        //the first ray is the ray from camera toward pixel(i,j) center
        beam.add(new Ray(place, Vij));
        numOfRays--;
        if(numOfRays>0) {
            //// the parameter to calculate the coefficient of the _vRight and _vUp vectors
            double dX, dY;
            //the coefficient to calculate in which quadrant is random point on this pixel
            double maxLengthHorizontal, maxLengthVertical;
            // the number of random point in each quadrant
            int sum = numOfRays / 4;
            // divide the random points evenly within the four quadrants
            for (int t = 0; t < 4; t++) {
                maxLengthHorizontal = Rx / 2d * (t != 1 && t != 2 ? 1 : -1);
                maxLengthVertical = Ry / 2d * (t != 2 && t != 3 ? 1 : -1);
                numOfRays -= sum;
                for (int u = 0; u < sum; u++) {
                    dX = Math.random() * maxLengthHorizontal;
                    dY = Math.random() * maxLengthVertical;
                    // find random point on this pixel to create new ray from camera
                    Point3D randomPoint = Pij;
                    if (!isZero(dX)) randomPoint = randomPoint.add(vRight.scale(dX));
                    if (!isZero(dY)) randomPoint = randomPoint.subtract(vUp.scale(dY));
                    // the other Rays
                    beam.add(new Ray(place, randomPoint.subtract(place)));
                }
            }
        }
        return beam;
    }



}

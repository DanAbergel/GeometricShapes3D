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
        //xi is the wanted distance in direction of vUp
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);
        //initialize Pij at Pc which is the center of viewPlane
        Point3D Pij = Pc;
        //check if we stay at Pc on vRight axis
        if (!isZero(xj))
            Pij = Pij.add(vRight.scale(xj));
        //check if we stay at Pc on vUp axis
        if (!isZero(yi))
            Pij = Pij.add(vUp.scale(-yi));
        //create a new Vector from camera to Pij
        Vector Vij = Pij.subtract(place);
        return new Ray(place, Vij.normalize());
    }

    /**
     * Function constructRayBeamThroughPixel calculates for some i and j - indexes of view plane - rays which starts at camera
     * and cross the view plane at indexes i and j
     * It calculate predefined number of rays via the pixel i,j for make the principle of Super Sampling
     * If the screen distance is 0 throws an exception
     * @param nX pixels on width
     * @param nY pixels on height
     * @param numOfRaysSuperSampling is predefined number of Super Sampling rays
     * @param j Pixel column
     * @param i pixel row
     * @param screenDistance dst from view plane
     * @param screenWidth  screen width
     * @param screenHeight screen height
     * @return new instance of Ray which cross the pixel j,i
     */
    public List<Ray> constructRayBeamThroughPixel(int j, int i, int numOfRaysSuperSampling, int nX, int nY, double screenWidth, double screenHeight, double screenDistance) {
        //initialize a new list for contain all rays for super sampling
        List<Ray> beam = new LinkedList<>();

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
        //xi is the wanted distance in direction of vUp
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);
        //initialize Pij at Pc which is the center of viewPlane
        Point3D Pij = Pc;
        //check if we stay at Pc on vRight axis
        if (!isZero(xj))
            Pij = Pij.add(vRight.scale(xj));
        //check if we stay at Pc on vUp axis
        if (!isZero(yi))
            Pij = Pij.add(vUp.scale((-yi)));
        //create a new Vector from camera to Pij
        Vector Vij = Pij.subtract(place);
        //the first ray added to the list is the ray from camera toward center of pixel(i,j)
        beam.add(new Ray(place, Vij));
        //subtract 1 from requested number of rays of Super Sampling
        numOfRaysSuperSampling--;
        //if numOfRaysSuperSampling==0 it's means that it's no need to continue to calculate and then exit the func and return
        //only with one ray by the center of pixel(i,j)
        if(numOfRaysSuperSampling>0) {
            //// initialize parameters to calculate the coefficients of the vRight and vUp vectors
            double dX, dY;
            //the limits of pixel in axis x and in axis y
            double maxLengthHorizontal, maxLengthVertical;
            // number of rays which it have to create for each quadrant
            int quadrantNumber = numOfRaysSuperSampling / 4;
            // divide the random points within the four quadrants
            for (int t = 0; t < 4; t++) {
                //if it is in quadrant 0 or 4 the limit in axis x will be positive and else - negative
                maxLengthHorizontal = Rx / 2d * (t != 1 && t != 2 ? 1 : -1);
                //if it is in quadrant 0 or 1 the limit in axis y will be positive and else - negative
                maxLengthVertical = Ry / 2d * (t != 2 && t != 3 ? 1 : -1);
                for (int u = 0; u < quadrantNumber; u++) {
                    //Math.random get a random number between 0 and 1
                    //calculate dX by multiply a random number (0,1) to the limit in x for stay within the limits
                    dX = Math.random() * maxLengthHorizontal;
                    //calculate dY by multiply a random number (0,1) to the limit in y for stay within the limits
                    dY = Math.random() * maxLengthVertical;
                    // randomPoint is initialized to the center of pixel(i,j)
                    Point3D randomPoint = Pij;
                    //check if we stay at Pij on vRight axis
                    if (!isZero(dX))
                        randomPoint = randomPoint.add(vRight.scale(dX));
                    //check if we stay at Pij on vUp axis
                    if (!isZero(dY))
                        randomPoint = randomPoint.subtract(vUp.scale(dY));
                    //add to the list the new Ray from camera to randomPoint in pixel(i,j)
                    beam.add(new Ray(place, randomPoint.subtract(place)));
                }
                //after finish one quadrant subtract fourth of total number of rays from the total number
                numOfRaysSuperSampling -= quadrantNumber;
            }
        }
        return beam;
    }



}

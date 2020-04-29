package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    Point3D point;
    Vector Vup;
    Vector Vright;
    Vector Vto;


    public Camera(Point3D point, Vector vup, Vector vto) {
        if(vup.dotProduct(vto)!=0)
            throw new IllegalArgumentException("the vectors Vup and Vto are not perpendicular");
        this.point = point;
        this.Vup = vup.normalized();
        this.Vto = vto.normalized();
        this.Vright=vup.crossProduct(vto).normalized();
    }

    public Point3D getPoint() {
        return point;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVright() {
        return Vright;
    }

    public Vector getVto() {
        return Vto;
    }
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight){
        return null;
    }

}

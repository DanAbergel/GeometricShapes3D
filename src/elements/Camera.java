package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private Point3D point;
    private Vector Vto;
    private Vector Vup;
    private Vector Vright;

    public Camera(Point3D point, Vector vto, Vector vup) {
        try {
            if (vto.dotProduct(vup) != 0)
                throw new IllegalArgumentException("Vto and Vup are not perpendicular");
            this.point = point;
            Vto = vto;
            Vup = vup;
            Vright=Vto.crossProduct(Vup);
        }catch(IllegalArgumentException message)
        {
            System.out.println(message);
        }
    }

    public Point3D getPoint() {
        return point;
    }

    public Vector getVto() {
        return Vto;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVright() {
        return Vright;
    }

    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight){
        return null;
    }

}

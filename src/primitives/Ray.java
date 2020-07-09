package primitives;

/**
 * Class Ray representing a partial vector with a direction and the point that it starts from , for using 3D Cartesian coordinates
 * @author Joss Lalou and Dan Abergel
 */
public class Ray {
    private static final double DELTA = 0.1;
    private Vector direction;
    private Point3D p0;

    /**
     * Constructor of the Ray class which takes as parameters a Point3D and 2 Vectors :
     *            one for the direction vector and other for normal vector
     * It's copy the direction vector after have been normalized
     * @param dir ray's direction
     * @param normal ray's normal
     * @param point  point that ray starts from
     */
    public Ray(Point3D point, Vector dir, Vector normal) {
        this.direction = dir.normalized();
        double nv = normal.dotProduct(dir);
        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        this.p0 = point.add(normalDelta);
    }

    /**
     * Constructor of the Ray class which takes as parameters p0 Point3D and direction Vector
     * @param p is the p0 Point3D of Ray
     * @param v is the direction Vector of Ray
     * */
    public Ray(Point3D p, Vector v) {
        this.p0 = p;
        this.direction = v.normalized();
    }

    public Vector getDirection() {
        return direction;
    }

    public Point3D getPoint() {
        return p0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Ray))
            return false;
        if (this == obj)
            return true;
        Ray other = (Ray) obj;
        return p0.equals(other.p0) && direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "vector=" + direction +
                ", point=" + p0 +
                '}';
    }

    /**
     * Function getPoint calculate a new Point3D which is the new space after multiply the direction
     * vector by a scalar offset <br/>
     * If offset equals 0 it will throw an exception because it can't create ZERO vector
     * @param offset is the scalar the direction vector multiplied by
     * @return new instance of Point3D which is the new result space
     * */
    public Point3D getPoint(double offset) {
        try {
            return p0.add(direction.scale(offset));
        } catch (IllegalArgumentException exception) {
            return p0;
        }
    }
}
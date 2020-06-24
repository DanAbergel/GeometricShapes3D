package primitives;

import static primitives.Util.isZero;

/**
 * class representing a partial vector with a direction and the point that it starts from using 3D Cartesian coordinates
 *
 * @author Yeoshua and Dan
 */
public class Ray {
    private static final double DELTA = 0.1;
    private Vector dir;
    private Point3D p0;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Ray))
            return false;
        if (this == obj)
            return true;
        Ray other = (Ray) obj;
        return p0.equals(other.p0) && dir.equals(other.dir);
    }

    public Point3D getPoint(double offset) {
        try {
            return p0.add(dir.scale(offset));
        } catch (IllegalArgumentException exception) {
            return p0;
        }
    }

    /**
     * @return ray direction in new vector so the direction can't be changed
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * @return head as new point so it can't be modified
     */

    public Point3D getPoint() {
        return p0;
    }

    /**
     * builds ray from point and directon
     *
     * @param dir ray's direction
     * @param point     point that ray starts from
     */
    public Ray(Point3D point, Vector dir, Vector normal) {
        this.dir = dir.normalized();

        double nv = normal.dotProduct(dir);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        this.p0 = point.add(normalDelta);
    }

    public Ray(Point3D p, Vector v) {
        p0 = p;
        this.dir = v.normalized();
    }

    /**
     * @return ray's head and direction
     */
    @Override
    public String toString() {
        return "Ray{" +
                "vector=" + dir +
                ", point=" + p0 +
                '}';
    }
}
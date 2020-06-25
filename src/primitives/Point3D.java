package primitives;

/**
 * @author Dan Abergel and Joss Lalou
 */
public class Point3D {
    //static public final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    static public final Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    public Coordinate getX() {
        return x;
    }

    public Coordinate getY() {
        return y;
    }

    public Coordinate getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) &&
                y.equals(point3D.y) &&
                z.equals(point3D.z);
    }

    @Override
    public String toString() {
        return "x:" + x +
                " y:" + y +
                " z:" + z;
    }

    public Vector subtract(Point3D point) {
        double newX = this.x._coord - point.x._coord;
        double newY = this.y._coord - point.y._coord;
        double newZ = this.z._coord - point.z._coord;
        return new Vector(newX, newY, newZ);
    }

    public Point3D add(Vector vector) {
        double newX = this.x._coord + vector.head.x._coord;
        double newY = this.y._coord + vector.head.y._coord;
        double newZ = this.z._coord + vector.head.z._coord;
        return new Point3D(newX, newY, newZ);
    }

    public double distanceSquared(Point3D p) {
        double dx = this.x._coord - p.x._coord;
        double dy = this.y._coord - p.y._coord;
        double dz = this.z._coord - p.z._coord;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    public Point3D subtract(Vector v) {
        return new Point3D(this.x._coord - v.head.x._coord,
                this.y._coord - v.head.y._coord,
                this.z._coord - v.head.z._coord);
    }
}
package primitives;

/**
 * @author Dan Abergel and Joss Lalou
 */
public class Point3D {

    //those coordinates can't change after been create , then are final
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    static public final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * Constructor for the class Point3D which takes three parameters double
     * @param x is the coordinate x
     * @param y is the coordinate y
     * @param z is the coordinate z
     * */
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

    /**
     * Function subtract takes as parameter a Point3D and subtract the actual instance of Point3D
     *          and subtract from it the Point3D parameter - for each coordinate of actual instance
     *          subtract the same coordinate of parameter's instance
     * @param point is the point which will be subtract from actual instance of Point3D
     * @return a new instance of Vector which is result of the subtract
     * */
    public Vector subtract(Point3D point) {
        return new Vector(this.x._coord - point.x._coord,
                this.y._coord - point.y._coord,
                this.z._coord - point.z._coord);
    }

    /**
     * Function subtract takes as parameter a Vector and subtract the actual instance of Point3D
     *          and subtract from it the Point3D head's vector - for each coordinate of actual instance
     *          subtract the same coordinate of head's vector parameter
     * @param v is the vector which its head will be subtract from actual instance of Point3D
     * @return a new instance of Point3D which the result of the subtract
     * */
    public Point3D subtract(Vector v) {
        return new Point3D(this.x._coord - v.head.x._coord,
                this.y._coord - v.head.y._coord,
                this.z._coord - v.head.z._coord);
    }

    /**
     * function add takes as parameter a Vector and add to actual instance the vector for get a new Point3D
     *           which will be addition of actual instance with the Point3D head's vector - for each coordinate
     *           of actual instance add the same coordinate of head's vector
     * @param vector is the vector which will be added to actual instance Point3D
     * @return a new instance of Point3D which is result of the addition
     * */
    public Point3D add(Vector vector) {

        return new Point3D(this.x._coord + vector.head.x._coord,
                this.y._coord + vector.head.y._coord,
                this.z._coord + vector.head.z._coord);
    }

    /**
     * function distanceSquared calculate the squared distance between actual instance of Point3D and the parameter p
     * @param p is the second point for calculate the distance squared
     * @return double value which is the squared distance between the two points
     * */
    public double distanceSquared(Point3D p) {
        double dx = this.x._coord - p.x._coord;
        double dy = this.y._coord - p.y._coord;
        double dz = this.z._coord - p.z._coord;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * function distance take as parameter a Point3D and calculate the distance between actual instance Point3D and this point
     *          The function use the distanceSquared function for calculate this distance by get the returned value by distanceSquared
     function and put it in the function Math.sqrt for remover the power
     * @param point is the second point for calculate the distance
     * @return a double value of the distance between those two points
     * */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }



}
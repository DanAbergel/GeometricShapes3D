package primitives;

/**
 * class representing a vector on the 3D grid
 *
 * @author yeoshua and Dan
 */
public class Vector {
    Point3D head;//vector's head
    private double length = 0;
    private double lengthSquared = 0;

    /**
     * builds a vector's head with the coordinates
     *
     * @param x point on x axel
     * @param y point on y axel
     * @param z point on z axel
     */
    public Vector(double x, double y, double z) {
        this.head = new Point3D(x, y, z);
        if (Point3D.ZERO.equals(this.head))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
    }

    /**
     * @param head vector's head is set to head received
     */
    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        this.head = head;
    }

    /**
     * @return head of vector as new point so it can't be modified
     */
    public Point3D getHead() {
        return head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return getHead().equals(vector.getHead());
    }

    /**
     * @return vector's head coordinates
     */
    @Override
    public String toString() {
        return "Vector{" + head.toString() + '}';
    }

    /**
     * @param other vector to substract from currect vector
     * @return new vector that's this vector minus v
     */
    public Vector subtract(Vector other) {
        return this.head.subtract(other.head);
    }

    /**
     * @param other vector to add to current
     * @return new vector with addition of two vectors
     */
    public Vector add(Vector other) {
        return new Vector(head.add(other));
    }

    /**
     * @param n number of times to multiply vector
     * @return new vector times num
     */
    public Vector scale(double n) {
        return new Vector(head.x._coord * n, head.y._coord * n, head.z._coord * n);
    }

    /**
     * @param vect3 other vector to calculate with
     * @return dot product between the two vectors
     */
    public double dotProduct(Vector vect3) {
        return (this.head.x._coord * vect3.head.x._coord + this.head.y._coord * vect3.head.y._coord + this.head.z._coord * vect3.head.z._coord);
    }

    /**
     * @param vect4 other vector
     * @return cross product between the two vectors.
     */
    public Vector crossProduct(Vector vect4) {
        double newX = this.head.y._coord * vect4.head.z._coord - this.head.z._coord * vect4.head.y._coord;
        double newY = this.head.z._coord * vect4.head.x._coord - this.head.x._coord * vect4.head.z._coord;
        double newZ = this.head.x._coord * vect4.head.y._coord - this.head.y._coord * vect4.head.x._coord;
        return new Vector(newX, newY, newZ);
    }

    /**
     * @return vector's length squared
     */
    public double lengthSquared() {
        if (lengthSquared == 0)
            lengthSquared = this.head.x._coord * this.head.x._coord + this.head.y._coord * this.head.y._coord + this.head.z._coord * this.head.z._coord;
        return lengthSquared;
    }

    /**
     * @return and calculates vector's length
     */
    public double length() {
        if (length == 0) {
            if (lengthSquared == 0)
                lengthSquared = lengthSquared();
            length = Math.sqrt(lengthSquared);
        }
        return length;
    }


    /**
     * @return current vector with normalized length.
     */
    public Vector normalize() {
        double length = this.length();
        this.head = new Point3D(this.head.x._coord / length, this.head.y._coord / length, this.head.z._coord / length);
        return this;
    }

    //ask if to change original vector (this.normalize changes it)
    public Vector normalized() {
        return new Vector(this.head).normalize();
    }
}
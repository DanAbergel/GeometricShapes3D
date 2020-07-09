package primitives;

/**
 * class representing a vector on the 3D grid
 *
 * @author joss lalou and Dan abergel
 */
public class Vector {
    Point3D head;//vector's head
    //private double length = 0;//length vector
    private double lengthSquared = 0;

    /**
     * Constructor of Vector class with three parameters double for the three coordinates of head's vector
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
     * Constructor of Vector class with one parameter Point3D for the head's vector
     * @param head vector's head is set to head received
     */
    public Vector(Point3D head) {
        //check if the parameter is equals to the Point3D.ZERO
        if (head.equals(Point3D.ZERO))
            //if it's equals to , throw an Exception
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        this.head = head;
    }

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

    @Override
    public String toString() {
        return "Vector{" + head.toString() + '}';
    }

    /**
     * Function subtract calculate the subtract of the vector's parameter from the actual instance Vector <br/>
     * It is use the principle of DRY and delegate the calculation to the Point3D class
     * @param other vector to subtract from actual vector instance
     * @return a new instance of Vector which is the result of the subtract
     */
    public Vector subtract(Vector other) {
        return this.head.subtract(other.head);
    }

    /**
     * Function add calculate the addition of the vector's parameter to the actual instance Vector <br/>
     * It is use the principle of DRY and delegate the calculation to the Point3D class
     * @param other vector to add to actual instance Vector
     * @return a new instance of Vector which the result of the addition
     */
    public Vector add(Vector other) {
        return new Vector(head.add(other));
    }

    /**
     * Function scale takes the parameter n to multiply in a scalar multiplication it to the vector
     * @param n is the scalar which will be multiplies to actual instance Vector
     * @return na new instance of Vector which is the result of scalar multiplication
     */
    public Vector scale(double n) {
        return new Vector(head.x._coord * n, head.y._coord * n, head.z._coord * n);
    }

    /**
     * Function dotProduct takes a Vector parameter and calculates the dotProduct between actual instance and the parameter
     * If both are perpendicular the result will be 0
     * @param v is the second Vector
     * @return a double value which is the result of this operation
     */
    public double dotProduct(Vector v) {
        return (this.head.x._coord * v.head.x._coord + this.head.y._coord * v.head.y._coord + this.head.z._coord * v.head.z._coord);
    }

    /**
     * Function crossProduct takes an other instance of Vector as parameter and calculate the dotProduct between them
     * The formula is X= y1 * z2 - z1 * y2
     *                Y= z1 * x2 - x1 * z2
     *                Z= x1 * y2 - y1 * x2
     * @param v is the second vector
     * @return a new instance of Vector which is the result of crossProduct operation
     */
    public Vector crossProduct(Vector v) {
        double newX = this.head.y._coord * v.head.z._coord - this.head.z._coord * v.head.y._coord;
        double newY = this.head.z._coord * v.head.x._coord - this.head.x._coord * v.head.z._coord;
        double newZ = this.head.x._coord * v.head.y._coord - this.head.y._coord * v.head.x._coord;
        return new Vector(newX, newY, newZ);
    }

    /**
     * Function calculate the length of the Vector instance
     * If the variable lengthSquared equals zero it's means that this variable had never been initialized
     *    and then it's calculate here , but if it's not equals zero it's means that this distance has already been calculated
     *    and then the function only returns the value of squaredDistance
     * @return vector's length squared
     */
    public double lengthSquared() {
        if (lengthSquared == 0)
            lengthSquared = this.head.x._coord * this.head.x._coord + this.head.y._coord * this.head.y._coord + this.head.z._coord * this.head.z._coord;
        return lengthSquared;
    }

    /**
     * Function length calculate  the length of this instance by the function lengthSquared
     * @return vector length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }


    /**
     * Function normalize calculate a new instance of Vector which will be the same vector but normalized
     * @return the actual instance
     */
    public Vector normalize() {
        double length = this.length();
        this.head = new Point3D(this.head.x._coord / length, this.head.y._coord / length, this.head.z._coord / length);
        return this;
    }

    /**
     * Function normalized only return the result of the function normalize in a new instance Vector
     * @return the new instance of Vector
     * */
    public Vector normalized() {
        return new Vector(this.head).normalize();
    }
}
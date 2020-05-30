package primitives;
import java.util.Objects;
/**
 * class representing a vector on the 3D grid
 * @author yeoshua and Dan
 */
public class Vector
{
    Point3D Point;//vector's head
    double lenght;
    /**
     * builds vector out of three coordinates
     * @param x coordinate on x axel
     * @param y coordinate on y axel
     * @param z coordinate on z axel
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z)throws IllegalArgumentException
    {

        if(x.get()==0&&y.get()==0&&z.get()==0)
        {
          throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        }
        this.Point = new Point3D(x,y,z);
        this.lenght=Math.sqrt(Math.pow(x._coord,2)+Math.pow(y._coord,2)+Math.pow(z._coord,2));

    }

    /**
     * builds a vector's head with the coordinates
     * @param x point on x axel
     * @param y point on y axel
     * @param z point on z axel
     */
    public Vector(double x,double y,double z) throws IllegalArgumentException
    {
        if(x==0&&y==0&&z==0)
        {
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        }
        else{
            this.Point=new Point3D(x,y,z);}
    }

    /**
     *
     * @param point vector's head is set to head received
     */
    public Vector(Point3D point)
    {
        if (point.equals(Point3D.ZERO))
        {
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        }
        this.Point=new Point3D(point.x._coord,point.y._coord,point.z._coord);
    }
    public Vector(Point3D p1, Point3D p2) {
        this(p1.subtract(p2));
    }
    /**
     *
     * @param vector vector with head coordinates to set this vector from the same coordinates
     */

    public Vector(Vector vector)
    {
        Point=new Point3D(vector.Point);
        lenght=vector.lenght;
    }

    /**
     *
     * @return head of vector as new point so it can't be modified
     */
    public Point3D getPoint()
    {
        return Point;
    }

    public double getLenght()
    {
        return lenght;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return getPoint().equals(vector.getPoint());
    }
    /**
     *
     * @return vector's head coordinates
     */
    @Override
    public String toString()
    {
        return "Vector{"+Point.toString() +'}';
    }
    /**
     *
     * @param vect1 vector to substract from currect vector
     * @return new vector that's this vector minus v
     */
    public Vector substract(Vector vect1)
    {
        Coordinate newX=new Coordinate(this.Point.x._coord- vect1.Point.x._coord);
        Coordinate newY=new Coordinate(this.Point.y._coord- vect1.Point.y._coord);
        Coordinate newZ=new Coordinate(this.Point.z._coord- vect1.Point.z._coord);

        return new Vector(newX,newY,newZ);
    }
    
    /**
     *
     * @param vect2 vector to add to current
     * @return new vector with addition of two vectors
     */
    public Vector add(Vector vect2)throws IllegalArgumentException
    {

        Coordinate newX=new Coordinate(this.Point.x._coord+vect2.Point.x._coord);
        Coordinate newY=new Coordinate(this.Point.y._coord+ vect2.Point.y._coord);
        Coordinate newZ=new Coordinate(this.Point.z._coord+ vect2.Point.z._coord);
        return new Vector(newX,newY,newZ);}
    /**
     *
     * @param n number of times to multiply vector
     * @return new vector times num
     */
    public Vector Scale(double n)
    {
        Coordinate newX=new Coordinate(this.Point.x._coord*n);
        Coordinate newY=new Coordinate(this.Point.y._coord*n);
        Coordinate newZ=new Coordinate(this.Point.z._coord*n);
        return new Vector(newX,newY,newZ);
    }
    /**
     *
     * @param vect3 other vector to calculate with
     * @return dot product between the two vectors
     */
    public Double dotProduct(Vector vect3)
    {
        return (this.Point.x._coord*vect3.Point.x._coord+this.Point.y._coord*vect3.Point.y._coord+this.Point.z._coord*vect3.Point.z._coord);
    }

    /**
     *
     * @param vect4 other vector
     * @return cross product between the two vectors.
     */
    public Vector crossProduct(Vector vect4)
    {
        Coordinate newX=new Coordinate((this.Point.y._coord * vect4.Point.z._coord)-(this.Point.z._coord * vect4.Point.y._coord));
        Coordinate newY=new Coordinate((this.Point.z._coord * vect4.Point.x._coord)-(this.Point.x._coord * vect4.Point.z._coord));
        Coordinate newZ=new Coordinate((this.Point.x._coord * vect4.Point.y._coord)-(this.Point.y._coord * vect4.Point.x._coord));
        return new Vector(newX,newY,newZ);
    }
    /**
     *
     * @return vector's length squared
     */
    public double lengthSquared()
    {
        return this.Point.x._coord*this.Point.x._coord+this.Point.y._coord*this.Point.y._coord+this.Point.z._coord*this.Point.z._coord;
    }
    /**
     *
     * @return and calculates vector's length
     */
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }


    /**
     *
     * @return current vector with normalized length.
     */
    public Vector normalize()
    {

        double x = this.Point.x._coord;
        double y = this.Point.y._coord;
        double z = this.Point.z._coord;

        double length = this.length();

        if (length == 0)
            throw new ArithmeticException("divide by Zero in trying of normalise a vector");

        this.Point.x = new Coordinate(x / length);
        this.Point.y = new Coordinate(y / length);
        this.Point.z = new Coordinate(z / length);

        return this;
    }
    //ask if to change original vector (this.normalize changes it)
    public Vector normalized()
    {
        return new Vector(this).normalize();
    }
}
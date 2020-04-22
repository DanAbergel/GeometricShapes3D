package primitives;
import static java.lang.System.out;
import java.util.Objects;

public class Point3D
{
    static public final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    Coordinate x;
    Coordinate y;
    Coordinate z;

    //final Point3D ZERO =new Point3D(new Coordinate(0),new Coordinate(0),new Coordinate(0));

    public Point3D(double x,double y,double z)
    {
        this.x=new Coordinate(x);
        this.y=new Coordinate(y);
        this.z=new Coordinate(z);
    }

    public Point3D(Coordinate x,Coordinate y,Coordinate z)
    { this.x=x;
        this.y=y;
        this.z=z;

    }

    public Point3D(Point3D point)
    {
        this.x=new Coordinate(point.x);
        this.y=new Coordinate(point.y);
        this.z=new Coordinate(point.z);
    }

    public Coordinate getX()
    {
        return new Coordinate(x) ;
    }

    public Coordinate getY()
    {
        return new Coordinate(y);
    }

    public Coordinate getZ()
    {
        return new Coordinate(z);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D oth = (Point3D) obj;
        return x.equals(oth.x) && y.equals(oth.y) && z.equals(oth.z);
    }

    @Override
    public String toString()
    {
        return "x:" + x +
                " y:" + y +
                " z:" + z;
    }


    public Vector subtract(Point3D point2)
    {
        Coordinate newX=new Coordinate(this.x._coord-point2.x._coord);
        Coordinate newY=new Coordinate(this.y._coord-point2.y._coord);
        Coordinate newZ=new Coordinate(this.z._coord-point2.z._coord);
        return new Vector(newX,newY,newZ);
    }

    public Point3D add(Vector vector)
    {
        Coordinate newX=new Coordinate(this.x._coord+vector.Point.x._coord);
        Coordinate newY=new Coordinate(this.y._coord+vector.Point.y._coord);
        Coordinate newZ=new Coordinate(this.z._coord+vector.Point.z._coord);
        return new Point3D(newX,newY,newZ);
    }

    public double distanceSquared(Point3D point2)
    {
        double doubleX=point2.x._coord*(point2.x._coord);
        double doubleY=point2.y._coord*(point2.y._coord);
        double doubleZ=point2.z._coord*(point2.z._coord);
        return doubleX+doubleY+doubleZ;
    }

    public double distance(Point3D point3)
    {
        return Math.sqrt(distanceSquared(point3));
    }

}
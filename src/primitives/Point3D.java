package primitives;
import static java.lang.System.out;
import java.util.Objects;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public class Point3D
{
    //static public final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    Coordinate x;
    Coordinate y;
    Coordinate z;

  static  public final Point3D ZERO =new Point3D(new Coordinate(0),new Coordinate(0),new Coordinate(0));

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) &&
                y.equals(point3D.y) &&
                z.equals(point3D.z);
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

    public double distanceSquared(Point3D p) {
        double dx = this.x.get() - p.x.get();
        double dy = this.y.get() - p.y.get();
        double dz = this.z.get() - p.z.get();
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Point3D point3)
    {
        return Math.sqrt(distanceSquared(point3));
    }

    public Point3D subtract(Vector v) {
        return new Point3D(this.x._coord - v.getPoint().x._coord,
            this.y._coord - v.getPoint().y._coord,
            this.z._coord - v.getPoint().z._coord);
    }
}
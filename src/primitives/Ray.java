package primitives;
/**
 * class representing a partial vector with a direction and the point that it starts from using 3D Cartesian coordinates
 * @author Yeoshua and Dan
 */
public class Ray
{
    Vector vector;
    Point3D point;


    /**
     * sets this directon and head based on r's direction and head
     * @param rayon ray to copy information from
     */
    public Ray(Ray rayon)
    {
        vector=new Vector(rayon.vector);
        point=new Point3D(rayon.point);

    }
    /**
     *
     * @return ray direction in new vector so the direction can't be changed
     */
    public Vector getVector()
    {
        return vector;
    }
    /**
     *
     * @return head as new point so it can't be modified
     */

    public Point3D getPoint()
    {
        return point;
    }

    /**
     * builds ray from point and directon
     * @param _dir ray's direction
     * @param _p point that ray starts from
     */

    public Ray(Point3D _p, Vector _dir) throws IllegalArgumentException
    {

        if(vector.lenght != 1.0)
            throw new IllegalArgumentException("Vector is not normalized");

        this.point = new Point3D(_p);
        this.vector= new Vector(_dir);
    }

    /**
     *
     * @return ray's head and direction
     */
    @Override
    public String toString()
    {
        return "Ray{" +
                "vector=" + vector +
                ", point=" + point +
                '}';
    }
}
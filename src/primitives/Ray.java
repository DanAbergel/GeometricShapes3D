package primitives;

import static primitives.Util.isZero;

/**
 * class representing a partial vector with a direction and the point that it starts from using 3D Cartesian coordinates
 * @author Yeoshua and Dan
 */
public class Ray
{
    Vector vector;
    Point3D point;


    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return  false;
        }
        if (!(obj instanceof Ray)) {
            return false;
        }
        if (this == obj)
            return true;
        Ray other = (Ray)obj;
        return (point.equals(other.point) &&
                vector.equals(other.vector));
    }
    /**
     * sets this directon and head based on r's direction and head
     * @param rayon ray to copy information from
     */
    public Ray(Ray rayon)
    {
        vector=new Vector(rayon.vector).normalize();
        point=new Point3D(rayon.point);

    }
    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? point : new Point3D(point).add(vector.Scale(length));
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
     * @param direction ray's direction
     * @param pointe point that ray starts from
     */


    public Ray(Point3D pointe, Vector direction)
    {
        point = new Point3D(pointe);
        vector = new Vector(direction).normalize();
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
    public Point3D getPoint(double length) {
        return isZero(length ) ? point : new Point3D(point).add(vector.Scale(length));
    }
}
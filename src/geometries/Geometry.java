package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public interface Geometry extends Intersectable
{
    public Vector getNormal(Point3D pt);

}

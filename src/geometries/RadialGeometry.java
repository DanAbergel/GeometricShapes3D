package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public abstract class RadialGeometry implements Geometry {
    private double _radius;

    public double get_radius()
    {
        return _radius;
    }
    public RadialGeometry(double _radius)
    {
        this._radius = _radius;
    }

}

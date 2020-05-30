package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public abstract class RadialGeometry extends Geometry {
    private double _radius;
    public RadialGeometry(Color emission, Material material, double _radius) {
        super(emission, material);
        this._radius = _radius;
    }
    public RadialGeometry(Color emission, double _radius){
        this(emission,new Material(0,0,0),_radius);
    }
    public RadialGeometry(double _radius){
        this(Color.BLACK,new Material(0,0,0),_radius);
    }
    public double get_radius()
    {
        return _radius;
    }

}

package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;
/**
 * @author Dan Abergel and Joss Lalou
 * Class RadialGeometry used for all geometries thath have a radius
 */
public abstract class RadialGeometry extends Geometry {
    private double _radius;

    /**
     * Constructor of RadialGeometry with three parameters : Color , Material , and the radius
     * @param emission is the color of the geometry
     * @param material  is the material of the geometry
     * @param _radius is the radius of the geometry
     * */
    public RadialGeometry(Color emission, Material material, double _radius) {
        super(emission, material);
        this._radius = _radius;
    }

    /**
     * Constructor of RadialGeometry with two parameters : Color and radius
     * Use principle DRY
     * @param emission is the color of the geometry
     * @param _radius is the radius of the geometry
     * */
    public RadialGeometry(Color emission, double _radius){
        this(emission,new Material(0,0,0),_radius);
    }

    /**
     * Constructor of RadialGeometry with only the radius
     * @param _radius is the radius of the geometry
     * */
    public RadialGeometry(double _radius){
        this(Color.BLACK,new Material(0,0,0),_radius);
    }

    public double get_radius()
    {
        return _radius;
    }

}

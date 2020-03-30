package geometries;

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

package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
/**
 *
 * @author Dan Abergel and Joss Lalou
 */
public abstract class Geometry implements Intersectable
{
    protected Color _emission;

/**
 * Constructor by default of the class Geometry
 * */
    public Geometry() {
        this._emission = Color.BLACK;
    }
/**
 * Constructor of the class Geometry
 * @param emission it copies from this variable to _emission
 * */
    public Geometry(Color emission) {
        this._emission = emission;
    }

    /**
 * function to get the variable _emission
 */
    public Color get_emission() {
        return _emission;
    }

    public abstract Vector getNormal(Point3D pt);
}

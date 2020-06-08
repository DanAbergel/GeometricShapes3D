package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
    /**
     *
     * @author Dan Abergel and Joss Lalou
     */
public abstract class Geometry implements Intersectable
{
    protected Color _emission;
    protected Material material;
    /**
     * constructor
     * @param emission
     * @param material
     */
    public Geometry(Color emission, Material material) {
        this._emission = emission;
        this.material = material;
    }

    /**
     * Constructor by default of the class Geometry
     * */
    public Geometry() {
        this._emission = Color.BLACK;
        material = new Material(0,0,0);
    }
    /**
     * Constructor of the class Geometry
     * @param emission it copies from this variable to _emission
     * */
    public Geometry(Color emission) {
        this._emission = emission;
        material=new Material(0,0,0);
    }

    /**
     *  function to get the variable _emission
    */
    public Color get_emission() {

        return _emission;
    }
    public Material getMaterial() {

        return material;
    }
    public Color getEmission() {
        return _emission;
    }
    public abstract Vector getNormal(Point3D pt);
}

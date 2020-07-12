package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
    /**
     * @author Dan Abergel and Joss Lalou
     * All classes which herit from this abstract class herit the function getNormal
     */
public abstract class Geometry implements Intersectable
{
    protected Color _emission;
    protected Material material;

    /**
     * cConstructor of Geometry instance with 2 parameters Color and Material
     * @param emission is the color of each geometry instance
     * @param material is the material of each geometry instance
     */
    public Geometry(Color emission, Material material) {
        this._emission = emission;
        this.material = material;
    }

    /**
     * Constructor by default of the class Geometry
     * for emission of actual instance it takes per default the black color
     * it use the DRY principle
     * and for material it takes per default kD=0 ,kS=0 ,nShininess0
     * */
    public Geometry() {
        this(Color.BLACK,new Material(0,0,0));
    }
    /**
     * Constructor of the class Geometry with one parameter Color
     * it use the DRY principle
     * @param emission it copies from this variable to _emission
     * */
    public Geometry(Color emission) {
        this(emission,new Material(0,0,0));
    }

    public Color get_emission() {
        return _emission;
    }

    public Material getMaterial() {
        return material;
    }

    /**
     * Function setMaterial allow to set new values for the material of the geometry
     * @param mat is the new material
     * @return geometry itself for chaining
     */
    public Geometry setMaterial(Material mat) {
        material = mat;
        return this;
    }

    /**
     * This function is heritage for all classes which herits from this class
     * and for this function we have created all the class
     * @param pt is the point for get the normal
     * */
    public abstract Vector getNormal(Point3D pt);
}

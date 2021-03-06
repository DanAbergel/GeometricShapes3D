
package elements;
import primitives.Color;
public abstract class Light {
    /**
     * variable:_intensity value; type of variable: protected
     */
    protected Color _intensity;

    /**
     * the constructor of the class Light for the variable _intensity
     * @param _intensity is the color of Light
     * @return _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }
    public Color getIntensity() {
        return _intensity;
    }
}
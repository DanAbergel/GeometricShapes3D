package elements;

import primitives.Color;

public class AmbientLight {

    Color _intensity;

    public AmbientLight(Color color, double k) {
        this._intensity =color.scale(k);
    }

    public Color get_intensity() {
        return _intensity;
    }
}

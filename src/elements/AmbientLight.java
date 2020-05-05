package elements;

import primitives.Color;

public class AmbientLight {

    Color _intensity;
    double k;

    public AmbientLight(Color color, double k) {
        this.color = color;
        this.k = k;
    }
}

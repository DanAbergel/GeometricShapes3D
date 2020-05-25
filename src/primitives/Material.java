package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;

    /**
     * constructor
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * getter
     * @return _kD
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter
     * @return _kS
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter
     * @return _nShininess
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
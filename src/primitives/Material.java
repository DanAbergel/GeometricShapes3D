package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;
    private final double _kr;
    private final double _kt;

    public Material(Material material) {
        this(material._kD, material._kS, material._nShininess, material._kt, material._kr);
    }
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }
    public Material(double kD, double kS, int nShininess, double kt, double kr) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
        this._kt = kt;
        this._kr = kr;
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
    public double get_kS()
    {
        return _kS;
    }

    /**
     * getter
     * @return _nShininess
     */
    public int get_nShininess() {

        return _nShininess;
    }
    public double getKr() {
        return _kr;
    }

    public double getKt() {
        return _kt;
    }

}
package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;
    private final double _kr;
    private final double _kt;
//----------Constructors of the class------------------

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
     * kD: represent the concentration of "diffusion" of the Color in the space
     * kS:  is for specular component
     * nShininess: depend of kS if he grows as ks shrinks
     * kr: is for reflexion component
     * kt: is for reflection component
     */
    public double get_kD() { return _kD; }
    public double get_kS() { return _kS; }
    public int get_nShininess() { return _nShininess; }
    public double getKr() { return _kr; }
    public double getKt() { return _kt; }

}
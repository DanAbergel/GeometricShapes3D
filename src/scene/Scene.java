package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Rhe Scene class is the set of all elements in a scene
 * Name of the scene
 * The color of the background of the scene
 * The ambient light of the scene
 * All geometries which exists in ths scene
 * A camera which see all the scene
 * and a list of all lights which are in the scene
 * */
public class Scene {
    String _name;
    Color _background;
    double _distance;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    List<LightSource> _lights;

    /**
     * Constructor of Scene with only name parameter
     * Initialize the lists of geometries and lights
     * @param _Name is the name of the scene
     * */
    public Scene(String _Name) {
        _name = _Name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    public List<LightSource> get_lights() {
        return _lights;
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setBackground(Color _background) {
        this._background = _background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void addGeometries(Intersectable...geometries){
        _geometries.add(geometries);
    }

    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
    }

    public double getDistance() {
        return _distance;
    }
    public void setDistance(double dist){
        _distance=dist;
    }
}

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

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;
    List<LightSource> _lights;

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

    public double getDistance() {

        return _distance;
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

    public void setDistance(double _distance) {

        this._distance = _distance;
    }

    public void addGeometries(Intersectable...geometries){
        _geometries.add(geometries);
    }

    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
    }


}

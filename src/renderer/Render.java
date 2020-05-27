package renderer;

import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;
/**
 * Dan Abergel and Joshua Lalou
 this class aims to represent the whole structure
 */
public class Render {
   private Scene scene;
   private ImageWriter image;
   public Render(ImageWriter _imageWriter, Scene _scene)
   {
        this.image = _imageWriter;
        this.scene = _scene;
   }
    public void writeToImage() {
        image.writeToImage();
    }
    /**
     * Calculate the color intensity in a point
     *
     * @param geoPoint intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint) {
        Color resultColor;
        Color ambientLight = scene.getAmbientLight().getIntensity();
        Color emissionLight = geoPoint.geometry.getEmission();
        resultColor = ambientLight;
        resultColor = resultColor.add(emissionLight);
        List<LightSource> lights = scene.get_lights();
        Material material = geoPoint.geometry.getMaterial();
        Vector v = geoPoint.point.subtract(scene.getCamera().getPlace()).normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point).normalize();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (lights != null) {
            for (LightSource lightSource : lights) {
                Vector l = lightSource.getL(geoPoint.point).normalize();
                if (n.dotProduct(l)*n.dotProduct(v) > 0) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    Color diffuse = calcDiffusive(kd, l, n, lightIntensity);
                    Color specular = calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    resultColor = resultColor.add(diffuse,specular);
                }
            }
        }
        return resultColor;
    }
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        Vector r = l.substract(n.Scale(l.dotProduct(n) * 2));
        return lightIntensity.scale(ks * Math.pow(Math.max(0, v.Scale(-1).dotProduct(r)), nShininess));
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param _interval The interval between the lines.
     */
    public void printGrid(int _interval, java.awt.Color _color)
    {
        for (int i = 0; i < this.image.getNx(); ++i)
            for (int j = 0; j < this.image.getNy(); ++j)
            {
                if (j % _interval == 0 || i % _interval == 0)
                    image.writePixel(j, i, _color);
            }
    }
    /**
     * Finding the closest point to the P0 of the camera.
     *
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */

    private Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> intersectionPoints) {
        Intersectable.GeoPoint result = null;
        double minDistance = Double.MAX_VALUE;
        Point3D p0 = this.scene.getCamera().getPlace();
        if (intersectionPoints == null)
            return null;
        for (Intersectable.GeoPoint geoPoint : intersectionPoints) {
            double distance = p0.distance(geoPoint.point);
            if (distance < minDistance) {
                minDistance = distance;
                result = geoPoint;
            }
        }
        return result;
    }
    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage()
    {
        //pixels grid
        for (int i=0;i<image.getNx();i++)
        {
            for (int j=0;j<image.getNy();j++)
            {
                Ray ray=scene.getCamera().constructRayThroughPixel(image.getNx(),image.getNy(),j,i,scene.getDistance(),image.getWidth(),image.getHeight());
                List<Intersectable.GeoPoint> intersectionPoints=scene.getGeometries().findIntersections(ray);
                if (intersectionPoints==null)
                    image.writePixel(j,i,scene.getBackground().getColor());
                else
                {
                    Intersectable.GeoPoint closestPoint=getClosestPoint(intersectionPoints);
                    image.writePixel(j,i,calcColor(closestPoint).getColor());
                }
            }
        }
    }

}

package renderer;
import elements.Camera;
import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Dan Abergel and Joshua Lalou
 this class aims to represent the whole structure
 */
public class Render {

   private static final int MAX_CALC_COLOR_LEVEL = 10;
   private static final double MIN_CALC_COLOR_K = 0.001;
   private static final double DELTA = 0.1;
   private Scene scene;
   private ImageWriter image;
   public Render(ImageWriter _imageWriter, Scene _scene) {
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
//    private Color calcColor(Intersectable.GeoPoint geoPoint) {
//        Color resultColor;
//        Color ambientLight = scene.getAmbientLight().getIntensity();
//        Color emissionLight = geoPoint.geometry.getEmission();
//        resultColor = ambientLight;
//        resultColor = resultColor.add(emissionLight);
//        List<LightSource> lights = scene.get_lights();
//        Material material = geoPoint.geometry.getMaterial();
//        Vector v = geoPoint.point.subtract(scene.getCamera().getPlace()).normalize();
//        Vector n = geoPoint.geometry.getNormal(geoPoint.point).normalize();
//        int nShininess = material.get_nShininess();
//        double kd = material.get_kD();
//        double ks = material.get_kS();
//        for (LightSource lightSource : lights) {
//            if(unshaded(n,geoPoint,lightSource)) {
//            if (lights != null) {
//                    Vector l = lightSource.getL(geoPoint.point).normalize();
//                    if (n.dotProduct(l) * n.dotProduct(v) > 0) {
//                        Color lightIntensity = lightSource.getIntensity(geoPoint.point);
//                        Color diffuse = calcDiffusive(kd, l, n, lightIntensity);
//                        Color specular = calcSpecular(ks, l, n, v, nShininess, lightIntensity);
//                        resultColor = resultColor.add(diffuse, specular);
//                    }
//                }
//            }
//        }
//        return resultColor;
//    }

    /**
     * Calculating the color by a Point
     *
     * @param gp    Point3D the point that in the calculation
     * @param inRay Ray ray
     * @param level int the level of the color
     * @param k     double value to scale
     * @return Color the calculated color
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray inRay, int level, double k) {

        if (level == 1 || k < MIN_CALC_COLOR_K) return Color.BLACK;

        Color result = gp.geometry.getEmission();//_scene.getAmbientLight().getIntensity();
        List<LightSource> lights = scene.get_lights();
        Vector v = gp.point.subtract(scene.getCamera().getPlace()).normalize();
        Vector n = gp.geometry.getNormal(gp.point);

        Material material = gp.geometry.getMaterial();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (scene.get_lights() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(gp.point);
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    double ktr = transparency(lightSource,l,n,gp);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color ip = lightSource.getIntensity(gp.point).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip)
                        );
                    }
                }
            }

            double kR = gp.geometry.getMaterial().getKr();
            double kkR = k * kR;
            if (kkR > MIN_CALC_COLOR_K) {
                Ray reflectedRay = constructReflectedRay(gp.point, inRay, n);
                Intersectable.GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
                if (reflectedPoint != null) result = result.add(
                        calcColor(reflectedPoint,
                                reflectedRay,
                                level - 1, kkR).scale(kR));
            }

            double kT = gp.geometry.getMaterial().getKt();
            double kkT = k * kT;
            if (kkT > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(gp.point, inRay, n);
                Intersectable.GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
                if (refractedPoint != null) result = result.add(
                        calcColor(refractedPoint,
                                refractedRay,
                                level - 1, kkT).scale(kT));
            }
        }
        return result;
    }
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.Scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param _interval The interval between the lines.
     */
    public void printGrid(int _interval, java.awt.Color _color) {
        for (int i = 0; i < this.image.getNx(); ++i)
            for (int j = 0; j < this.image.getNy(); ++j) {
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
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this.scene.getCamera().getPlace();

        for (Intersectable.GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.point;
            double distance = p0.distance(pt);
            if (distance < mindist) {
                mindist = distance;
                result = geo;
            }
        }
        return result;
    }
    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {
        double sumColorR, sumColorG, sumColorB;// sum rgb colors to do averages
        double colorR, colorG, colorB; //rgb colors

        Camera camera = scene.getCamera();
        double screenDistance = scene.getDistance();
        double screenWidth = image.getWidth();
        double screenHeight = image.getHeight();
        Intersectable geometries = scene.getGeometries();
        java.awt.Color background = scene.getBackground().getColor();
        int nX = image.getNx();
        int nY = image.getNy();


        for (int j = 0; j < nY; j++) {
            for (int i = 0; i < nX; i++) {
                sumColorR = sumColorG = sumColorB = 0.0;
                int count_rays = 50;//numbers of rays per pixel

                List<Ray> rays = new ArrayList<>();
                rays.add(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight,true));

                for(int k= 0;k < count_rays-1;k++)
                    rays.add(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight, true));

                Color tempColor;
                for(Ray ray : rays){
                    Intersectable.GeoPoint closestPoint = findCLosestIntersection(ray);
                    if(closestPoint == null)
                    {
                        //_imageWriter.writePixel(j, i, background);
                        count_rays--;
                        continue;
                    }

                    tempColor = new Color(calcColor(closestPoint, ray).getColor());

                    colorR = tempColor.getColor().getRed();
                    colorG = tempColor.getColor().getGreen();
                    colorB = tempColor.getColor().getBlue();

                    sumColorR += colorR;// * colorR;
                    sumColorG += colorG;// * colorG;
                    sumColorB += colorB;// * colorB;


                }
                image.writePixel(j, i, new Color((sumColorR/count_rays), (sumColorG/count_rays), (sumColorB/count_rays)).getColor());

            }
        }
    }
    /**
     * Calculating the color by a Point
     *
     * @param gp  Point3D the point that in the calculation
     * @param ray Ray ray
     * @return Color the calculated color
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(
                scene.getAmbientLight().getIntensity());
    }

//    /**
//     *
//     * @param lightSource
//     * @param n
//     * @param gp
//     * @return
//     */
//    private boolean unshaded(Vector l, Vector n, Intersectable.GeoPoint gp, LightSource lightSource) {
//        Vector lightDirection = l.Scale(-1); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n);
//
//        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(lightRay);
//
//        if(intersections == null) return true;
//
//        double distance = lightSource.getDistance(gp.point);
//        for(Intersectable.GeoPoint geoP : intersections){
//            if(alignZero(geoP.point.distance(gp.point) - distance) <= 0 &&
//                    geoP.geometry.getMaterial().getKt() == 0) return false;
//
//        }
//
//        return true;
//
//    }
    private Intersectable.GeoPoint findCLosestIntersection(Ray ray){

            if (ray == null) {
                return null;
            }

            Intersectable.GeoPoint closestPoint = null;
            double closestDistance = Double.MAX_VALUE;
            Point3D ray_p0 = ray.getPoint();

            List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(ray);
            if (intersections == null)
                return null;

            for (Intersectable.GeoPoint geoPoint : intersections) {
                double distance = ray_p0.distance(geoPoint.point);
                if (distance < closestDistance) {
                    closestPoint = geoPoint;
                    closestDistance = distance;
                }
            }
            return closestPoint;
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, Intersectable.GeoPoint geopoint) {// transparency(l,n,gp,lightSource);

        Vector lightDirection = l.Scale(-1); // from point to light source
        Vector epsVector=n.Scale(n.dotProduct(lightDirection)>0?DELTA:-DELTA);
        Point3D point=geopoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);


        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return 1d;
        }
        double lightDistance = light.getDistance(geopoint.point);
        double ktr = 1d;
        for (Intersectable.GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }
    /**
     * this function gets a point, a ray and a vector and return the reflected ray
     *
     * @param p   Point3D point
     * @param ray Ray Ray
     * @param n   Vector vector
     * @return Ray reflected ray
     */
    private Ray constructReflectedRay(Point3D p, Ray ray, Vector n) {

        Vector v = ray.getVector();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }

        Vector r = n.Scale(2 * vn).substract(v);
        return new Ray(p, r, n);
    }
    /**
     * this function gets a point and a ray and return the refracted ray
     *
     * @param p     Point3D point
     * @param inRay Ray ray
     * @return Ray the refracted ray
     */
    private Ray constructRefractedRay(Point3D p, Ray inRay, Vector n) {
        return new Ray(p, inRay.getVector(), n);
    }

}

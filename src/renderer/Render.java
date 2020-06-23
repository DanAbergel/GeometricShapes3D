package renderer;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.alignZero;

/**
 * Dan Abergel and Joshua Lalou
 * this class aims to represent the whole structure
 */
public class Render {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double DELTA = 0.1;
    private static final int COUNT_RAYS = 1;//numbers of rays per pixel
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

        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;

        Color result = gp.geometry.getEmission();//_scene.getAmbientLight().getIntensity();
        List<LightSource> lights = scene.get_lights();
        Vector v = inRay.getVector();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return result;

        Material material = gp.geometry.getMaterial();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        if (scene.get_lights() != null) {
            for (LightSource lightSource : lights) {
                Vector l = lightSource.getL(gp.point);
                double nl = alignZero(n.dotProduct(l));
                if (nl * nv > 0) {
                    double ktr = transparency(l, n, gp, lightSource);
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

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coefficient
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
        Camera camera=scene.getCamera();
        Geometries geometries=scene.getGeometries();
        java.awt.Color background= scene.getBackground().getColor();
        double distance=scene.getDistance();
        int Nx=image.getNx();
        int Ny=image.getNy();

        double width =image.getWidth();
        double height=image.getHeight();
        for(int row=0;row<Ny;row++){
            for(int collumn=0;collumn<Nx;collumn++){
                Ray ray =camera.constructRayThroughPixel(Nx,Ny,collumn,row,distance,width,height,false);
                Intersectable.GeoPoint closestPoint=findCLosestIntersection(ray);
                if(closestPoint==null){
                    image.writePixel(collumn,row,background);

                }else{
                    image.writePixel(collumn,row,calcColor(closestPoint,ray).getColor());
                }
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


    private Intersectable.GeoPoint findCLosestIntersection(Ray ray) {
        if (ray == null) return null;

        Intersectable.GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getPoint();

        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(ray);
        if (intersections == null) return null;

        for (Intersectable.GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    private double transparency(Vector l, Vector n, Intersectable.GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.Scale(-1); // from point to light source
        Vector epsVector = n.Scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);//
        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return 1.0;
        double lightDistance = lightSource.getDistance(point);
        double ktr = 1.0;
        for (Intersectable.GeoPoint geoP : intersections) {
            if (alignZero(geoP.point.distance(point) - lightDistance) <= 0) {
                ktr *= geoP.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
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
        if (Util.isZero(vn)) return null;
        Vector r = v.add(n.Scale(-2 * vn));
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

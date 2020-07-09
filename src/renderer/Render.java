package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import primitives.Color;
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
    int numOfRaysSuperSampling = 100;
    int numOfRaysSoftShadow = 100;
    private int numThreads = 1;//num of separate threads
    private final int SPARE_THREADS = 2;
    private boolean print = false;//says if it have to print percentage while processing

    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this.print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this.print) {
                    System.out.println();
                    System.out.printf("\r %02d%%", percents);
                }
            if (percents >= 0)
                return true;
//            if (Render.this.print) {
//                System.out.println();
//                System.out.printf("\r %02d%%", 100);
//            }
            return false;
        }
    }

    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            numThreads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                numThreads = 1;
            else
                numThreads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }


    /**
     * Constructor of the class Render
     * @param _imageWriter the image which will be used for draw the desired image
     * @param _scene the scene which will appear in the image and it is needed for calculate all the
     * intersections with the shapes within this scene
     * */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this.image = _imageWriter;
        this.scene = _scene;
    }

    /**
     * function to define the number of desired rays in super sampling
     * @param num the number of rays which will be calculated for super sampling
     * @return the render object itself for call it at creation of the render object
     * */
    public Render setRaysSuperSampling(int num){
        numOfRaysSuperSampling=num;
        return this;
    }

    /**
     * function to define the number of desired rays in soft shadow
     * @param num the number of rays which will be calculated for soft shadow
     * @return the render object itself for call it at creation of render object
     * */
    public Render setRaysSoftShadow(int num){
        numOfRaysSoftShadow=num;
        return this;
    }

    /**
     * this function call the same function in the class of ImageWriter to draw the desired image
     * */
    public void writeToImage() {
        image.writeToImage();
    }


    private Color calcColor(List<Ray> rays) {
        Color averageColor = new Color(Color.BLACK);
        Intersectable.GeoPoint closestPoint;

        //calculate the color of every intersection point of every ray/
        for (Ray ray : rays) {

            closestPoint = findCLosestIntersection(ray);

            //if there is no intersections for this ray
            if (closestPoint == null) {
                averageColor.add(scene.getBackground());
            } else {
                averageColor.add(calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1.0));
            }
        }
        //the color of the pixel will be the average of all rays/
        averageColor = averageColor.scale(1.0 / rays.size());

        averageColor = averageColor.add(scene.getAmbientLight().getIntensity());
        return averageColor;
    }

    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray inRay, int level, double k) {
        // to recursion stop
        if (level == 1 || k < MIN_CALC_COLOR_K) {
            return Color.BLACK;
        }

        Color result = geoPoint.geometry.get_emission();
        Point3D pointGeo = geoPoint.point;
        // direction of camera to the intersection point:
        Vector v = pointGeo.subtract(scene.getCamera().getPlace()).normalize();
        // the normal vector of geometric object in the goePoint
        Vector n = geoPoint.geometry.getNormal(pointGeo);

        double nv = alignZero(n.dotProduct(v));
        //if v( vector from camera to ward lighted point) orthogonal to n(normal vector)
        if (nv == 0) {
            return result;
        }

        //the material of the geometric object
        Material material = geoPoint.geometry.getMaterial();
        //the shininess of the geometric object
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        double kT = material.getKt();
        double kR = material.getKr();
        // the calculate of k*kR
        double kkr = k * kR;
        // the calculate of k*kT
        double kkt = k * kT;

        result = result.add(getLightSourcesColors(geoPoint, k, result, v, n, nv, nShininess, kd, ks));

        // if the reflection factor after multiplication to the 'k' is bigger than MIN_CALC_COLOR_K
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
            Intersectable.GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                result = result.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kR));
            }
        }
        // if the transparency factor after multiplication to the 'k' is bigger than MIN_CALC_COLOR_K
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
            Intersectable.GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
            if (refractedPoint != null) {
                result = result.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kT));
            }
        }

        return result;
    }

    private Color getLightSourcesColors(Intersectable.GeoPoint geoPoint, double k, Color result, Vector v, Vector n, double nv, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.point;
        List<LightSource> lightSources = scene.get_lights();

        // if the light source collection did not empty
        if (lightSources != null) {
            for (LightSource lightSource : lightSources) {

                Vector l = lightSource.getL(pointGeo);
                double nl = alignZero(n.dotProduct(l));

                // if nv != 0 and nl != 0 and the nl , nv have the same sign
                if (nl * nv > 0) {
                    //if (unshaded(lightSource, l, n, geoPoint)) {

                    double ktr = transparency(l, n, geoPoint, lightSource);

                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(pointGeo).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity)
                        );
                    }
                }
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
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
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
        Camera camera = scene.getCamera();
        double distance = scene.getDistance();
        double width = image.getWidth();
        double height = image.getHeight();
        int Nx = image.getNx();
        int Ny = image.getNy();
        final Pixel MainPixel = new Pixel(Nx, Ny);
        Thread[] threads = new Thread[numThreads];
        for (int i = numThreads - 1; i >= 0; i--) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel(); //Auxiliary pixel object
                while (MainPixel.nextPixel(pixel)) {
                    List<Ray> beamOfRays = (camera.constructRayBeamThroughPixel(pixel.col, pixel.row, numOfRaysSuperSampling, Nx, Ny, width, height, distance));
                    Color colorAverage = Color.BLACK;
                    for (Ray ray : beamOfRays) {
                        Intersectable.GeoPoint closestPoint = findCLosestIntersection(ray);
                        if (closestPoint == null)
                            colorAverage = colorAverage.add(scene.getBackground());
                        else
                            colorAverage = colorAverage.add(calcColor(closestPoint, ray));
                    }
                    colorAverage = colorAverage.reduce(numOfRaysSuperSampling);
                    image.writePixel(pixel.col, pixel.row, colorAverage.getColor());
                }
            });
        }
        for (Thread thread : threads) thread.start(); //Start all the threads
        //wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (print)
            System.out.printf("\r100%%\n"); //print 100%
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
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D newPoint = gp.point.add(epsVector);
        //shadowRay is the main ray of shadow
        Ray shadowRay = new Ray(newPoint, lightDirection);
        int numOfRays = numOfRaysSoftShadow;

        //initialize a new list for calculate soft shadow rays
        List<Ray> rays = new LinkedList<>();

        //add the first ray in center of light to the list
        rays.add(shadowRay);
        //calculate all others rays to light
        lightSource.findBeamRaysLight(rays, newPoint, --numOfRays);
        double averageKtr = 0;
        for (Ray ray : rays) {
            double ktr = 1.0;
            List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(ray);
            if (intersections != null) {
                double lightDistance = lightSource.getDistance(newPoint);
                for (Intersectable.GeoPoint geoP : intersections) {
                    if (alignZero(geoP.point.distance(newPoint) - lightDistance) <= 0) {
                        ktr *= geoP.geometry.getMaterial().getKt();
                        if (ktr < MIN_CALC_COLOR_K)
                            ktr = 0.0;
                    }
                }
            }
            averageKtr += ktr;
        }
        return averageKtr / numOfRaysSoftShadow;
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
        Vector v = ray.getDirection();
        double vn = v.dotProduct(n);
        if (Util.isZero(vn)) return null;
        Vector r = v.add(n.scale(-2 * vn));
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

        return new Ray(p, inRay.getDirection(), n);
    }

}

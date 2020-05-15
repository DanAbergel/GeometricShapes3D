package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
/**
 * Dan Abergel and יהושע לאלו
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
     * @param point intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Point3D point)
    {
        return scene.getAmbientLight().get_intensity();
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
     * @param intersectionsPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */
    private Point3D getClosestPoint(List<Point3D> intersectionsPoints){
       double distance=Double.MAX_VALUE;
       Point3D P0=scene.getCamera().getPlace();
       Point3D minDistancePoint=null;
       for (Point3D point:intersectionsPoints){
           if(P0.distance(point)<distance)
           {
               minDistancePoint=new Point3D(point);
               distance=P0.distance(point);
           }
       }
       return minDistancePoint;
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
                List<Point3D> intersectionPoints=scene.getGeometries().findIntersections(ray);
                if (intersectionPoints==null)
                    image.writePixel(j,i,scene.getBackground().getColor());
                else
                {
                    Point3D closestPoint=getClosestPoint(intersectionPoints);
                    image.writePixel(j,i,calcColor(closestPoint).getColor());
                }
            }
        }
    }

}

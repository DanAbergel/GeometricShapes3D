package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class Render {
   private Scene scene;
   private ImageWriter image;
   public Render(ImageWriter _imageWriter, Scene _scene)
   {
        this.image = _imageWriter;
        this.scene = _scene;
   }
    private Color calcColor(Point3D point)
    {
        return scene.getAmbientLight().get_intensity();
    }
    public void printGrid(int _interval, java.awt.Color _color)
    {
        for (int i = 0; i < this.image.getNx(); ++i)
            for (int j = 0; j < this.image.getNy(); ++j)
            {
                if (j % _interval == 0 || i % _interval == 0)
                    image.writePixel(j, i, _color);
            }
    }
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
    public void renderImage()
    {
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

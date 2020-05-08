package renderer;

import primitives.Color;
import primitives.Point3D;
import scene.Scene;

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

}

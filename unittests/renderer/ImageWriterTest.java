package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;
public class ImageWriterTest {

    @Test
    public void ImageTest()
    {
        String ImageName="Address";
        int width=1600;
        int height=1000;
        int Nx=800;
        int Ny=500;
        ImageWriter image=new ImageWriter(ImageName,width,height,Nx,Ny);
        for (int i=0;i<Nx;i++)
        {
            for(int j=0;j<Ny;j++)
            {
                //if it is the pixel on the grid color it in different color than interior of the case
                if (i % 50 == 0 || j % 50 == 0) {
                    image.writePixel(i, j, Color.PINK);
                } else {
                    image.writePixel(i, j, Color.YELLOW);
                }

            }
        }
        image.writeToImage();
    }

    }

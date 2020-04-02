package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class TubeTest
{
    @Test
   public void getNormal()
    {
        Ray r = new Ray(new Point3D(1,0,0), new Vector(0,1,0));
        Tube t = new Tube(1, r);
        assertEquals(new Vector(1, 0, 0), t.getNormal(new Point3D(2, 0, 0)),"Tube.getNormal() result is wrong");
    }

}
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author yeoshua and Dan
 */

class CylinderTest {

   @Test
   public void testGetNormal() {
       Ray r = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
       Point3D p = new Point3D(1, 0, 0);
       Cylinder cylinder = new Cylinder(5d, r,5d);
       assertEquals(new Vector(0, 0, 1), cylinder.getNormal(p),"Cylinder.getNormal() result is wrong" );
   }
}
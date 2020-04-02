package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * * @author yeoshua and Dan
 */
class PlaneTest {


    @Test
    void getNormal() {
        Plane p1=new Plane(new Point3D(0,1,0),new Point3D(1,0,0),new Point3D(0,0,1));
        Vector v1=p1.getNormal(null);
      // System.out.println(v.toString());
        Plane p2=new Plane(new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        Vector v2=p2.getNormal(null);
       // System.out.println(v.toString());
        Plane p3=new Plane(new Point3D(1,0,0),new Point3D(0,0,1),new Point3D(0,1,0));
        Vector v3=p1.getNormal(null);
        assertEquals(v1,v3);//pas la meme direction
        assertNotEquals(v1,v2);
    }
}
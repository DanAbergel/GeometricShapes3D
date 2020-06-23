package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.function.Supplier;

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
        assertEquals(v1,v3);
        assertNotEquals(v1,v2);
    }


    @Test
    void TestFindIntersections() {
        //TC01 The ray cross the plane without passing by the center of axes
        Plane plane=new Plane(new Point3D(1,0,1),new Vector(1,1,1));
        Point3D trueResult1=new Point3D(1,0,1);
        Ray ray1=new Ray(new Point3D(-1,0,0),new Vector(2,0,1));
        Intersectable.GeoPoint result1=plane.findIntersections(ray1).get(0);
        assertEquals(trueResult1,result1.point);

        //TC02 The ray is parallel to the plane (0 points)
        Plane plane2= new Plane(new Point3D(0,0,0),new Point3D(1,0,0),new Point3D(0,1,0));
        Ray ray2=new Ray(new Point3D(0,0,1),new Vector(1,1,4));
        assertEquals(null, plane2.findIntersections(ray2));

        // =============== Boundary Values Tests =================
        // TC03 : ray into the plane (0 points)
        Ray ray3=new Ray(new Point3D(0.5,0.5,0),new Vector(1,1,0));
        assertEquals(null, plane2.findIntersections(ray3));

        // TC04 : ray parralele to  the plane (0 points)
        Ray ray4=new Ray(new Point3D(0,0,2),new Vector(1,2,2));
        assertEquals(null, plane2.findIntersections(ray4));

        // TC05 : cast 1: before the plane ray orthogonal to  the plane
        Ray ray5=new Ray(new Point3D(1,1,-2),new Vector(1,1,2));
        Point3D point5=new Point3D(2.0,2.0,0.0);
        assertEquals(point5, plane2.findIntersections(ray5).get(0).point);

        // TC06 : cast 2: after the plane ray orthogonal to  the plane (0 points)
        Ray ray6=new Ray(new Point3D(1,1,2),new Vector(1,1,4));
        assertEquals(null, plane2.findIntersections(ray6));

        // TC07 : cast 3: in the plane ray orthogonal to  the plane (0 points)
        Ray ray7=new Ray(new Point3D(1,0,0),new Vector(1,0,1));
        assertEquals(null, plane2.findIntersections(ray7));

        // TC08 : ray not parallel and not orthogonal to  the plane (0 points)
        Ray ray8=new Ray(new Point3D(1,0,0),new Vector(4,4,1));
        assertEquals(null, plane2.findIntersections(ray8));

    }
}
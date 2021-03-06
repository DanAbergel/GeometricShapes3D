package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
/**
 * @author yeoshua and Dan
 */
public class SphereTest {
    @Test
   public void getNormalTest() {
        Sphere sp = new Sphere(1, new Point3D(0,0,1));
        assertNotEquals(new Vector(0,0,1),sp.getNormal(new Point3D(0,1,1)));
        System.out.println(sp.getNormal(new Point3D(0,1,1)));
    }
    @Test
    public void TestFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));


        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.getX().get()> result.get(1).point.getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), List.of(result.get(0).point,result.get(1).point));


        //TC03: Ray starts inside the sphere (1 point)
        Point3D p4=new Point3D(2,0,0);
        Ray ray=new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0));
        List<Intersectable.GeoPoint> result2 = sphere.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result2.size());
        assertEquals("Ray crosses sphere two time", p4, result2.get(0).point);

        // TC04: Ray starts after the sphere (0 points)
        Ray ray2=new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0));
        List<Intersectable.GeoPoint> result3 = sphere.findIntersections(ray2);
        assertEquals("on verra",null,result3);


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p5=new Point3D(1.8,0.6,0);
        Ray ray11=new Ray(new Point3D(0, 0, 0), new Vector(3, 1, 0));
        List<Intersectable.GeoPoint> result11 = sphere.findIntersections(ray11);
        assertEquals("Wrong number of points", 1, result2.size());
        assertEquals("Ray crosses sphere two time", p5, result11.get(0).point);

        // TC12: Ray starts at sphere and goes outside (0 points)
        Point3D p12=new Point3D(2,0,0);
        Ray ray12=new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0));
        List<Intersectable.GeoPoint> result12 = sphere.findIntersections(ray12);
        assertEquals("Ray crosses sphere two time",null , result12);

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point3D p13a = new Point3D(2, 0, 0);
        Point3D p13b = new Point3D(0, 0, 0);
        Ray ray13=new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0));
        List<Intersectable.GeoPoint> result13 = sphere.findIntersections(ray13);
        assertEquals(" don't goes through the center",ray13.getPoint(2),sphere._center);
        assertEquals("Wrong number of points", 2, result13.size());
        assertEquals("Ray crosses sphere two time", List.of(p13b,p13a), List.of(result13.get(0).point,result13.get(1).point));

        // TC14: Ray starts at sphere and goes inside (1 points)
        Point3D p14 = new Point3D(2, 0, 0);
        Ray ray14=new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0));
        List<Intersectable.GeoPoint> result14 = sphere.findIntersections(ray14);
        assertEquals(" don't goes through the center",ray14.getPoint(1),sphere._center);
        assertEquals("Wrong number of points", 1, result14.size());
        assertEquals("Ray crosses sphere two time", p14, result14.get(0).point);


        // TC15: Ray starts inside (1 points)
        assertEquals("Line through O, ray from inside sphere",
                new Point3D(1, 1, 0),
                sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 1, 0))).get(0).point);

        // TC16: Ray starts at the center (1 points)
        assertEquals(  "Line through O, ray from O", List.of(new Intersectable.GeoPoint(sphere,new Point3D(1, 1, 0))), sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));


        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("Line through O, ray from sphere outside", sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertNull("Line through O, ray outside sphere", sphere.findIntersections(new Ray(new Point3D(5, 5, 5), new Vector(0, 1, 0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("Tangent line, ray before sphere", sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))));

        // TC20: Ray starts at the tangent point
        assertNull("Tangent line, ray at sphere", sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))));

        // TC21: Ray starts after the tangent point
        assertNull("Tangent line, ray after sphere", sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))));

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull("Ray orthogonal to ray head -> O line", sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1))));

    }
}
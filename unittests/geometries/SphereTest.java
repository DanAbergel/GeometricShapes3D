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
    public void getNormal() {
        Sphere sphere = new Sphere(Point3D.ZERO, 5);
        Vector normal = sphere.getNormal(new Point3D(1, 1, 1));
        assertEquals("error", normal, new Vector(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)));
    }

    @Test
    public void TestFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

//        // TC01: Ray's line is outside the sphere (0 points)
//        assertEquals("Ray's line out of sphere", null,
//                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));


        // TC02: Ray starts before and crosses the sphere (2 points)
//        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
//        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
//        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
//                new Vector(3, 1, 0)));
//        assertEquals("Wrong number of points", 2, result.size());
//        if (result.get(0).getX().get()> result.get(1).getX().get())
//            result = List.of(result.get(1), result.get(0));
//        assertEquals("Ray crosses sphere", List.of(p1, p2), result);


//        //TC03: Ray starts inside the sphere (1 point)
//        Point3D p4=new Point3D(2,0,0);
//        Ray ray=new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0));
//        List<Point3D> result2 = sphere.findIntersections(ray);
//        assertEquals("Wrong number of points", 1, result2.size());
//        assertEquals("Ray crosses sphere two time", List.of(p4), result2);

        // TC04: Ray starts after the sphere (0 points)
//        Ray ray=new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0));
//        List<Point3D> result2 = sphere.findIntersections(ray);
//        assertEquals("on verra",null,result2);


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
//        Point3D p4=new Point3D(1.8,0.6,0);
//        Ray ray=new Ray(new Point3D(0, 0, 0), new Vector(3, 1, 0));
//        List<Point3D> result2 = sphere.findIntersections(ray);
//        assertEquals("Wrong number of points", 1, result2.size());
//        assertEquals("Ray crosses sphere two time", List.of(p4), result2);

        // TC12: Ray starts at sphere and goes outside (0 points)
//        Point3D p4=new Point3D(2,0,0);
//        Ray ray=new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0));
//        List<Point3D> result2 = sphere.findIntersections(ray);
//        assertEquals("Ray crosses sphere two time",null , result2);

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        //        Point3D p4=new Point3D(2,0,0);
//        Point3D p1 = new Point3D(2, 0, 0);
//        Point3D p2 = new Point3D(0, 0, 0);
//        Ray ray2=new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0));
//        List<Point3D> result2 = sphere.findIntersections(ray2);
//        System.out.println(ray2.getTargetPoint(2));
//        System.out.println(sphere._center);
//        System.out.println(result2);
//        assertEquals(" don't goes through the center",ray2.getTargetPoint(2),sphere._center);
//        assertEquals("Wrong number of points", 2, result2.size());
//        assertEquals("Ray crosses sphere two time", List.of(p2,p1), result2);


    }
}
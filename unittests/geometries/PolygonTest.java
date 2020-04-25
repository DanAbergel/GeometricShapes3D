package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @author yeoshua and Dan
 */
class PolygonTest {
    /**
     * Test method for
     * {@link geometries.Polygon# Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(
                    new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0),
                    new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

            // TC02: Wrong vertices order
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                        new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
                fail("Constructed a polygon with wrong order of vertices");
            } catch (IllegalArgumentException e) {
            }

            // TC03: Not in the same plane
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 2, 2));
                fail("Constructed a polygon with vertices that are not in the same plane");
            } catch (IllegalArgumentException e) {
            }

            // TC04: Concave quadrangular
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
                fail("Constructed a concave polygon");
            } catch (IllegalArgumentException e) {
            }

            // =============== Boundary Values Tests ==================

            // TC10: Vertix on a side of a quadrangular
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
                fail("Constructed a polygon with vertix on a side");
            } catch (IllegalArgumentException e) {
            }

            // TC11: Last point = first point
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 0, 1));
                fail("Constructed a polygon with vertice on a side");
            } catch (IllegalArgumentException e) {
            }

            // TC12: Collocated points
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 1, 0));
                fail("Constructed a polygon with vertice on a side");
            } catch (IllegalArgumentException e) {
            }

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
      //  Vector expected = new Vector(sqrt3, sqrt3, sqrt3);
        // at this point we are assuming that each palne can have
        // two opposite normals.
       // assertTrue(expected.equals(pl.getNormal(null)) || expected.equals(pl.getNormal(null).Scale(-1)));
     assertEquals( new Vector(sqrt3, sqrt3, sqrt3).Scale(-1), pl.getNormal(new Point3D(0, 0, 1)));

    }

    @Test
    public void findIntersectionsTests() {
        Triangle t1=new Triangle(new Point3D(0,0,4),new Point3D(0,0,0),new Point3D(4,0,0));
    Triangle t2=new Triangle(new Point3D(5,0,0),new Point3D(0,-4,0),new Point3D(0,0,5));
    Ray r1=new Ray(new Point3D(0,-2,0),new Vector(1,2,1));
    Ray r2=new Ray(new Point3D(0,-2,0),new Vector(-1,1,1));
    Ray r3=new Ray(new Point3D(0,-2,0),new Vector(-1,-1,1));
    Ray r4=new Ray(new Point3D(0,-2,0),new Vector(2,1,1));
    assertEquals(t1.findIntersections(r1).size(),1);
    try {
        t1.findIntersections(r2).size();
    }
        catch (NullPointerException e){
        System.out.println(e+ "good , r2 no Intersections with t1");
    }

        try {
        t1.findIntersections(r3).size();
    }
        catch (NullPointerException e){
        System.out.println("good , r3 no Intersections with t1");
    }

        try {
        t1.findIntersections(r4).size();
    }
        catch (NullPointerException e){
        System.out.println("good , r4 no Intersections with t1");
    }
        try{
        t2.findIntersections(r1).size();
    }
        catch (NullPointerException e){
        System.out.println("good , r1 no Intersections with t2");
    }
        try{
        t2.findIntersections(r2).size();
    }
        catch (NullPointerException e){
        System.out.println("good , r2 no Intersections with t2");
    }
        try{
        t2.findIntersections(r3).size();
    }
        catch (NullPointerException e){
        System.out.println("good , r3 no Intersections with t2");
    }

        try{

        assertEquals("",t2.findIntersections(r4).size(),1);

    }
        catch (NullPointerException e){
        System.out.println("good , r4 no Intersections with t2");
    }

}
}


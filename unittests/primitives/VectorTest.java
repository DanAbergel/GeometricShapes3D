package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest
{

     @Test
     public void testConstructorUsesCoordinates()
     {
         // ============ Boundary Value Analysis ==============
         // test zero vector c-tor with coordinates
         try {
             new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0));
             fail("ERROR: zero vector does not throw an exception");
         } catch (Exception e)
         {
         }
     }
    @Test
    void add()
    {
       Vector v1=new Vector(0.0,1.0,0.0);
       Vector v2=new Vector(1.0,0.0,0.0);

       Vector v3=v1.add(v2);
       assertEquals(new Vector(1.0,1.0,0.0),v3);
    }
    @Test
    void testscale() {
        Vector v1=new Vector(1.0,1.0,1.0);
        Vector v=v1.Scale(1);
        assertEquals(v,v1);
        v1=v1.Scale(2);
        assertEquals(new Vector(2.0,2.0,2.0),v1);
        v1=v1.Scale(-2);
        assertEquals(new Vector(-4.0,-4.0,-4.0),v1);
    }
    @Test
    void subtract()
    {

        Vector v1=new Vector(1.0,1.0,1.0);
        Vector v2=new Vector(-1.0,-1.0,-1.0);
        v1=v1.substract(v2);
        System.out.println("v1 = "+v1.toString());
        assertEquals(new Vector(2.0,2.0,2.0),v1);
    }

        @Test
    public void dotProduct()
        {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // test Dot-Product for orthogonal vectors
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        // test Dot-Product value for vectors
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
        }

    @Test
    public void testCrossProduct()
    {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue( isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    public void length()
    {
       // Vector v1 = new Vector(1, 2, 3);
        // test length
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");

    }
    @Test
    public void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        assertTrue( isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }
    @Test
    public void normalize()
    {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals ( vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
        //uses isZero function to compare with more accuracy

    }
    @Test
    public void normalized()
    {
        //tests that normalized creates a new vector
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse(u == v, "ERROR: normalizated() function does not create a new vector");

    }


}
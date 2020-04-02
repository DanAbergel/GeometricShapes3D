package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
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
    void dotProduct() {
    }

    @Test
    void crossProduct() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
    }


}
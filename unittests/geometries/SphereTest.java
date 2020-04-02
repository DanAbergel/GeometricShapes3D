package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
}
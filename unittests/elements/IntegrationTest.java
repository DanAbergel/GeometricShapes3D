package elements;

import org.junit.Test;


import primitives.*;

import geometries.*;
import elements.*;


import java.util.List;
import static org.junit.Assert.*;

/**
 * testing the intersection between the view plane and the differents forms
 */

public class IntegrationTest {
    //=====================================initialisation of cameras that will use bH=======================================
    Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera camera2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    //======================================SPHERE CAMERA TEST===============================================================
    @Test
    public void WithSphere1() {
        //TC01 first cast of maavada we need to find 3 intection with the sphere with radius 1
        Sphere sphere = new Sphere( new Point3D(0, 0, 3),1);
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 2, count);

    }
    @Test
    public void WithSphere2() {
        //TC02 second  cast of maavada we need to find 18 intections with the sphere with radius 2.5
        Sphere sphere = new Sphere( new Point3D(0, 0, 2.5),2.5);
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 18, count);

    }
    @Test
    public void WithSphere3() {
        //TC03 third  cast of maavada we need to find 10 intections with the sphere with radius 2
        Sphere sphere = new Sphere( new Point3D(0, 0, 2),2);
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 10, count);

    }
    @Test
    public void Withsphere4() {
        //TC04 fourth   cast of maavada we need to find  intections with the sphere with radius 4
        Sphere sphere = new Sphere( new Point3D(0, 0, 2),4);
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 9, count);

    }
    @Test
    public void Withsphere5() {
        //TC05 fifth   cast of maavada we need to find  intections with the sphere with radius 0.5
        Sphere sphere = new Sphere( new Point3D(0, 0, -1),0.5);
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sphere.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 0, count);

    }
    //====================================PLANE TEST CAMERA=============================================================
    @Test
    public void WithPlane() {
        //TC01 first  cast of maavada we need to find  intections with the plane
        Plane plane = new Plane(new Point3D(0,0,1),new Point3D(1,1,1),new Point3D(1,0,1));
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 9, count);

    }
    @Test
    public void WithPlane3() {
        //TC03 Third  cast of maavada we need to find  intections with the plane
        Plane plane = new Plane(new Point3D(0,0,1),new Point3D(1,1,-1),new Point3D(1,0,1));
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 6, count);

    }
    @Test
    public void WithPlane2() {
        //TC02 second  cast of maavada we need to find  intections with the plane
        Plane plane = new Plane(new Point3D(0,0,1),new Point3D(1,1,0.5),new Point3D(1,0,1));
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 9, count);

    }
    //=====================================================TRIANGLE======================================================
    @Test
    public void Withtriangle() {
        //TC01 first  cast of maavada we need to find  intections with the triangle
        Triangle triangle = new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 1, count);

    }
    @Test
    public void Withtriangle2() {
        //TC02 second  cast of maavada we need to find  intections with the plane
        Triangle triangle = new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        List<Intersectable.GeoPoint> results;
        int count = 0;
        int Nx = 3;
        int Ny = 3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("fail", 2, count);

    }

}

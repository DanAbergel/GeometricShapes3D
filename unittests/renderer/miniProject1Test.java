package renderer;
import elements.*;
import geometries.*;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.awt.*;

public class miniProject1Test {
    /**
     * Produce a picture of without any improvements
     */

    @org.junit.Test
    public void JossTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new primitives.Color((Color.black)));
        scene.setAmbientLight(new AmbientLight(new primitives.Color((Color.black)), 0));

        Material materialDefault = new Material(0.5,0.5,30);
        primitives.Color cubeColor = new primitives.Color(200, 0, 100);
        scene.addGeometries(
                new Sphere(new primitives.Color(255,0,0), new Material(0.5, 0.5, 30, 0.7, 0),
                        10, new Point3D(0, 0, 0)),
                new Sphere(new primitives.Color((Color.black)), materialDefault,
                        15, new Point3D(-20, -5, 20)),
                new Sphere(new primitives.Color(59, 189, 57), materialDefault,
                        20, new Point3D(-30, -10, 60)),
                new Sphere(new primitives.Color(java.awt.Color.blue), new Material(0.5, 0.5, 30, 0.6, 0),
                        30, new Point3D(15, -20, 70)),
                new Sphere(new primitives.Color(java.awt.Color.red), new Material(0.5, 0.5, 30, 0.6, 0),
                        10, new Point3D(-50, 0, 0)),


                new Plane(new primitives.Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 30, 0, 0.5),
                        new Point3D(-20, 10, 100), new Point3D(-30, 10, 140), new Point3D(15, 10, 150)),
                //create cube
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(80, -30, -10), new Point3D(60, -30, 30)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(80, -30, -10), new Point3D(40, -30, -30),
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(20, -30, 10), new Point3D(60, -30, 30),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(40, 10, -30), new Point3D(80, 10, -10),
                        new Point3D(60, 10, 30), new Point3D(20, 10, 10)),
                new Polygon(cubeColor, materialDefault,
                        new Point3D(60, -30, 30), new Point3D(80, -30, -10),
                        new Point3D(80, 10, -10), new Point3D(60, 10, 30))
        );

        scene.addLights(new spotLight(new primitives.Color(java.awt.Color.WHITE),
                        new Point3D(-100, -10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7),
                new PointLight(new primitives.Color(Color.green),
                        new Point3D(-50, -95, 0), 1,0.00005, 0.00005)
                //new DirectionalLight(new Color(java.awt.Color.WHITE),
                //       new Vector(-1,1,-1))
        );

        ImageWriter imageWriter = new ImageWriter("Josstest", 200, 200, 800, 800);
        Render render = new Render(imageWriter, scene).setMultithreading(10).setDebugPrint().setRaysSuperSampling(100).setRaysSoftShadow(70);
        render.renderImage();
        render.writeToImage();
    }


}


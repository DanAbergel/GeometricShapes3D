package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
/**
 *@author Dan Abergel and Joss Lalou
 * Class Geometries is used for define function to work on a set of geometries in the same scene
 * This class have only a list of geometries already initialized in other classes
 */
public class Geometries implements Intersectable {

    List<Intersectable> geometriesList;
    /**
     * Constructor of Geometries with no parameters
     * It initialize the list of geometries as ArrayList because it easier to add elements
     * of the list than if it would be an LinkedList
     * */
    public Geometries() {
        geometriesList = new ArrayList<Intersectable>();
    }

    /**
     * Constructor of Geometries with a list of geometries as parameter
     * As in the default constructor it initialize the list with an ArrayList
     * */
    public Geometries(Intersectable... geometries) {
        geometriesList = new ArrayList<>();
        add(geometries);
    }

    /**
     * Function add adds all the geometries which are in the list to the list of this instance Geometries
     * @param geometries is a list of geometries to add to this list
     * */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries)
        {
            geometriesList.add(geometry);
        }
    }

     /**
      *Function findIntersections override the function of Intersectable interface
      * It takes two parameters
      * @param ray is the ray which may cross one of the geometries in the geometriesList
      * @param max is the max distance between the camera and one geometry
      * @return a list of all intersection points between the ray and all the geometries in the geometriesList
      * * */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        if(geometriesList.size() == 0)
            return null;
        else
        {
            List<GeoPoint> point3DS = new ArrayList<GeoPoint>();
            for(int i = 0; i< geometriesList.size(); i++) {
                var points = geometriesList.get(i).findIntersections(ray);
                if(points != null) {
                    for (int j = 0; j < points.size(); j++) {
                        point3DS.add(points.get(j));
                    }
                }
            }
            return point3DS.size() == 0 ? null : point3DS;
        }
    }


}
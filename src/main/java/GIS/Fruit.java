package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class Fruit {
    //This class represents a non-moveable target at a known geo location.
    /**
     * should be a Gis element.
     */
    private GisElementImpl myFruit;
    private Point3D fruit_coords;
    //have static coordinates
    public Fruit(Point3D otherFruit){
        this.myFruit = new GisElementImpl(otherFruit,myFruit.getData());
        this.fruit_coords = new Point3D(otherFruit);
    }
    public Geom_element getCoords(){
        return myFruit.getGeom();
    }
}

package GIS;

import Geom.Point3D;

public class PackMan {
    //This class represents a Robot , defiend by orientations loacation and movement
    //orientation
    //movement
    //	location
    //	speed
    //	eating radius
    /**
     * should be based gis element with add ons such as movement , and other required edits.
     */

    private double speed;
    private double eating_radius;
    private GIS_element myPackMan;

    public PackMan(double other_speed , double other_radius ,Point3D coordinates){
        speed = other_speed;
        eating_radius = other_radius;
        myPackMan = new GisElementImpl(coordinates,myPackMan.getData());
    }

    public double getSpeed() {
        return speed;
    }

    public double getEating_radius() {
        return eating_radius;
    }

    public GIS_element getMyPackMan() {
        return myPackMan;
    }
}

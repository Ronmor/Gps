package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class Fruit {

    private final int id;
    private final Point3D coordinates;
    private double weight;

    public Fruit(int id, Point3D coordinates, double weight) {
        this.id = id;
        this.coordinates = coordinates;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public Point3D getCoordinates() {
        return coordinates;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

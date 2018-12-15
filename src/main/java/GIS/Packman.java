package GIS;

import Geom.Point3D;

public class Packman {

    private final int id;
    private Point3D coordinates;
    private final double speed;
    private double eatingRadius;
    private double score = 0;

    public Packman(int id, Point3D coordinates, double speed , double eatingRadius){
        this.id = id;
        this.coordinates = coordinates;
        this.speed = speed;
        this.eatingRadius = eatingRadius;
    }

    public int getId() {
        return id;
    }

    public Point3D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point3D coordinates) {
        this.coordinates = coordinates;
    }

    public double getSpeed() {
        return speed;
    }

    public double getEatingRadius() {
        return eatingRadius;
    }

    public void setEatingRadius(double eatingRadius) {
        this.eatingRadius = eatingRadius;
    }

    public double getScore() {
        return score;
    }

    public void addScore(double toAdd) {
        score += toAdd;
    }


}

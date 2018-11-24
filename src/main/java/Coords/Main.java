package Coords;

import Geom.Point3D;

import java.util.Arrays;

import static Geom.Point3D.d2r;

public class Main {

    public static void main(String[] args){
        MyCoords myCoords = new MyCoords();
        Point3D gps = new Point3D(32.103315,35.209039,670);
        Point3D gps1 = new Point3D(32.106352,35.205225,650);

        double[] test = myCoords.azimuth_elevation_dist(gps,gps1);
        Arrays.toString(test);
        System.out.println(test);
    }
}

package Coords;

import Geom.Point3D;

public class Main {

    public static void main(String[] args){
        MyCoords myCoords = new MyCoords();
        Point3D building9 = new Point3D(32.103315,35.209039,670);
        Point3D humus = new Point3D(32.106352,35.205225,650);
        Point3D toMeter = myCoords.toMeter(building9,humus);
        Point3D toRadd = myCoords.add(building9,toMeter);

       // double[] test = myCoords.azimuth_elevation_dist(gps,gps1);
      //  Arrays.toString(test);

        System.out.println("Debug");
    }
}

package Coords;

import Geom.Point3D;

public class Main {

    public static void main(String[] args){
        MyCoords myCoords = new MyCoords();
        Point3D gps = new Point3D(32.103315,35.209039,670);
        Point3D gps1 = new Point3D(32.106352,35.205225,650);
        Point3D m = myCoords.toMeter(gps1,gps);
        System.out.println(m.toString());
        System.out.println(myCoords.distance3d(gps,gps1));
    }
}

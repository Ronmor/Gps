package Coords;

import Geom.Point3D;
import edu.nps.moves.disutil.CoordinateConversions;
import edu.nps.moves.disutil.CoordinateTransformer;

public class Main {

    public static void main(String[] args){
        MyCoords myCoords = new MyCoords(new Point3D(2.0 ,1.0,1.2));
        myCoords.toSpherical();
        System.out.println(myCoords.toString());
    }
}

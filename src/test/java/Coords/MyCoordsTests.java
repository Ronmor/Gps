package Coords;

import Geom.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyCoordsTests {
    private Point3D building9;
    private Point3D humusLocation;

    MyCoords myCoords = new MyCoords();
    @Before
    public void setup(){
        // Coordinates copied from the examples in the attached Exel file.
        building9 = new Point3D(32.103315,35.209039 ,670);
        humusLocation = new Point3D(32.106352,35.205225,650);
    }
    @Test
    public void diffTest() {
        Point3D p1 = new Point3D(5, 4);
        Point3D p0 = new Point3D(5, 3);
        Point3D expected = new Point3D(0, 1);
        Point3D actual = myCoords.diff(p1, p0);
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void azimuthTest() {
        Point3D p1 = new Point3D(1, 2, 1.2);
        Point3D p0 = new Point3D(1.8, 0.8, -6);
        Point3D diff = myCoords.diff(p1, p0);
        Point3D azimuth = diff.normalize();
        Point3D expected = new Point3D(-0.109, 0.163, 0.98);
        Assert.assertTrue(azimuth.close2equals(expected, 0.1));
    }

    @Test
    public void azimuthElevationDistanceTest() {
        //Point3D p = new Point3D(7.0710678118655, 53.130102354156	, 45);
      //  Point3D p0 = new Point3D(0, 0, 0);
        double[] d = myCoords.azimuth_elevation_dist(humusLocation, building9);
        Assert.fail();
    }

}

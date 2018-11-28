package Coords;

import Geom.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyCoordsTests {
    private Point3D building9;
    private Point3D humusLocation;
    private Point3D local_vector_in_meter; // Set Coordinates as you want.

    MyCoords myCoords = new MyCoords();

    @Before
    public void setup() {
        // Coordinates copied from the examples in the attached Exel file.
        building9 = new Point3D(32.103315, 35.209039, 670);
        humusLocation = new Point3D(32.106352, 35.205225, 650);
        local_vector_in_meter = new Point3D(337.6989920612881, -359.24920693881893, -20.0);
    }

    @Test
    public void addTest() {
        Point3D actual = myCoords.add(building9, local_vector_in_meter);
        Point3D expected = new Point3D(humusLocation);
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDistance3D() {
        double distance_asAppears_example = 493.0523;
        double actual = myCoords.distance3d(building9, humusLocation);
        Assert.assertEquals(distance_asAppears_example, actual, 0.02);
    }

    @Test
    public void azimuthElevationDistanceTest() {
        double[] azimuth_elevation_dist = myCoords.azimuth_elevation_dist(building9, humusLocation);
        double[] expected = {313.2582, -2.326, 493.0523};
        Assert.assertArrayEquals(expected, azimuth_elevation_dist, 0.05);
    }

    @Test
    public void testVector3D() {
        Point3D actual = myCoords.vector3D(building9, humusLocation);
        Point3D expected = new Point3D(337.6989920612881, -359.24920693881893, -20.0);
        Assert.assertTrue(actual.equals(expected));
    }

    @Test
    public void testIsValid() {
        boolean expected = false;
        Assert.assertEquals(myCoords.isValid_GPS_Point(new Point3D("-181,-92,9800")), expected);
    }

}

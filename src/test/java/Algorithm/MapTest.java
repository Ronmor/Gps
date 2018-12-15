package Algorithm;

import Coords.MyCoords;
import Geom.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapTest {

    private Point3D inPixel;
    private Point3D inPixel1;
    private BufferedImage myImage;
    {
        try {
            String mapPath = "Ariel1.png";
            myImage = ImageIO.read(new File(mapPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Map frameConverter;

    @Before
    public void setUp() {
        inPixel = new Point3D(645,478);
        inPixel1 = new Point3D(353,432);
        frameConverter = new Map();

    }

    @Test
    public void pixelToPoint3D() {
        Point3D expected = new Point3D(32.10979442049724,35.206255140318355);
        Point3D actual = frameConverter.pixelToPoint3D(inPixel,myImage);
        Assert.assertTrue(expected.close2equals(actual,0.00001));
    }

    @Test
    public void point3DToPixel() {
        Point3D actualCoordinates = new Point3D(32.10809746113932,35.20590088832077);
        Point3D actualPixel = frameConverter.point3DToPixel(actualCoordinates,myImage);
        Assert.assertTrue(inPixel1.close2equals(actualPixel,0.00001));
    }

    @Test
    public void distanceBetweenPixels() {
        double expectedDistance = Math.sqrt(87380);
        double actual = frameConverter.distanceBetweenPixels(inPixel1.ix(),inPixel1.iy(),inPixel.ix(),inPixel.iy());
        Assert.assertEquals(expectedDistance,actual,0.00001);
    }

    @Test
    public void azimuthBetweenPixels() {
        double azimuthPix = frameConverter.azimuthBetweenPixels(inPixel1.ix(),inPixel1.iy(),inPixel.ix(),inPixel.iy(),myImage);
        MyCoords myCoords = new MyCoords();
        double azimuthGps = myCoords.azimuth_elevation_dist(frameConverter.pixelToPoint3D(inPixel1,myImage),
                frameConverter.pixelToPoint3D(inPixel,myImage))[0];
        Assert.assertEquals(azimuthGps,azimuthPix,0.0001);
    }
}
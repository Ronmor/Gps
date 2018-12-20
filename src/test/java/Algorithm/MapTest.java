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
    private Point3D point3D;
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
        inPixel = new Point3D(422,237);
        inPixel1 = new Point3D(353,432);
        frameConverter = new Map();
        Point3D point3D = new Point3D(32.10317,35.210);
    }
    @Test
    public void pixelToPoint3D() {
        Point3D expected = new Point3D(32.10979442049724,35.206255140318355);
        Point3D actual = frameConverter.pixelToPoint3D(inPixel,myImage);
        Assert.assertTrue(expected.close2equals(actual,0.01));
    }
    @Test
    public void point3DToPixel() {
        Point3D actualCoordinates = new Point3D(32.1031356881926,35.20859012949533);
        Point3D actualPixel = frameConverter.point3DToPixel(actualCoordinates,myImage);
        double pXdistance = frameConverter.distanceBetweenPixels(inPixel.ix(),inPixel.iy(),actualPixel.ix(),actualCoordinates.iy());
        Assert.assertTrue(actualPixel.close2equals(inPixel,pXdistance));
    }
    @Test
    public void distanceBetweenPixels() {
        double expectedDistance = Math.sqrt(42768.1534);
        double actual = frameConverter.distanceBetweenPixels(inPixel1.ix(),inPixel1.iy(),inPixel.ix(),inPixel.iy());
        Assert.assertEquals(expectedDistance,actual,1);
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
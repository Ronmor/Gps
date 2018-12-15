package Algorithm;

import Coords.MyCoords;
import Geom.Point3D;

import java.awt.image.BufferedImage;

public class Map {
    /**
     * This class represents a Map , Built from an image.
     * with all parameters needed to image's adjustments to a global coordinate system.
     * Class should allow a flexible conversion from a coordinate representation to it's Pixel representation
     * to image and Vice versa
     * also , it should allow calculations such as distance between two points represented as pixels
     * and also the angle between two pixels.
     * define two anchor points , at the top left and bottom down right of an image representing a geo map.
     */

    private final MyCoords myCoords = new MyCoords();
    private final Point3D nwBound = new Point3D(32.106046,35.202574);
    private final  Point3D seBound = new Point3D(32.101858,35.212405);    // read image through a MenuBar object.
    private final Point3D swAnchor = new Point3D(seBound.x(),nwBound.y());
    private final double realHeight = myCoords.distance3d(swAnchor,nwBound);
    private final double realWidth = myCoords.distance3d(swAnchor,seBound);
    private final double actualDiagnalLength = myCoords.distance3d(nwBound,seBound);


    public Point3D pixelToPoint3D(Point3D pixelPoint ,BufferedImage image) {
        double xInmeter = realWidth / image.getWidth();
        double yInmeter = realHeight / image.getHeight();
        return myCoords.add(nwBound,new Point3D(pixelPoint.x()*xInmeter,pixelPoint.y()*yInmeter));
    }

    public Point3D point3DToPixel(Point3D point3D , BufferedImage image) {
        Point3D meter = myCoords.vector3D(nwBound,point3D);
        double pxLat = meter.x()*image.getWidth() / realWidth;
        double pxLon =  meter.y()*image.getHeight() / realHeight;
        return new Point3D(pxLat,pxLon); // x * height/distance3D , y*width/distance3D
    }

    public double distanceBetweenPixels(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }


    public double azimuthBetweenPixels(int x1, int y1, int x2, int y2 , BufferedImage bufferedImage) {
        Point3D px0 = pixelToPoint3D(new Point3D(x1,y1),bufferedImage);
        Point3D px1 = pixelToPoint3D(new Point3D(x2,y2),bufferedImage);
        return myCoords.azimuth_elevation_dist(px0,px1)[0];
    }
    private double diagnalInPixels(BufferedImage bufferedImage){
        return Math.sqrt(Math.pow(bufferedImage.getHeight(),2) + Math.pow(bufferedImage.getWidth(),2) );
    }

}

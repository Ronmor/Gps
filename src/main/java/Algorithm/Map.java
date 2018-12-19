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
    private final Point3D nwBound = new Point3D(32.10619669,35.203089528);
    private final  Point3D seBound = new Point3D(32.101858,35.21180900);    // read image through a MenuBar object.
    private final Point3D swAnchor = new Point3D(seBound.x(),nwBound.y());
    private final Point3D neAnchor = new Point3D(nwBound.x(),seBound.y());
    private final double realHeight = myCoords.distance3d(swAnchor,nwBound);
    private final double realWidth = myCoords.distance3d(swAnchor,seBound);
    private final double actualDiagnalLength = myCoords.distance3d(nwBound,seBound);

    public Point3D pixelToPoint3D(Point3D pixelPoint ,BufferedImage image) {
        double latDifOfBounds = nwBound.x()-seBound.x();
        double lonDifOfBounds = nwBound.y()-seBound.y();
        double xp = latDifOfBounds*pixelPoint.x() / image.getWidth();
        double latDegrees = seBound.x() + xp;
        double yp = lonDifOfBounds*pixelPoint.y() / image.getHeight();
        double lonDegrees = seBound.y() + yp;
        return new Point3D(latDegrees,lonDegrees);
    }

    public Point3D point3DToPixel(Point3D point3D , BufferedImage image) {
        double pxWidthdiff = (point3D.x()-seBound.x())*image.getWidth() / (nwBound.x()-seBound.x());
        double pxHeightdiff = (point3D.y()-seBound.y())*image.getHeight() / (nwBound.y()-seBound.y());
        return new Point3D(pxWidthdiff,pxHeightdiff);
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

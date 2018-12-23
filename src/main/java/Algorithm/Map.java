package Algorithm;

import Coords.MyCoords;
import Geom.Point3D;
import java.awt.image.BufferedImage;

/**
 *This class contains calculations to match geom space into dimensions of a given image.
 *class represents a Map , Built from an image.
 *with all parameters needed to image's adjustments to a global coordinate system.
 *Class should allow a flexible conversion from a coordinate representation to it's Pixel representation
 *to image and Vice versa
 *also , it should allow calculations such as distance between two points represented as pixels
 *and also the angle between two pixels.
 *define two anchor points , at the top left and bottom down right of an image representing a geo map.
 *all class fields have been customized to match a specific image , attached to repository.
 * Also, check out
 * link  https://github.com/Ronmor/Gps/blob/master/Ariel1.png
 * @author Ron Mor , Rivka Revivo
 */
public class Map {
    private final MyCoords myCoords = new MyCoords();
    private final Point3D nwBound = new Point3D(32.10619669,35.203089528);
    private final  Point3D seBound = new Point3D(32.101858,35.21180900);    // read image through a MenuBar object.
    private final Point3D swAnchor = new Point3D(seBound.x(),nwBound.y());

    /**
     * @param pixelPoint is a representation of a point in pixel values
     * @param image is image representation of the geo map
     * @return Point3D representation in Lat,Lon coordinates in reality , in the proportion of the geo map
     */
    public Point3D pixelToPoint3D(Point3D pixelPoint ,BufferedImage image) {
        double latDifOfBounds = nwBound.x()-seBound.x();
        double lonDifOfBounds = nwBound.y()-seBound.y();
        double xp = latDifOfBounds*pixelPoint.x() / image.getWidth();
        double latDegrees = seBound.x() + xp;
        double yp = lonDifOfBounds*pixelPoint.y() / image.getHeight();
        double lonDegrees = seBound.y() + yp;
        return new Point3D(latDegrees,lonDegrees);
    }
    /**
     * @see #pixelToPoint3D (Point3D, BufferedImage) convert to pixel from Lat,Lon coordinates
     * @param point3D represented by Lat,Lon Coordinates
     * @param image represent a geo map
     * @return representation in pixels
     */
    public Point3D point3DToPixel(Point3D point3D , BufferedImage image) {
        double pxWidthdiff = (point3D.x()-seBound.x())*image.getWidth() / (nwBound.x()-seBound.x());
        double pxHeightdiff = (point3D.y()-seBound.y())*image.getHeight() / (nwBound.y()-seBound.y());
        return new Point3D(pxWidthdiff,pxHeightdiff);
    }
    /**
     * @param x1 ∈ Pixel1 = (x1,y1)
     * @param y1 ∈ Pixel1 = (x1,y1)
     * @param x2 ∈ Pixel2 = (x2,y2)
     * @param y2 ∈ Pixel2 = (x2,y2)
     * @return distance between pixels
     */
    public double distanceBetweenPixels(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    /**
     * @param x1 ∈ Pixel1 = (x1,y1)
     * @param y1 ∈ Pixel1 = (x1,y1)
     * @param x2 ∈ Pixel2 = (x2,y2)
     * @param y2 ∈ Pixel2 = (x2,y2)
     * @param bufferedImage is the geo map image representation
     * @return the angle between (Pixel1,Pixel2)
     */
    public double azimuthBetweenPixels(int x1, int y1, int x2, int y2 , BufferedImage bufferedImage) {
        Point3D px0 = pixelToPoint3D(new Point3D(x1,y1),bufferedImage);
        Point3D px1 = pixelToPoint3D(new Point3D(x2,y2),bufferedImage);
        return myCoords.azimuth_elevation_dist(px0,px1)[0];
    }
}

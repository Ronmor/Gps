package Algorithm;



import Coords.MyCoords;
import Geom.Point3D;
import java.util.LinkedList;
import java.util.List;

public class Path {
    /**
     * This class represents a route which defiend from a collection of Points
     */
    private final List<Point3D> points = new LinkedList<>();
    private final MyCoords myCoords = new MyCoords();
    public Path() {
    }
    /**
     * @return the distance of Path in meters
     */
    public double pathDistance() {
        if (points.size() < 2) {
            return 0;
        }
        return myCoords.distance3d(points.get(0), points.get(points.size() - 1));
    }
    public void addPoint(Point3D point) {
        points.add(point);
    }
    /**
     * @return how much points are in current path
     */
    public int size() {
        return points.size();
    }
    /**
     * @return the last point added to Path
     */
    public Point3D getLastPointCopy() {
        return new Point3D(points.get(size() - 1));
    }
}

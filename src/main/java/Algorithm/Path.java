package Algorithm;

import Coords.MyCoords;
import GIS.Fruit;
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
     * Constructor to hold coordinates of eaten fruits for a single path , ideal for kml writing of path's data
     * @param fruits is a List of Fruit
     */
    public Path(List<Fruit> fruits){
        points.clear();
        for (Fruit fruit:fruits){
            points.add(fruit.getCoordinates());
        }
    }
    /**
     * @return the list of points alongside path
     */
    public List<Point3D> getPointsCopy() {
        return new LinkedList<>(points);}
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
    /**
     * a static method
     * @param LP represents a List of path
     * @return size of longest path on LP
     */
    private static int BiggestPath(List<Path> LP)
    {
        int max = -1;
        for(Path p: LP)
        {
            if(p.getPointsCopy().size() > max)
                max = p.getPointsCopy().size();
        }
        return max;
    }
    /**
     * Method array of point3D.ix() (x values) , for graphic
     * @return array of integers
     */
    private int[] aXis(){
        int[] x = new int[this.points.size()];
        for (int i = 0; i <x.length ; i++) {
            x[i] = points.get(i).ix();
        }
        return x;
    }
    /**
     * @see Path#aXis() ---> for Y values
     * @return array of integers
     */
    private int[] aYis(){
        int[] y = new int[this.points.size()];
        for (int i = 0; i <y.length ; i++) {
            y[i] = points.get(i).iy();
        }
        return y;
    }
    /**
     * @return if Path is empty
     */
    public boolean empty(){
        return points.isEmpty();
    }
}

package Algorithm;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Packman;
import Geom.Point3D;

import java.util.LinkedList;
import java.util.List;


/**
 *This class has a list of paths specified for coordinates alongside path.
 * --------------------------------------------------------------------------------
 *Let P be a Path ∈ Solution , And let p be a Point3D such as {p∈P}
 * ---- It Exists that :  ⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒ ⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒⇒
 *∀ p [(p∈P) ⇔ p is a Fruit coordinate]
 * @author Ron Mor , Rivka Revivo.
 */
public class Solution {

    private Game game;
    private List<Path> calcFruitPathes= new LinkedList<>();
    private MyCoords myCoords = new MyCoords();

    private List<Fruit> CopySingleEatPath(Path path){
        List<Fruit> fruitsOnly = new LinkedList<>();
        for (Fruit fruit: game.getFruitsCopy()){
            for (Point3D point3D : path.getPointsCopy()){
                if (fruit.getCoordinates().equals(point3D)){
                  fruitsOnly.add(fruit);
                }
            }
        }
        return fruitsOnly;
    }
    /**
     * Constructor is essential for outputting , Game sequence properly to Google Earth view
     * @param calcPathes is the full Path List as returned from ShortestPathAlgo for a requested Game.
     * @param kmlSolution from a Type of a requested Game , which user would like to write to Kml.
     */
    public Solution(List<Path> calcPathes ,Game kmlSolution){
        game = new Game(kmlSolution);
        for (Path path : calcPathes){
           List<Fruit> fruitsOnPath = CopySingleEatPath(path);
           Path fruitPath = new Path(fruitsOnPath);
           if (!fruitPath.empty())
           calcFruitPathes.add(fruitPath);
        }
    }
    /**
     * @return the "Eat" paths list.
     */
    public List<Path> getSolutionPathList() {
        return new LinkedList<>(calcFruitPathes);
    }
    public Game getGame() {
        return game;
    }
    public double timeToNextStop(double[] timeArray ,int here , int nextStop) {
        return timeArray[nextStop] - timeArray[here] ;
    }
    public double totalPathTime(Path path) {
        double totalTime = 0;
        int index = 0;
        for (Point3D point : path.getPointsCopy()) {
            if(path.getPointsCopy().size() > index+1) {
                totalTime += myCoords.distance3d(point, path.getPointsCopy().get(index+1));
            }
        }
        return totalTime;
    }
    public double[] PackmanEatingTimes(Packman pacman, Path path) { //if !path.empty() only!
        double[] times;
        if(path.size()==1) {
            times = new double[path.size()];
            times[0] = 0;
            return times;
        }
        times = new double[path.size()];
        for(int i=1;i<path.getPointsCopy().size();i++) {
            int index = 0;
            times[index] = 0;
            double distance = myCoords.distance3d(path.getPointsCopy().get(index), path.getPointsCopy().get(i));
            times[i] = distance/pacman.getSpeed();
            index++;
        }
        return times;
    }
}

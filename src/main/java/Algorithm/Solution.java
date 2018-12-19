package Algorithm;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import Geom.Point3D;

import java.util.LinkedList;
import java.util.List;

public class Solution {
    /**
     * This class has a list of <Path> and will use and needs to decide the best path While there are still uneaten fruits
     */
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
    public Solution(List<Path> calcPathes ,Game kmlSolution){
        game = new Game(kmlSolution);
        for (Path path : calcPathes){
           List<Fruit> fruitsOnPath = CopySingleEatPath(path);
           Path fruitPath = new Path(fruitsOnPath);
           if (!fruitPath.empty())
           calcFruitPathes.add(fruitPath);
        }
    }
    public List<Path> getSolutionPathList() {
        return new LinkedList<>(calcFruitPathes);
    }
    public double[] timesOfEating(Path myPath){
        double[] times = new double[myPath.size()];
        for (int i = 0; i <myPath.size()-1 ; i++) {
            times[i] = myCoords.distance3d(myPath.getPointsCopy().get(i),myPath.getPointsCopy().get(i+1));
        }
        return times;
    }

    public Game getGame() {
        return game;
    }
}

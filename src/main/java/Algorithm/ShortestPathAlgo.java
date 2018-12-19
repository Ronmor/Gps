package Algorithm;

import Coords.MyCoords;
import GIS.*;
import Geom.Geom_element;
import Geom.Point3D;
import javafx.util.Pair;

import java.util.*;

public class ShortestPathAlgo {
    /**
     * This class Should return the optimal route , When activated over a Game
     * an Optimal Route would be - the shortest option for all the Fruits to be eaten.
     * It means the fastest possibility to end a game as quickly as possible
     * it includes calculation method for a "eat Fruit Path" for each of the packmans.
     */

    public static void main(String... args) {
        String path = "C:\\Users\\ronmo\\Desktop\\Ex2\\Ex3\\data\\game_1543684662657.csv";
        Game game = new Game(path);
        ShortestPathAlgo algo = new ShortestPathAlgo(game);
        List<Path> pathes = algo.calcPathes();
        GIS_project project = algo.getProject();
    }

    private static final double MAX_ITERATION_TIME_SECONDS = 1;

    private final List<Packman> packmen;
    private final List<Fruit> fruits;
    private final Set<Integer> eatenFruitsIndices = new HashSet<>();
    private final MyCoords myCoords = new MyCoords();
    private final GIS_project project = new GisProjectImpl();
    private int iterationCounter = 0;
    private long timeOffset = System.currentTimeMillis();

    public ShortestPathAlgo(Game game) {
        this.packmen = new ArrayList<>(game.getPackMansCopy());
        this.fruits = new ArrayList<>(game.getFruitsCopy());
    }

    public int getNumOfPackmen() {
        return packmen.size();
    }
    public int getIterationCounter(){
        return iterationCounter;
    }

    public GIS_project getProject() {
        return project;
    }

    public List<Path> calcPathes() {
        List<Path> pathes = new ArrayList<>(packmen.size() + fruits.size());
        for (int i = 0; i < packmen.size() + fruits.size(); i++) {
            pathes.add(new Path());
        }
        java.util.Map<Integer, Integer> packmanToFruitAssignments = new HashMap<>();
        double iterationTimeSeconds = 1;
        while (eatenFruitsIndices.size() < fruits.size()) {
            // calc time matrix
            double[][] timeMatrix = calcTimeMatrix(eatenFruitsIndices);
            // figure out which packman goes to which fruit
            packmanToFruitAssignments = assignPackmenToFruits(timeMatrix);
            // fill current coordinates
            fillCurrentCoordinates(pathes, timeOffset, packmanToFruitAssignments);
            // figure out iterationCounter time (in seconds)
            Pair<Double, java.util.Map<Integer, Integer>> iterationTimeSecondsEatenFruitTpPackmanMappingPair = findMinTime(timeMatrix);
            iterationTimeSeconds = Double.min(1, iterationTimeSecondsEatenFruitTpPackmanMappingPair.getKey());
            // set each packmen new location after moving to the calculated direction for a specific amount of time
            updatePackmenLocations(packmanToFruitAssignments, iterationTimeSeconds);
            // if a fruit has been eaten
            java.util.Map<Integer, Integer> eatenFruitToPackmanMapping = iterationTimeSecondsEatenFruitTpPackmanMappingPair.getValue();
            eatenFruitToPackmanMapping.forEach((fruitIndex, packmanIndex) -> {
                eatenFruitsIndices.add(fruitIndex);
                packmen.get(packmanIndex).addScore(fruits.get(fruitIndex).getWeight());
            });
            // update iteration counter and time offset
            timeOffset += (long) (iterationTimeSeconds * 1000);
        }
        fillCurrentCoordinates(pathes, timeOffset, packmanToFruitAssignments);
        return pathes;
    }
    public GIS_project fillKmlProjectVersion(List<Path> paths){
        fillCurrentCoordinates(paths,System.currentTimeMillis(),assignPackmenToFruits(calcTimeMatrix(eatenFruitsIndices)));
        return project;
    }
    private void fillCurrentCoordinates(List<Path> pathes, long timeOffset, java.util.Map<Integer, Integer> packmanToFruitAssignments) {
        double score = 0;
        for (int packmanIndex = 0; packmanIndex < packmen.size(); packmanIndex++) {
            pathes.get(packmanIndex).addPoint(packmen.get(packmanIndex).getCoordinates());
            score += packmen.get(packmanIndex).getScore();
        }
        for (int friutIndex = 0; friutIndex < fruits.size(); friutIndex++) {
            if (!eatenFruitsIndices.contains(friutIndex)) {
                pathes.get(packmen.size() + friutIndex).addPoint(fruits.get(friutIndex).getCoordinates());
            }
        }
        Meta_data metaData = new MetaDataImpl(timeOffset, "score=" + score, null);
        GIS_layer layer = new GisLayerImpl(metaData, timeOffset);
        int pathIndex = 0;
        for (; pathIndex < packmen.size(); pathIndex++) {
            Geom_element geom = pathes.get(pathIndex).getLastPointCopy();
            double packmanScore = packmen.get(pathIndex).getScore();
            double totalPathDistance = pathes.get(pathIndex).pathDistance();
            Point3D orientation = null;
            Meta_data data;
            if (packmanToFruitAssignments.containsKey(pathIndex)) {
                orientation = fruits.get(packmanToFruitAssignments.get(pathIndex)).getCoordinates();
                data = new MetaDataImpl(timeOffset, "score=" + packmanScore + " pathDistance=" + totalPathDistance, orientation);
            } else {
                data = new MetaDataImpl(timeOffset, "score=" + packmanScore + " pathDistance=" + totalPathDistance);
            }
            GIS_element element = new GisElementImpl(geom, data);
            layer.add(element);
        }
        for (; pathIndex < pathes.size(); pathIndex++) {
            if (pathes.get(pathIndex).size() < iterationCounter) {
                // filter eaten fruits whose pathes are no longer updated
                continue;
            }
            Geom_element geom = pathes.get(pathIndex).getLastPointCopy();
            Meta_data data = new MetaDataImpl(timeOffset);
            GIS_element element = new GisElementImpl(geom, data);
            layer.add(element);
        }
        project.add(layer);
    }

    private void updatePackmenLocations(java.util.Map<Integer, Integer> packmanToFruitAssignments, double iterationTimeSeconds) {
        iterationCounter++;
        packmanToFruitAssignments.forEach((packmanIndex, fruitIndex) -> {
            UTM packmanMetricLocation = CoordinatesConverter.Deg2UTM(packmen.get(packmanIndex).getCoordinates());
            UTM fruitMetricLocation = CoordinatesConverter.Deg2UTM(fruits.get(fruitIndex).getCoordinates());
            double packmanVelocity = packmen.get(packmanIndex).getSpeed();
            double dx = fruitMetricLocation.getEasting() - packmanMetricLocation.getEasting();
            double dy = fruitMetricLocation.getNorthing() - packmanMetricLocation.getNorthing();
            double vx;
            double vy;
            if (dx == 0) {
                vx = 0;
                vy= packmanVelocity;
            } else {
                double alpha = Math.atan2(dy, dx);
                vx = packmanVelocity * Math.cos(alpha);
                vy = packmanVelocity * Math.sin(alpha);
            }
            UTM updatedPackmanMetricLocation = new UTM(packmanMetricLocation.getEasting() + iterationTimeSeconds * vx,
                    packmanMetricLocation.getNorthing() + iterationTimeSeconds * vy,
                    packmanMetricLocation.getZone(), packmanMetricLocation.getLetter());
            Point3D updatedPackmanGpsLocation = CoordinatesConverter.UtmToPoint3D(updatedPackmanMetricLocation);
            packmen.get(packmanIndex).setCoordinates(updatedPackmanGpsLocation);
        });
    }

    private double calcVLat(Packman packman, double azimuth) {
        double speed = packman.getSpeed();
        return 90 < azimuth && azimuth < 270 ? -speed * Math.cos(Math.toRadians(azimuth)) : speed * Math.cos(Math.toRadians(azimuth));
    }

    private double calcVLon(Packman packman, double azimuth) {
        double speed = packman.getSpeed();
        return azimuth <= 180 ? speed * Math.sin(Math.toRadians(azimuth)) : -speed * Math.sin(Math.toRadians(azimuth));
    }

    private Pair<Double, java.util.Map<Integer, Integer>> findMinTime(double[][] timeMatrix) {
        double minTime = Double.MAX_VALUE;
        java.util.Map<Integer, Integer> eatenFruitToPackmanMapping = new HashMap<>();
        for (int packmanIndex = 0; packmanIndex < timeMatrix.length; packmanIndex++) {
            for (int fruitIndex = 0; fruitIndex < timeMatrix[0].length; fruitIndex++) {
                if (timeMatrix[packmanIndex][fruitIndex] < minTime) {
                    minTime = timeMatrix[packmanIndex][fruitIndex];
                    eatenFruitToPackmanMapping.clear();
                }
                if (timeMatrix[packmanIndex][fruitIndex] == minTime) {
                    if (minTime <= MAX_ITERATION_TIME_SECONDS) {
                        eatenFruitToPackmanMapping.put(fruitIndex, packmanIndex);
                    }
                }
                minTime = Double.min(minTime, timeMatrix[packmanIndex][fruitIndex]);
            }
        }
        return new Pair<>(minTime, eatenFruitToPackmanMapping);
    }

    private java.util.Map<Integer, Integer> assignPackmenToFruits(double[][] timeMatrix) {
        //Method is responsible to assign Packman to Fruits Based on the time matrix
        java.util.Map<Integer, Integer> packmanToFruitAssignments = new HashMap<>();
        Set<Integer> assignedFruits = new HashSet<>();
        while (packmanToFruitAssignments.size() != Integer.min(packmen.size(), fruits.size() - eatenFruitsIndices.size())) {
            // as long as there are uneaten fruits we wish to assign packmen
            double minTime = Double.MAX_VALUE;
            int packman = -1;
            int fruit = -1;
            //For all packmans and fruits , if it already has a mapping , continue , otherwise --> put
            for (int packmanIndex = 0; packmanIndex < timeMatrix.length; packmanIndex++) {
                if (packmanToFruitAssignments.containsKey(packmanIndex)) {
                    continue;
                }
                for (int fruitIndex = 0; fruitIndex < timeMatrix[0].length; fruitIndex++) {
                    if (assignedFruits.contains(fruitIndex)) {
                        continue;
                    }
                    if (timeMatrix[packmanIndex][fruitIndex] < minTime) {
                        minTime = timeMatrix[packmanIndex][fruitIndex];
                        packman = packmanIndex;
                        fruit = fruitIndex;
                    }
                }
            }
            packmanToFruitAssignments.put(packman, fruit);
            assignedFruits.add(fruit);
        } //when finished assignments , returns a map representation
        return packmanToFruitAssignments;
    }

    private double[][] calcTimeMatrix(Set<Integer> eatenFruitsIndices) {
        //2D array where every row represent a Packman and every column represents a Fruit
        //Every index in raw is the time for a certain Packman to eat the Fruit represent by it's column
        double[][] timeMatrix = new double[packmen.size()][fruits.size()];
        for (int packmanIndex = 0; packmanIndex < timeMatrix.length; packmanIndex++) {
            for (int fruitIndex = 0; fruitIndex < timeMatrix[0].length; fruitIndex++) {
                if (eatenFruitsIndices.contains(fruitIndex)) {
                    timeMatrix[packmanIndex][fruitIndex] = Double.MAX_VALUE;
                } else {
                    double distance =
                            myCoords.distance3d(packmen.get(packmanIndex).getCoordinates(),
                                    fruits.get(fruitIndex).getCoordinates()) - packmen.get(packmanIndex).getEatingRadius();
                    timeMatrix[packmanIndex][fruitIndex] =
                            Double.max(distance, 0) /
                                    packmen.get(packmanIndex).getSpeed();
                }
            }
        }
        return timeMatrix;
    }

}

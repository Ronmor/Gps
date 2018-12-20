package GIS;

import File_format.CsvReader;
import Geom.Point3D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Game {
    /**
     * This class represents the Game , Where the players are Packman and Fruits .
     * a Game can be saved to or be created from by a Csv file as appears in the example
     * should have two sets and proper methods to add sets its elements from csv.
     */
    private Set<Fruit> fruits = new HashSet<>();
    private Set<Packman> packmen = new HashSet<>();
    
    public Game(Game other){
        fruits = other.getFruitsCopy();
        packmen = other.getPackMansCopy();
    }

    public Game(){
    }
    public Game(String csvFile) {
        File input = new File(csvFile);
        if (isCsv(input)) {
            String[] lines = CsvReader.readLines(csvFile);
            for (int currentLine = 1; currentLine < lines.length; currentLine++) {
                String[] splittedData = lines[currentLine].split(",");
                int id = Integer.parseInt(splittedData[1]);
                if (isPackMan(splittedData[0])) {
                    double speed = Double.parseDouble(splittedData[5]);
                    double eatingRadius = Double.parseDouble(splittedData[6]);
                    double lat = Double.parseDouble(splittedData[2]);
                    double lon = Double.parseDouble(splittedData[3]);
                    Point3D coordinates = new Point3D(lat, lon);
                    Packman p = new Packman(id, coordinates, speed, eatingRadius);
                    packmen.add(p);
                } else {
                    double lat = Double.parseDouble(splittedData[2]);
                    double lon = Double.parseDouble(splittedData[3]);
                    double weight = Double.parseDouble(splittedData[5]);
                    Point3D fruit_coords = new Point3D(lat, lon);
                    Fruit fruit = new Fruit(id, fruit_coords, weight);
                    fruits.add(fruit);
                }
            }
        } else {
            System.err.print("Wrong Input!");
        }
    }
    public void toCsv () throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new File(stats()+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("Type" + ',');
        sb.append("Id" + ',');
        sb.append("Lat" + ',');
        sb.append("Lon" + ',');
        sb.append("Alt" + ',');
        sb.append("Speed/Weight" + ',');
        sb.append("Radius" + ',');
        sb.append(""+packmen.size() + ',');
        sb.append(""+fruits.size() + '\n');
        for (Packman packman : packmen){
            sb.append("P" + ',');
            sb.append(""+packman.getId() + ',');
            sb.append(""+packman.getCoordinates().x() + ',');
            sb.append(""+packman.getCoordinates().y() + ',');
            sb.append(""+packman.getCoordinates().z() + ',');
            sb.append(""+packman.getSpeed() + ',');
            sb.append("" + packman.getEatingRadius()).append('\n');
        }
        for (Fruit fruit : fruits){
            sb.append("F" + ',');
            sb.append(""+fruit.getId() + ',');
            sb.append(""+fruit.getCoordinates().x() + ',');
            sb.append(""+fruit.getCoordinates().y() + ',');
            sb.append(""+fruit.getCoordinates().z() + ',');
            sb.append(""+fruit.getWeight()).append('\n');
        }
        printWriter.write(sb.toString());
        printWriter.close();
    }
    private boolean isPackMan(String str) {
        return str.toLowerCase().equals("p");
    }
    public Set<Packman> getPackMansCopy() {
        return new HashSet<>(packmen);
    }

    public Set<Fruit> getFruitsCopy() {
        return new HashSet<>(fruits);
    }
    private boolean isCsv(File fileType){
            return fileType.getName().endsWith(".csv");
        }
        public void addPackman(Packman p){
        this.packmen.add(p);
        }
        public void addFruit(Fruit f){
        this.fruits.add(f);
        }
        public void removePackmanbyPosition(Point3D packman){
        for (Packman packman1 : this.getPackMansCopy())
            if (packman.equals(packman1.getCoordinates()))
                this.packmen.remove(packman1);
                }
        public String stats(){
        return "["+packmen.size()+ "]"+"["+fruits.size()+"]";
        }

    public List<Packman> getPackmanAsList() {
        List<Packman> pacman = new LinkedList<>();
        for (Packman packman: packmen){
            pacman.add(packman);
        }
        return pacman;
    }
}


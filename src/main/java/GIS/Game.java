package GIS;

import File_format.CsvReader;
import Geom.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class Game {
    /**
     * This class represents a collection of <Fruit> and a collection of <Robots> </></Fruit>
     * a Game can be build by a Csv file as appers in the example
     * This class Should have the abillity to save Game's data to a Csv file as well
     *
     */
    /**
     * should have two sets and proper methods to add sets its elements from csv.
     */
    private Set<Fruit> fruits = new HashSet<>();
    private Set<Packman> packmen = new HashSet<>();
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
    public void toCsvFile() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File(this +"Game.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Packman packman : packmen){
            String[] pData = packManData(packman);
            for (int i = 0; i < pData.length; i++) {
                printWriter.print(pData[i]);
                if(i != pData.length-1) {
                    printWriter.print(",");
                }
            }
            printWriter.println();
        }
        for (Fruit fruit : fruits){
            String[] fData = fruitData(fruit);
            for (int i = 0; i <fData.length ; i++) {
                printWriter.print(fData[i]);
                if(i != fData.length-1) {
                    printWriter.print(",");
                }
            }
            printWriter.println();
        }
        printWriter.close();
    }

    private boolean isPackMan(String str) {
        return str.toLowerCase().equals("p");
    }
    private String[] packManData(Packman packman){
            String[] pacman = new String[6];
            pacman[0] = "P";
            pacman[1] = ""+packman.getId();
            pacman[2] = ""+packman.getCoordinates().x();
            pacman[3] = ""+packman.getCoordinates().y();
            pacman[4] = ""+packman.getCoordinates().z();
            pacman[5] = ""+packman.getSpeed();
            pacman[6] = ""+packman.getEatingRadius();
            return pacman;
    }
    private String[] fruitData(Fruit fruit){
        String[] sFruit = new String[5];
        sFruit[0] = "F";
        sFruit[1] = ""+fruit.getId();
        sFruit[2] = ""+fruit.getCoordinates().x();
        sFruit[3] = ""+fruit.getCoordinates().y();
        sFruit[4] = ""+fruit.getCoordinates().z();
        sFruit[5] = ""+fruit.getWeight();
        return sFruit;

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
    }


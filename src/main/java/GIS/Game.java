package GIS;

import File_format.CsvReader;
import Geom.Point3D;

import java.util.HashSet;
import java.util.Set;

public class Game {
    /**
     * This class represents a collection of <Fruit> and a collection of <Robots> </></Fruit>
     * a Game can be build by a Csv file as appers in the example
     * This class Should have the abillity to save Game's data to a Csv file as well
     *
     */
    //TODO - write a class Csv2Game.
    /**
     * should have two sets and proper methods to add sets its elements from csv.
     */
    Set<Fruit> fruits = new HashSet<>();
    Set<PackMan> packMans = new HashSet<>();
    public Game(String csvFile){
        String[] lines = CsvReader.readLines(csvFile);
        for (String line : lines){
            String[] splittedData = line.split(",");
           if (isPackMan(splittedData[0])){
               double speed = Double.parseDouble(splittedData[5]);
               double eat_radius = Double.parseDouble(splittedData[6]);
               double lat = Double.parseDouble(splittedData[2]);
               double lon = Double.parseDouble(splittedData[3]);
               Point3D packMan_coords = new Point3D(lat , lon);
               PackMan p = new PackMan(speed,eat_radius,packMan_coords);
               packMans.add(p);
           }
        else {
               double lat = Double.parseDouble(splittedData[2]);
               double lon = Double.parseDouble(splittedData[3]);
               Point3D fruit_coords = new Point3D(lat , lon);
               Fruit fruit = new Fruit(fruit_coords);
               fruits.add(fruit);
           }
        }
    }
    public boolean isPackMan(String str){
        return str.toLowerCase().equals("p");
    }
}

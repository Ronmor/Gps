package File_format;

import GIS.Fruit;
import GIS.Game;
import GIS.Packman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvWriter {
    private PrintWriter printWriter;
    public void SaveGameCsv (Game game , String nameOfGame){
        try {
            printWriter = new PrintWriter(new File(nameOfGame+".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Type" + ',');
        sb.append("Id:" + ',');
        sb.append("Lat:" + ',');
        sb.append("Lon:" + ',');
        sb.append("Alt:" + ',');
        sb.append("Speed/Weight:" + ',');
        sb.append("Radius" + ',');
        sb.append(game.getPackMansCopy().size() + ',');
        sb.append(game.getFruitsCopy().size() + ',');
        sb.append('\n');
        for (Packman packman : game.getPackMansCopy()){
            sb.append("P" + ',');
            sb.append(packman.getId() + ',');
            sb.append(packman.getCoordinates().x() + ',');
            sb.append(packman.getCoordinates().y() + ',');
            sb.append(packman.getCoordinates().z() + ',');
            sb.append(packman.getSpeed() + ',');
            sb.append(packman.getEatingRadius() + ',');
            sb.append('\n');
        }
        for (Fruit fruit : game.getFruitsCopy()){
            sb.append("F" + ',');
            sb.append(fruit.getId() + ',');
            sb.append(fruit.getCoordinates().x() + ',');
            sb.append(fruit.getCoordinates().y() + ',');
            sb.append(fruit.getCoordinates().z() + ',');
            sb.append(fruit.getWeight() + ',');
            sb.append('\n');
        }
        printWriter.write(sb.toString());
        printWriter.close();

    }

}

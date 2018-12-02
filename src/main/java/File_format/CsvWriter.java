package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvWriter {
        //TODO - finish class , or if not really needed , mute class .   if class will be used , test with Junit Creation of csv file , using search algorithm

    public static void main (String[] args){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File("CoordinatesTable.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Current lat:" + ',');
        sb.append("Current Lon:" + ',');
        sb.append('\n');
        //Table Columns are set.
        //TODO - complete , append a list of coordinates
    }
}

package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;

public class CsvWriter {

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

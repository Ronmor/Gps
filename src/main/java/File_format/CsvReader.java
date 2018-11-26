package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//TODO - read csv example using this class , after that --> Junit
//TODO - find and ask about kml libaries to add to project
public class CsvReader {
    private String csvFile; // this represent file name in project folder or , enter a full location lane.

    public static String[] readLines(String path) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // to iterate reading line by line
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            return new String[0];
        }
        return lines.toArray(new String[lines.size()]);
    }
}

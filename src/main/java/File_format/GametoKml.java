package File_format;
import GIS.Game;

import java.io.FileNotFoundException;

public class GametoKml {

    public static void main(String[] args){
        Game game = new Game("game_1543685769754.csv");
        Path2Kml path2Kml = new Path2Kml();
        Kml kml = new Kml();
        try {
            kml.GametoKml("m" , game);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

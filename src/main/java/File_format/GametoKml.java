package File_format;
import GIS.Game;
import java.io.FileNotFoundException;

public class GametoKml {


    public static void main(String[] args){
        Game game = new Game("game_1543685769754.csv");
        Kml kml = new Kml();
        try {
            kml.GametoKml("RivkaHalas",game);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package GUI;

import Algorithm.Map;
import Algorithm.Path;
import Algorithm.ShortestPathAlgo;
import GIS.Game;
import Geom.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This class draws Packman Path on Gui
 */

public class DrawPath extends Component{
    private Game game;
    private ShortestPathAlgo shortestPathAlgo;
    private List<Path> calcPathes;
    private Map linePainter = new Map();
    private Graphics g;

    public DrawPath(Game other , Graphics otherg){
        game = new Game(other);
        shortestPathAlgo = new ShortestPathAlgo(game);
        calcPathes = shortestPathAlgo.calcPathes();
        //g= otherg;
    }
    /**
     * This method draws all packmans paths
     * @param image is the image of the map that game is played on.
     * @param current is a Path
     */
    public void SetLinePainter(BufferedImage image, Path current, ImageIcon imageIcon) {
        int nextPoint;
        for (int i = 0; i < current.getPointsCopy().size()-1; i++) {
            nextPoint = i+1;
            Point3D from = linePainter.point3DToPixel(current.getPointsCopy().get(i),image);
            Point3D to = linePainter.point3DToPixel(current.getPointsCopy().get(nextPoint),image);
            //imageIcon.paintIcon(this,g,to.ix(),to.iy());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void DrawAllPath(BufferedImage image,ImageIcon imageIcon){
        for (Path path : calcPathes){
            SetLinePainter(image,path,imageIcon);
        }
    }

    public List<Path> getcalcPathes()
    {
        return this.calcPathes;
    }

}

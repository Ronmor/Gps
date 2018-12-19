package GUI;

import Algorithm.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packman;
import Geom.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;


public class LoadGameWindow {

    private Map frameConverter = new Map();
    private Game game;

    public void loadGame () {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filterCsv = new FileNameExtensionFilter("Csv File","csv");
        fileChooser.setFileFilter(filterCsv);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File openedFile = fileChooser.getSelectedFile();
            try {
                this.game  = new Game(openedFile.getPath());
                Scanner in = new Scanner(openedFile);
                in.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
            }
    }
    public Game getGame(){return game;}
    public void drawLoadedGameElements(Graphics g , Game game , ImageIcon pacman , ImageIcon fruitImg , BufferedImage myImage,Component c){
        for (Packman packman : game.getPackMansCopy()){
            Point3D pixelsToDrawAt = frameConverter.point3DToPixel(packman.getCoordinates(),myImage);
           // pacman.paintIcon(c,g,pixelsToDrawAt.ix(),pixelsToDrawAt.iy());
            g.drawImage(pacman.getImage(),pixelsToDrawAt.ix(),pixelsToDrawAt.iy(),pacman.getIconWidth(),pacman.getIconHeight(),c);
        }
        for (Fruit fruit : game.getFruitsCopy()){
            Point3D pixelsToDrawAt = frameConverter.point3DToPixel(fruit.getCoordinates(),myImage);
           // fruitImg.paintIcon(c,g,pixelsToDrawAt.ix(),pixelsToDrawAt.iy());
            g.drawImage(fruitImg.getImage(),pixelsToDrawAt.ix(),pixelsToDrawAt.iy(),fruitImg.getIconWidth(),fruitImg.getIconHeight(),c);
        }
    }
}


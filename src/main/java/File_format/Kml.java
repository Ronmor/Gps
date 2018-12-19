package File_format;

import Algorithm.Path;
import Algorithm.ShortestPathAlgo;
import Algorithm.Solution;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.Game;
import Geom.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Kml {

    private Collection<GIS_element> points = new HashSet<>();


    public void addLayer(GIS_layer layer) {
        for (GIS_element element : layer) {
            points.add(element);
        }
    }

    public void toKmlFile(String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(path));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
        writer.println("<Document>");
        writer.println("<Folder>");
        writer.println("<name/>");
        for (GIS_element element : points) {
            writer.println("<Placemark>");
            writer.println("<name/>");
            Date date = new Date(element.getData().getUTC());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.println("<description><![CDATA[Timestamp: <b>" + element.getData().getUTC() + "</b><br/>Date: <b>" + df.format(date) + "</b>]]></description>");
            writer.println("<Point>");
            writer.println("<coordinates>" + ((Point3D) element.getGeom()).y() + "," + ((Point3D) element.getGeom()).x() + "," + ((Point3D) element.getGeom()).z() + "</coordinates>");
            writer.println("</Point>");
            writer.println("</Placemark>");
        }
        writer.println("</Folder>");
        writer.println("</Document>");
        writer.println("</kml>");
        writer.close();
    }

    public void GametoKml(String output, Game game) throws FileNotFoundException {
        ShortestPathAlgo shortestPathAlgo = new ShortestPathAlgo(game);
        List<Path> paths = shortestPathAlgo.calcPathes();
        Solution solution = new Solution(paths,game);
        List<Path> solutions = solution.getSolutionPathList();
        Game Kmled = solution.getGame();
        ShortestPathAlgo kmlpaths = new ShortestPathAlgo(Kmled);
        GIS_project gameProject = kmlpaths.fillKmlProjectVersion(solutions);
        PrintWriter writer = new PrintWriter(new File(output + ".kml"));
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\"\n" +
                " xmlns:gx=\"http://www.google.com/kml/ext/2.2\">");
        writer.println("<Document>");
        writer.println("<open>1</open>\n" +
                "<description>\n" +
                " In Google Earth, enable historical imagery and sunlight,\n" +
                "then click on each placemark to fly to that point in time.\n" +
                "</description>");
        writer.println("<Folder>");
        writer.println("<name/>");
        writer.println("<Style id=\"#Pacman-dot-icon\">\n" +
                "<IconStyle>\n" +
                "<Icon>\n" +
                "<href>https://github.com/Ronmor/Gps/blob/master/pacman.png</href>\n" +
                "</Icon>\n" +
                "</IconStyle>\n" +
                "</Style>");
        writer.println("<Style id=\"Fruit-dot-icon\">\n" +
                "<IconStyle>\n" +
                "<Icon>\n" +
                "<href>https://github.com/Ronmor/Gps/blob/master/diet1.png</href>\n" +
                "</Icon>\n" +
                "</IconStyle>\n" +
                "</Style>");
        int layerCounter = 0;
        long firstTimeStamp = 0;
        long lastTimeStamp = 0;
        for (GIS_layer layer : gameProject) {
            if (layerCounter == 0) {
                firstTimeStamp = layer.get_Meta_data().getUTC();
            }
            layerCounter = 1;
            lastTimeStamp = firstTimeStamp;
        }
        for (Path pathIndex : solutions) {
            Path2Kml.Path2Kml(writer, pathIndex);
        }
        for (GIS_layer gameIteration : gameProject) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            int totalTime = shortestPathAlgo.getIterationCounter();
            int second = 1;
            if (totalTime > second) {
                Date date = new Date(firstTimeStamp + (second-1)*1000);
                Date date1 = new Date(lastTimeStamp + (second)*10000);
                writer.println("<TimeSpan>\n" + "<begin>" + df.format(date).toString().replace(" ", "T") + "</begin>");
                writer.println("<end>" + df.format(date1).toString().replace(" ", "T") + "</end>");
                writer.println("</TimeSpan>");
                second++;
            }
            addLayer(gameIteration);
            for (GIS_element element : points) {
                writer.println("<Placemark>");
                writer.println("<name></name>");
                Date date = new Date(element.getData().getUTC() + 100);
                writer.println("<TimeStamp>" + "\n" +"<when>"+  df.format(date) +"</when></TimeStamp> "+"\n");
                if (element.getData().get_Orientation() != null) {
                    writer.println("<styleUrl>#Pacman-dot-icon</styleUrl>");
                    writer.println("<description><![CDATA[Timestamp: <b>" + element.getData().getUTC() + "</b><br/>Date: <b>" + df.format(date) +
                            "</b><br/>PathStatus: <b>" + element.getData().toString() +
                            "</b><br/>Orientation <b>" + element.getData().get_Orientation() + " ]]></description>");
                } else {
                    writer.println("<styleUrl>#Fruit-dot-icon</styleUrl>");
                    writer.println("<description><![CDATA[Timestamp: <b>" + element.getData().getUTC() + "</b><br/>Date: <b>" + df.format(date) +
                            "<b> ]]></description>");
                }
                writer.println("<Point>");
                writer.println("<coordinates>" + ((Point3D) element.getGeom()).y() + "," + ((Point3D) element.getGeom()).x() + "," + ((Point3D) element.getGeom()).z() + "</coordinates>");
                writer.println("</Point>");
                writer.println("</Placemark>");
            }
        }
        writer.println("</Folder>");
        writer.println("</Document>");
        writer.println("</kml>");
        writer.close();
    }
}



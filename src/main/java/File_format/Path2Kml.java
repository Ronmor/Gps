package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import Algorithm.Path;
import Geom.Point3D;

public class Path2Kml {
    /**
     * @param writer  writes Kml syntax
     * @param current is the shortestPath solution
     */
    public static void Path2Kml(PrintWriter writer, Path current) {
        writer.println("<name>Path</name>");
        writer.println("<Style id=\"getcolor\">");
        writer.println("<LineStyle>");
        String color = get_color();
        writer.println("<color>" + color + "</color>");
        writer.println("<width>3</width>");
        writer.println("</LineStyle>");
        writer.println("</Style>");
        writer.println("<Placemark>");
        writer.println("<name>Absolute Extruded</name>");
        writer.println("<styleUrl>#getcolor</styleUrl>");
        writer.println("<LineString>");
        writer.println("<coordinates>");
        for (Point3D point : current.getPointsCopy()) {
            writer.println("" + point.y() + "," + point.x() + "," + point.z());
        }
        writer.println("</coordinates>");
        writer.println("</LineString>");
        writer.println("</Placemark>");
    }

    private static String get_color() {
        double f = Math.random();
        f = f * 6;
        String[] color = {"ff0000ff", "ff00ffff", "ffff0000", "ff00ff00", "ff800080", "ff0080ff", "ff336699", "ffff00ff"};
        return color[(int) f];
    }

}
package File_format;

import GIS.GIS_element;
import GIS.GIS_layer;
import Geom.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
}

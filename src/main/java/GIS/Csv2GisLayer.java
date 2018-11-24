package GIS;

import Coords.MyCoords;
import File_format.CsvReader;
import Geom.Point3D;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Csv2GisLayer {

    private static MyCoords myCoords = new MyCoords();

    public static GIS_layer csv2gisLayer(String path) {
        String[] lines = CsvReader.readLines(path);
        GIS_layer gisLayer = new GisLayerImpl();
        for (int lineIndex = 2; lineIndex < lines.length; lineIndex++) {
            String[] splittedData = lines[lineIndex].split(",");
            double alt = Double.parseDouble(splittedData[8]);
            double lon = Double.parseDouble(splittedData[7]);
            double lat = Double.parseDouble(splittedData[6]);
            Point3D geom = new Point3D(lat, lon, alt);
            String time = splittedData[3];
            DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(time, parseFormatter);
            long utc = dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
            double[] orientations = myCoords.azimuth_elevation_dist(geom ,new Point3D(0,0,0));
            Point3D orientation = new Point3D(orientations[0], orientations[1], orientations[2]);
            Meta_data data = new MetaDataImpl(utc, orientation);
            GIS_element element = new GisElementImpl(geom, data);
            gisLayer.add(element);
        }
        return gisLayer;
    }
}

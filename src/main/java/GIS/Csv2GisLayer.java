package GIS;

import Coords.MyCoords;
import File_format.CsvReader;
import Geom.Point3D;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Csv2GisLayer {

    private static MyCoords myCoords = new MyCoords();

    public static GIS_layer csv2gisLayer(String path) {
        String[] lines = CsvReader.readLines(path); //create a String[] , where every index in the array is a line.
        GIS_layer gisLayer = new GisLayerImpl(); // state a Gis_layer with an implementation of interface
        for (int lineIndex = 2; lineIndex < lines.length; lineIndex++) { //since first two rows are irrelevant
            String[] splittedData = lines[lineIndex].split(",");
            double alt = Double.parseDouble(splittedData[8]);
            double lon = Double.parseDouble(splittedData[7]);
            double lat = Double.parseDouble(splittedData[6]);
            Point3D geom = new Point3D(lat, lon, alt);
            String time = splittedData[3];
            DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(time, parseFormatter);
            long utc = dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
            Meta_data data = new MetaDataImpl(utc);
            GIS_element element = new GisElementImpl(geom, data);
            gisLayer.add(element);
            if (gisLayer.size()==1){
                long newLayer_mark = LayerUtc();
                ((GisLayerImpl) gisLayer).setData(newLayer_mark);
            }
        }
        return gisLayer;
    }
        private static long LayerUtc(){
            DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.now();
            return dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        }
}

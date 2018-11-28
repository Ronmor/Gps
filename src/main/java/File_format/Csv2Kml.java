package File_format;

import GIS.*;
//import de.micromata.opengis.kml.v_2_2_0.Kml;

import java.io.FileNotFoundException;

/**
 * Google Earth uses the <Placemark> element to mark a position on the earth's surface.
 * <p>
 * Objects of complex elements can be created classically (e.g., new Placemark()).
 * In addition, it offers a static factory, called KmlFactory.
 * The factory defines a static method for the creation of every complex element defined in the KML standard.
 * Each method has the prefix 'create', followed by the name of the complex element (e.g., createPlacemark()).
 * Elements that are placed in packages other than the default KML package (gx, xal, or atom) have their package name
 * put between 'create' an its name (e.g., createGxPlaylist()):
 * "A Placemark is a Feature with associated Geometry.
 * In Google Earth, a Placemark appears as a list item in the Places panel.
 * A Placemark with a Point has an icon associated with it that marks a point on the Earth in the 3D viewer."
 */

public class Csv2Kml {

    public static void KmlWriter(String csvFilePath) throws FileNotFoundException {
        GIS_layer layer = Csv2GisLayer.csv2gisLayer(csvFilePath);
        Kml kml = new Kml();
        kml.addLayer(layer);
        String path = csvFilePath.replace(".csv", ".kml");
        kml.toKmlFile(path);
    }
}


package GIS;
import File_format.Kml;

import java.io.File;
import java.io.FileNotFoundException;

public class MultiCsv {

    public GIS_project dir2proj(String directoryPath) throws FileNotFoundException {
        GIS_project project = recursiveSearch(new File(directoryPath));
        Kml kml = new Kml();
        for (GIS_layer layer : project) {
            kml.addLayer(layer);
        }
        // save kml as a text file
        kml.toKmlFile(directoryPath + "/CsvsAsKml.kml");
        return project;
    }

    private GIS_project recursiveSearch(File directory) {
        GIS_project project = new GisProjectImpl();
        if (directory.isDirectory()) {
            try {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        GIS_project tempProject = recursiveSearch(file);
                        for (GIS_layer layer : tempProject) {
                            project.add(layer);
                        }
                    }
                    if (isCsv(file)) {
                        GIS_layer layer = Csv2GisLayer.csv2gisLayer(file.getAbsolutePath());
                        project.add(layer);
                    }
                }
            } catch (Exception e) {
                // unexpected catastrophe
            }
        }
        return project;
    }

    private boolean isCsv(File fileType){
        return fileType.getName().endsWith(".csv");
    }
}

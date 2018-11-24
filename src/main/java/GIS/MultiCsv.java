package GIS;
import java.io.File;

public class MultiCsv {
    private GIS_project project = new GisProjectImpl();

    public GIS_project dir2proj(File directory){
        if (directory.isDirectory()) {
            try {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (isCsv(file)) {
                        GIS_layer layer = Csv2GisLayer.csv2gisLayer(file.getAbsolutePath());
                        project.add(layer);
                    }
                }
            } catch (Exception e) {
                if (project.isEmpty()){
                    System.err.println("no Csv files in requested folder");
                }
            }
        }
        return project;
    }
    private boolean isCsv(File fileType){
        return fileType.getName().endsWith(".csv");
    }
}

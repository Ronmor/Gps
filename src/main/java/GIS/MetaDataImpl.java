package GIS;

import Coords.MyCoords;
import Geom.Point3D;

public class MetaDataImpl implements Meta_data {

    private long dataUTC;
    private Point3D orientation;

    public MetaDataImpl(long dataUTC, Point3D orientation) {
        this.dataUTC = dataUTC;
        this.orientation = orientation;
    }
    /**
     * returns the Universal Time Clock associated with this data;
     */
    @Override
    public long getUTC() {
        return dataUTC;
    }
    /**
     * @return the orientation: yaw, pitch and roll associated with this data;
     */
    @Override
    public Point3D get_Orientation() {
        MyCoords coords = new MyCoords();
        double[] orientations = coords.azimuth_elevation_dist(orientation,new Point3D(0,0,0));
        return new Point3D(orientations[0],orientations[1],orientations[2]);
    }
    public String toString(){
        return "UTC:"+getUTC()+"Oreientations"+get_Orientation().toString();
    }

}



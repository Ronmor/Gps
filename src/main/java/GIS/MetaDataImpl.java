package GIS;

import Geom.Point3D;

public class MetaDataImpl implements Meta_data {

    private final long utc;
    private final String str;
    private Point3D orientation = null;

    public MetaDataImpl(long utc, String str, Point3D orientation) {
        this.utc = utc;
        this.str = "utc=" + utc + " orientation=" + orientation + " " + str;
        this.orientation = orientation;
    }

    public MetaDataImpl(long utc, String str) {
        this.utc = utc;
        this.str = "utc=" + utc + " " + str;
    }

    //private Point3D orientation;
    public MetaDataImpl(long utc) { //was : public MetaDataImpl(long dataUTC ,Point3D orientation)
        this.utc = utc;
        this.str = "utc=" + utc;
        //this.orientation = orientation;
    }

    /**
     * returns the Universal Time Clock associated with this data;
     */
    @Override
    public long getUTC() {
        return utc;
    }

    @Override
    public String toString() {
        return str;
    }

    /**
     * AS REQUESTED FOR EX_2 , RETURN NULL .
     *
     * @return the orientation: yaw, pitch and roll associated with this data;
     */
    @Override
    public Point3D get_Orientation() {
       /* MyCoords coords = new MyCoords();
        double[] orientations = coords.azimuth_elevation_dist(orientation,new Point3D(0,0,0));
        return new Point3D(orientations[0],orientations[1],orientations[2]); */
        return orientation;
    }
}



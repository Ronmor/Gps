package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter{
        private long earthRadius = 6371*1000;
        private double PI = Math.PI;

        public MyCoords(){}
        /**
     * computes a new point which is the gps point transformed by a 3D vector (in meters)
     *
     * @param gps is a coordinate
     * @param local_vector_in_meter is a coordinate
     */
    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
        Point3D pointsAt = toRadian(diff(local_vector_in_meter,gps));
        pointsAt.set_x(earthRadius*Math.sin(pointsAt.get_x()));
        pointsAt.set_y(earthRadius*Math.sin(pointsAt.get_y())*Math.cos(gps.get_x()*PI)/180);
        return pointsAt;
    }
    private Point3D diff(Point3D gps, Point3D local_vector_in_meter){
        double xDiff = gps.get_x()-local_vector_in_meter.get_x();
        double yDiff = gps.get_y()-local_vector_in_meter.get_y();
        double zDiff = gps.get_z()-local_vector_in_meter.get_z();
        return new Point3D(xDiff,yDiff,zDiff);
    }
    private Point3D toRadian(Point3D CalculatedDiff){
        double r = (CalculatedDiff.get_x()*PI) / 180;
        double tetha = (CalculatedDiff.get_y()*PI) / 180;
        double alt = CalculatedDiff.get_z();
        return new Point3D(r , tetha,alt);
    }
    public Point3D toMeter(Point3D gps,Point3D local_vector_in_meter){
        Point3D pointsAt = toRadian(diff(gps,local_vector_in_meter));
        pointsAt.set_x(earthRadius*Math.sin(pointsAt.get_x()));
        pointsAt.set_y(earthRadius*Math.sin(pointsAt.get_y())*Math.cos(gps.get_x()*PI/180));
        return pointsAt;
    }

    /**
     * computes the 3D distance (in meters) between the two gps like points
     *
     * @param gps0 is a coordinate
     * @param gps1 is a coordinate
     */
    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        Point3D distanceMeasure =  toMeter(gps0 , gps1);
        return Math.sqrt(distanceMeasure.get_y()*distanceMeasure.get_y()+distanceMeasure.get_x()*distanceMeasure.get_x());
    }

    /**
     * computes the 3D vector (in meters) between two gps like points
     *
     * @param gps0 is a coordinate
     * @param gps1 is a coordinate
     */
    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {
        return null;
    }

    /**
     * computes the polar representation of the 3D vector be gps0-->gps1
     * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance
     *
     * @param gps0 is a coordinate
     * @param gps1 is a coordinate
     */
    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
        return new double[0];
    }

    /**
     * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
     *
     * @param p is a Point3D
     * @return if current Point3D is in range
     */
    @Override
    public boolean isValid_GPS_Point(Point3D p) {
        boolean isValid = false;
        double lat = p.get_x();
        double lon = p.get_y();
        double alt = p.get_z();
        if (-180<= lat && lat <= 180 && -90<=lon && lon <=90 && -450 <= alt){
            isValid = true;
        }
        return isValid;
    }

}

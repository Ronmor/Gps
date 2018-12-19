package Coords;

import Geom.Point3D;


public class MyCoords implements coords_converter{
        private long earthRadius = 6371*1000;
        public MyCoords(){}
        /**
     * computes a new point which is the gps point transformed by a 3D vector (in meters)
     *
     * @param gps is a coordinate
     * @param local_vector_in_meter is a coordinate
     */
    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
                double radded_lat = ArcSin(local_vector_in_meter.x() / earthRadius);
                double latDifference = r2d(radded_lat);
                double destination_latValue = gps.x() + latDifference;  //change to minus for map conversions
                double lonNorm = Cos(d2r(gps.x()));
                double raded_lon = ArcSin((local_vector_in_meter.y() / (earthRadius * lonNorm)));
                double lonDifference = r2d(raded_lon);
                double destination_lonValue = gps.y() + lonDifference;
                double destination_altValue = local_vector_in_meter.z() + gps.z();
                return new Point3D(destination_latValue, destination_lonValue, destination_altValue);
            }


    public Point3D diff(Point3D gps, Point3D local_vector_in_meter){
        double xDiff = -gps.x()+local_vector_in_meter.x();
        double yDiff = -gps.y()+local_vector_in_meter.y();
        double zDiff = -gps.z()+local_vector_in_meter.z();
        return new Point3D(xDiff,yDiff,zDiff);
    }
    private Point3D toRadian(Point3D CalculatedDiff){
        return new Point3D(d2r(CalculatedDiff.x()) , d2r(CalculatedDiff.y()),CalculatedDiff.z());
    }
    private Point3D toMeter(Point3D gps, Point3D local_vector_in_meter){
        Point3D pointsAt = toRadian(diff(gps,local_vector_in_meter));
        double lonNorm = Cos(d2r(gps.x()));
        return new Point3D(earthRadius*Sin(pointsAt.x()),earthRadius*Sin(pointsAt.y())*lonNorm,pointsAt.z());
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
        return Math.sqrt(distanceMeasure.y()*distanceMeasure.y()+distanceMeasure.x()*distanceMeasure.x());
    }

    /**
     * computes the 3D vector (in meters) between two gps like points
     *
     * @param gps0 is a coordinate
     * @param gps1 is a coordinate
     */
    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {
        return  toMeter(gps0,gps1);
    }

    /**
     * computes the polar representation of the 3D vector be gps0-->gps1
     * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance
     *  Azimuth refers to the rotation of the whole antenna around a vertical axis.
     *  It is the side to side angle.
     *  Calculation by the idea of loosening the main amount bracket and swing the whole dish all the way around in a 360 deg circle.
     *  Calculation by definition == Point3D.north_angle.
     * @param gps0 is a coordinate
     * @param gps1 is a coordinate
     */
    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
        double[] polarRepOfVector = new double[3];
        double dy = Sin(d2r(dY(gps0,gps1)));
        double lonNorm = Cos(d2r(gps1.x()));
        double alpha = dy*lonNorm;
        double beta = Sin(d2r(gps1.x()))*Cos(d2r(gps0.x()))-Cos(d2r(gps1.x()))*Sin(d2r(gps0.x()))*Cos(d2r(dY(gps0,gps1))); // This row == Sin(d2r(gps1.x-gps0.x))
        double azimuth = Math.atan2(alpha,beta);
        double distance = distance3d(gps0,gps1);
        polarRepOfVector[0] = azimuth < 0 ? (r2d(azimuth)+360) : r2d(azimuth); //This gives a close answer.
        double elevation = r2d(dZ(gps0,gps1)/distance - distance/(2*earthRadius));
        polarRepOfVector[1] = elevation;
        polarRepOfVector[2] = distance;
        return polarRepOfVector;
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
        double lat = p.x();
        double lon = p.y();
        double alt = p.z();
        if (-180<= lat && lat <= 180 && -90<=lon && lon <=90 && -450 <= alt){
            isValid = true;
        }
        return isValid;
    }
    /**
     *
     * @param p  is a coordinate
     * @param p1 is a coordinate
     * @return Delta(Y)
     */
    private double dY(Point3D p, Point3D p1){ return p1.y()-p.y();}
    private double dX(Point3D p, Point3D p1){ return p1.x()-p.x();}
    private double dZ(Point3D p, Point3D p1){ return p1.z()-p.z();}
    private double d2r(double x){
        return Point3D.d2r(x);
    }
    private double r2d(double x){ return Point3D.r2d(x);}
    private double Sin(double a){ return Math.sin(a); }
    private double Cos(double a){ return Math.cos(a); }
    private double ArcSin(double a){ return Math.asin(a); }
    private double sizeOfVector(Point3D p){
        Point3D vectorZero = new Point3D(0,0,0);
        return p.distance3D(vectorZero);
    }
    private double ArcCos(double a){ return Math.acos(a); }
    private double angleBetween2Vectors(Point3D v, Point3D u){ return ArcCos(Point3D.productOfVectors(v,u) / sizeOfVector(v) * sizeOfVector(u));}

}

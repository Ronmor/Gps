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
        pointsAt.set_x(earthRadius*Math.sin(pointsAt.x()));
        pointsAt.set_y(earthRadius*Math.sin(pointsAt.y())*Math.cos(gps.x()*PI)/180);
        return pointsAt;
    }

    public Point3D diff(Point3D gps, Point3D local_vector_in_meter){
        double xDiff = gps.x()-local_vector_in_meter.x();
        double yDiff = gps.y()-local_vector_in_meter.y();
        double zDiff = gps.z()-local_vector_in_meter.z();
        return new Point3D(xDiff,yDiff,zDiff);
    }
    private Point3D toRadian(Point3D CalculatedDiff){
        double r = (CalculatedDiff.x()*PI) / 180;
        double tetha = (CalculatedDiff.y()*PI) / 180;
        double alt = CalculatedDiff.z();
        return new Point3D(r , tetha,alt);
    }
    public Point3D toMeter(Point3D gps,Point3D local_vector_in_meter){
        Point3D pointsAt = toRadian(diff(gps,local_vector_in_meter));
        pointsAt.set_x(earthRadius*Math.sin(pointsAt.x()));
        pointsAt.set_y(earthRadius*Math.sin(pointsAt.y())*Math.cos(gps.x()*PI/180));
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
        return new Point3D(gps1.x()-gps0.x(),gps1.y()-gps0.y(),gps1.z()-gps0.z());
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
       // double beta = Sin(d2r(dX(gps1,gps0)));
        double azimuth = Math.atan2(alpha,beta);
        double distance = distance3d(gps0,gps1);
        polarRepOfVector[0] = azimuth < 0 ? (r2d(azimuth)+360) : r2d(azimuth); //This gives a close answer.
        double elevation = gps0.up_angle(gps1);
        polarRepOfVector[1] = elevation;
        polarRepOfVector[2] = distance;
        return polarRepOfVector;
        /**
        Point3D diff = this.diff(gps0, gps1);
        double size = diff.distance3D(0, 0, 0);
        double r = Math.sqrt(size);
        double phi = Math.acos(diff.z() / r);
        double theta = Math.acos(diff.x() / Math.sqrt(diff.x() * diff.x() + diff.y() * diff.y()));
        double azimuth = gps0.north_angle(gps1);
        double elevation = Math.asin(gps0.z()/gps0.distance3D(gps1));
        double dist = distance3d(gps1,gps0);
        return new double[]{azimuth, elevation, dist}; **/
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
    public double dY(Point3D p , Point3D p1){ return p1.y()-p.y();}
    public double dX(Point3D p , Point3D p1){ return p1.x()-p.x();}
    public double dZ(Point3D p , Point3D p1){ return p1.z()-p.z();}
    public double d2r(double x){
        return Point3D.d2r(x);
    }
    public double r2d(double x){ return Point3D.r2d(x);}
    public double Sin(double a){ return Math.sin(a); }
    public double Cos(double a){ return Math.cos(a); }
    public double ArcSin(double a){ return Math.asin(a); }
    public double sizeOfVector(Point3D p){
        Point3D vectorZero = new Point3D(0,0,0);
        return p.distance3D(vectorZero);
    }
    public double ArcCos(double a){ return Math.acos(a); }
    public double angleBetween2Vectors(Point3D v , Point3D u){ return ArcCos(Point3D.productOfVectors(v,u) / sizeOfVector(v) * sizeOfVector(u));}

}

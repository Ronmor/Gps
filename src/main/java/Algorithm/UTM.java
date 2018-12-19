package Algorithm;

/**
 *UTM - Universal Transverse Mercator - coordinates to latitude and longitude coordinates
 * Based on the WGS84 Datum - the calculator is valid for the northern hemisphere.
 * The UTM north coordinate is the projected distance from equator for all zones.
 * The east coordinate is the distance from the central median.
 * The Universal Transverse Mercator (UTM) conformal projection uses a 2-dimensional Cartesian coordinate system
 * to give locations on the surface of the Earth.
 * Like the traditional method of latitude and longitude, it is a horizontal position representation,
 * i.e. it is used to identify locations on the Earth independently of vertical position.
 * However, it differs from that method in several respects.
 * The UTM system is not a single map projection.
 * The system instead divides the Earth into sixty zones,
 * each being a six-degree band of longitude,
 * and uses a secant transverse Mercator projection in each zone.
 * Online Calculator : https://www.engineeringtoolbox.com/utm-latitude-longitude-d_1370.html
 * Wikipedia Page: https://en.wikipedia.org/wiki/Universal_Transverse_Mercator_coordinate_system?oldformat=true
 * This Class provides a convenient tool to physically move elements and update it's locations throw the selected Path.
 * It basically makes it much easier to accurate movements
 */

public class UTM {

    private final double Easting;
    private final double Northing;
    private final int Zone;
    private final char Letter;

    public UTM(double easting, double northing, int zone, char letter) {
        Easting = easting;
        Northing = northing;
        Zone = zone;
        Letter = letter;
    }

    public double getEasting() {
        return Easting;
    }

    public double getNorthing() {
        return Northing;
    }

    public int getZone() {
        return Zone;
    }

    public char getLetter() {
        return Letter;
    }
    public boolean equals(UTM other){
        boolean ans = false;
        double EastingDiff = other.getEasting() - this.getEasting();
        double NorthingDiff = other.getNorthing() - this.getNorthing();
        return (Math.abs(EastingDiff)<=0.8 && Math.abs(NorthingDiff)<=0.8 && this.Letter==other.getLetter() && Zone==other.getZone());
    }
}

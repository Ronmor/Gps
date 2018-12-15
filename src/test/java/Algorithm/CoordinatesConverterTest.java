package Algorithm;

import Geom.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinatesConverterTest {
    private UTM utm;
    private static CoordinatesConverter converter = new CoordinatesConverter();
    Point3D toUTM;

    @Before
    public void setUp(){
        toUTM = new Point3D(32.104551,35.203502);
        utm = new UTM(707918.49,3554149.98,36,'S');
    }

    @Test
    public void deg2UTM() {
        UTM actual = CoordinatesConverter.Deg2UTM(toUTM);
        Assert.assertTrue(actual.equals(utm));
    }

    @Test
    public void utmToPoint3D() {
        Point3D actual = CoordinatesConverter.UtmToPoint3D(utm);
        Assert.assertTrue(actual.close2equals(actual,0.01));
    }
}
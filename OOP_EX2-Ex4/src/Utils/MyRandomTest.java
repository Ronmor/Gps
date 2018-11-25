package Utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Circle;
import Geom.Point3D;

class MyRandomTest {

	@Test
	void testMyRandomLong() {
		long s = 31;
		MyRandom r1 = new MyRandom(s);
		MyRandom r2 = new MyRandom(s);
		if(r1.nextDouble() != r2.nextDouble()) {
			fail("ERR two same seed random should act the same!!");
		}
		 r1 = new MyRandom();
		 r2 = new MyRandom();
		if(r1.nextDouble() == r2.nextDouble()) {
			fail("ERR two same seed random should NOT act the same!!");
		}
	}

//	@Test
	void testMyRandom() {
//		fail("Not yet implemented");
	}
	@Test
	void testMyRandomRange() {
		double min = 0.0001;
		double max = 0.0005;
		MyRandom r1 = new MyRandom();
		int times = 1000;
		for(int i=0;i<times;i++) {
			double d = r1.nextInRange(min, max);
			if(d<min || d>= max) {
				fail("ERR: the random should be between ["+min+","+max+")");
			}
		}
	}
	@Test
	void testMyRandomRange1() {
		double min = -10.33;
		double max = min+1;
		MyRandom r1 = new MyRandom();
		int times = 1000;
		for(int i=0;i<times;i++) {
			double d = r1.nextInRange(min, max);
			if(d<min || d>= max) {
				fail("ERR: the random should be between ["+min+","+max+")");
			}
		}
	}
	@Test
	void testMyRandomRange2() {
		double min = -10.33;
		double max = min+1000000;
		MyRandom r1 = new MyRandom();
		int times = 1000;
		for(int i=0;i<times;i++) {
			double d = r1.nextInRange(min, max);
			if(d<min || d>= max) {
				fail("ERR: the random should be between ["+min+","+max+")");
			}
		}
	}
	@Test
	void testMyRandomCircle() {
		Point3D cen = new Point3D(100,100,0);
		double rad = 10;
		Circle c = new Circle(cen,rad);
		MyRandom r1 = new MyRandom();
		int times = 100000;
		int count =0;
		for(int i=0;i<times;i++) {
			Point3D p = r1.randomPointInside(c);
			double dist = p.distance3D(c.get_cen());
			if(dist>c.get_radius()) {
				fail("ERR arandom point should be inside the circle");
			}
			if(dist<c.get_radius()/2) {count++;}
		}
		// BUG should be a fail..
		double rp = count*100.0/times; 
		// shoule be about 25%
		double err = Math.abs(25-rp);
		if(err>2) {
			fail("ERR using a random point over "+times +" should lead to ±25%");
		}
	//	System.out.println("% below half a radius "+rp);
	}
}

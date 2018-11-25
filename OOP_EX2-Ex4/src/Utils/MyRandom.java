package Utils;

import java.util.Random;

import Geom.Circle;
import Geom.Point3D;

/**
 * represents a random number generator - with controlled seed
 * @author ben-moshe
 *
 */
public class MyRandom {
	private long _seed;
	private Random _rand;
	
	public MyRandom(long seed) {
		_rand = new Random(seed);
		_seed = seed;
	}
	public MyRandom() {
		this(new Random().nextLong());
	}
	
	public double nextDouble() {
		return _rand.nextDouble();
	}
	/** 
	 * should return a random number with in [min,max)
	 * @param min
	 * @param max
	 * @return
	 */
	public double nextInRange(double min, double max) {
		double r = this.nextDouble();
		double dx = max-min;
		double ans = min + dx*r;
		return ans;
	}
	public Point3D randomPointInside(Circle c) {
		double x=c.get_cen().x(),y=c.get_cen().y(),z = 0;
		double r = c.get_radius();
		double n = nextInRange(0,1);
		n = Math.sqrt(n);
		double norm = r*n;
		double ang = nextInRange(-Math.PI, Math.PI);
		double dx = norm * Math.cos(ang);
		double dy = norm * Math.sin(ang);
		return new Point3D(x+dx,y+dy,z);
	}
}

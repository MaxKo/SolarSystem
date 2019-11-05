package kerberos.solar.system.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolarSystem {

	
	List<CosmicBody> bodies = new ArrayList<CosmicBody>();
	
	public SolarSystem() {
		bodies.add(new CosmicBody("Sun", 50, 50, 3, 1000, 0, 0) );
		bodies.add(new CosmicBody("Earth", 60, 50, 1, 2, 0, -1));
	}


	public void draw(Graphics g) {
		bodies.stream().forEach(cs -> cs.drawSwing(g));
	}
	
	
	public void calcNewPositions() {
		for (CosmicBody cs1 : bodies) {
			double shiftX = 0;
			double shiftY = 0;
			
			for (CosmicBody cs2 : bodies) {
				if (cs1 == cs2) continue;
				
				double force = forceBetween(cs1, cs2);
				
				double dx = cs2.getX() - cs1.getX();
				double dy = cs2.getY() - cs1.getY();
				double gip = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				
				
				
				shiftX +=  dx / gip / cs1.getM(); 
				shiftY +=  dy / gip / cs1.getM();
				
				//shiftX += forceBetween(cs1.getX(), cs2.getX(), cs1.getM(), cs2.getM(), "x:");
				//shiftY += forceBetween(cs1.getY(), cs2.getY(), cs1.getM(), cs2.getM(), "y:");
			}
			
			cs1.setVx(cs1.getVx() + shiftX);
			cs1.setVy(cs1.getVy() + shiftY);
		}
		
		bodies.stream().forEach(cs -> cs.move());
		
	}
	
	public double forceBetween(CosmicBody cs1, CosmicBody cs2) {
		double result = 1;
		
		double distX = Math.abs(cs1.getX() - cs2.getX()); 
		double distY = Math.abs(cs1.getY() - cs2.getY());
		
		double absDist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		
		result *= 0.01;
		result *= cs1.getM();
		result *= cs2.getM();
		result /= Math.pow(absDist, 2);
		
		return result;
	}
	
	public double forceBetween(double c1, double c2, double m1, double m2, String c) {
		double result = 1;
		double r = c1 - c2;
		
		result *= 0.1;
		result *= m1;
		result *= m2;
		result /= Math.pow(r, 2);
		
		result *= Math.signum(c2 - c1);
		
		System.out.print(c + Math.signum(c2 - c1) + " force:" + round(result));
		
		return result;
	}
	

	
	@Override
	public String toString() {
		return bodies.stream()
	            .map( Object::toString )
	            .collect( Collectors.joining( ", " ) );
	}
	
	private double round(double k) {
		return Math.round(k * 100) / 100;
	}
	//private getScaleShift
}

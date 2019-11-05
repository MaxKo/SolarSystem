package kerberos.solar.system.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolarSystem {

	
	List<CosmicBody> bodies = new ArrayList<CosmicBody>();
	
	public SolarSystem() {
		bodies.add(new CosmicBody("Sun", 750, 350, 30, 10000, 0, 0) );
		bodies.add(new CosmicBody("Earth", 1000, 350, 10, 20, 0, -2));
		bodies.add(new CosmicBody("Mars", 1200, 350, 10, 20, 0, -1.5));
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
				
				double dx = cs2.getX() - cs1.getX();
				double dy = cs2.getY() - cs1.getY();
				double gip = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				
				double force = forceBetween(cs1, cs2);
				
				shiftX +=  force * dx / gip / cs1.getM(); 
				shiftY +=  force * dy / gip / cs1.getM();
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
		
		result *= 0.1;
		result *= cs1.getM();
		result *= cs2.getM();
		result /= Math.pow(absDist, 2);
		
		return result;
	}

	
	@Override
	public String toString() {
		return bodies.stream()
	            .map( Object::toString )
	            .collect( Collectors.joining( ", " ) );
	}
}

package kerberos.solar.system.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class SolarSystem {

	
	List<CosmicBody> bodies = new ArrayList<CosmicBody>();
	
	public SolarSystem() {
							     //          X    Y     R     M     VX   VY
		bodies.add(new CosmicBody("Sun", 	0,  0, 		30,   150, 	0, 	 0		, true));
		bodies.add(new CosmicBody("Mercury", 50, 0, 	3,      1, 	0, 	-0.53));
		bodies.add(new CosmicBody("Earth", 	250, 0, 	4, 		2, 	0, 	-0.2));
		bodies.add(new CosmicBody("Moon", 	270, 0, 	1, 		1, 	0, 	-0.3));
		bodies.add(new CosmicBody("Mars", 	450, 0, 	4, 		2, 	0, 	-0.15));
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
				double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				
				double force = 0.1 * cs1.getM() * cs2.getM() / Math.pow(dist, 2);
				
				shiftX +=  force * dx / dist / cs1.getM(); 
				shiftY +=  force * dy / dist / cs1.getM();
			}
			
			cs1.setVx(cs1.getVx() + shiftX);
			cs1.setVy(cs1.getVy() + shiftY);
		}
		
		bodies.stream().forEach(cs -> cs.move());
	}
	
	@Override
	public String toString() {
		return bodies.stream()
	            .map( Object::toString )
	            .collect( Collectors.joining( ", " ) );
	}

	public void draw3D(GL2 gl2, GLU glu) {
		bodies.stream().forEach(cs -> cs.draw3D(gl2, glu));
	}
}

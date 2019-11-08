package kerberos.solar.system.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class SolarSystem {

	
	List<CosmicBody> bodies = new ArrayList<CosmicBody>();
	private GL gl2;
	
	public SolarSystem() {
	     initSystem();
	}

	private void initSystem() {
								//          		X    Y   Z  R     M     VX   VY VZ
		bodies.add(new CosmicBody("Sun", 			0,  0, 	 0,	30,   150, 	0, 	 0,		0, true, "2k_sun.png"));
		bodies.add(new CosmicBody("Mercury", 		50, 0,  0,	3,      2, 	0, 	-0.53, 	0 , false, "2k_mercury.png"));
		bodies.add(new CosmicBody("Asteroid",		0, 55,  0,	1,    0.1, 	0.05, -0.1, 	-0.53));		
		bodies.add(new CosmicBody("Earth", 			250, 0,  0,	4, 		6, 	0, 	-0.2, 	0, false, "earthmap1k.png"));
		bodies.add(new CosmicBody("Moon", 			280, 0,  0, 1, 		1, 	0, 	-0.3,	0, false, "2k_moon.png"));
		bodies.add(new CosmicBody("Mars", 			450, 0,  0, 4, 		2, 	0, 	-0.15,	0, false, "2k_mars.png"));
		
		bodies.stream().forEach(cb -> cb.initTexture(gl2));
	}

	public SolarSystem(GL gl2) {
		this.gl2 = gl2;
		initSystem();
	}

	public void draw(Graphics g) {
		bodies.stream().forEach(cs -> cs.drawSwing(g));
	}
	
	HashMap<String, Double> forceCache = new HashMap<String, Double>();
	
	private double getCachedForce(CosmicBody cs1, CosmicBody cs2, Double force) {
		String key = cs1.getName() + cs2.getName();
		Double result = forceCache.get(cs1.getName() + cs2.getName());
		
		if (result == null) {
			forceCache.put(key, force);
			return force;
		}
		
		if( result < force) {
			forceCache.put(key, force);
			return force;
		}
		
		forceCache.remove(key);
		
		return result;
	}
	
	public void calcNewPositions() {
		for (CosmicBody cs1 : bodies) {
			double shiftX = 0;
			double shiftY = 0;
			double shiftZ = 0;
			
			for (CosmicBody cs2 : bodies) {
				if (cs1 == cs2) continue;
				
				double dx = cs2.getX() - cs1.getX();
				double dy = cs2.getY() - cs1.getY();
				double dz = cs2.getZ() - cs1.getZ();
				double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
				dist = dist < 7f ? 7f : dist;
				
				double force = 0.1 * cs1.getM() * cs2.getM() / Math.pow(dist, 2);

				force = getCachedForce(cs1, cs2, force);
				
				shiftX +=  force * dx / dist / cs1.getM(); 
				shiftY +=  force * dy / dist / cs1.getM();
				shiftZ +=  force * dz / dist / cs1.getM();
			}
			
			cs1.setVx(cs1.getVx() + shiftX);
			cs1.setVy(cs1.getVy() + shiftY);
			cs1.setVz(cs1.getVz() + shiftZ);
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

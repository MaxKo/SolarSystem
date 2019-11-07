package kerberos.solar.system.model;

import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class CosmicBody {
	
	String name;
	double x = 0;
	double y = 0;
	double r = 0;
	double m = 0;
	
	double vx = 0;
	double vy = 0;
	boolean isShine = false;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public CosmicBody(String name, double x, double y, double r, double m, double vx, double vy) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.name = name;
		this.m = m;
		this.vx = vx;
		this.vy = vy;
	}
	
	public CosmicBody(String name, double x, double y, double r, double m, double vx, double vy, boolean isShine) {
		this(name, x, y, r, m, vx, vy);
		
		this.isShine = isShine;
	}

	public double getR() {
		return r;
	}
	
	public void setR(double r) {
		this.r = r;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public void drawSwing(Graphics g) {
		//int scale = 10;
		g.drawOval((int)(x - r / 2), (int)(y - r / 2), (int)r, (int)r );
	}
	
	@Override
	public String toString() {
		return "CosmicBody [name:" + name + ", x=" + round(x) + ", y=" + round(y) + ", m=" + round(m) + ", vx=" + round(vx) + ", vy="+ round(vy) +"]";
	}

	private double round(double k) {
		return Math.round(k * 100) / 100;
	}

	public void move() {
		x += vx;
		y += vy;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public void draw3D(GL2 gl2, GLU glu) {
        // Apply texture.
        //earthTexture.enable(gl2);
        //earthTexture.bind(gl2);

        // Draw sphere (possible styles: FILL, LINE, POINT).
		
		gl2.glTranslatef(scale(x), scale(y), 0);
		
		if (isShine) setLight(gl2);
		
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_LINE);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = (float)r / 100 /*6.378f*/;
        final int slices = 32;
        final int stacks = 32;
        glu.gluSphere(earth, radius, slices, stacks);
        //glu.gluDeleteQuadric(earth);

        // Save old state.
        //gl2.glPushMatrix();
        
        gl2.glTranslatef(scale(-x), scale(-y), 0);
	}
	private void setLight(GL2 gl2) {
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-0, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		
	}

	private float scale(double n) {
		return (float) (0.01f * n); 
	}
	
}

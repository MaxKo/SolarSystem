package kerberos.solar.system.model;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class CosmicBody {
	
	String name;
	double x = 0;
	double y = 0;
	double z = 0;
	double r = 0;
	double m = 0;
	
	double rotateAxisAngle = 23;
	double rotateSpeed = -0.2;
	double rotagteAngle;
	
	double vx = 0;
	double vy = 0;
	double vz = 0;
	boolean isShine = false;
	String textureName;
	Texture texture = null;
	
	
	
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

	public CosmicBody(String name, double x, double y, double z, double r, double m, double vx, double vy, double vz) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.name = name;
		this.m = m;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		
	}

	public CosmicBody(String name, double x, double y, double z, double r, double m, double vx, double vy, double vz, boolean isShine, String textureName) {
		this(name, x, y, z, r, m, vx, vy, vz);
		
		this.isShine = isShine;
		this.textureName = textureName;
	}

	
	public boolean isShine() {
		return isShine;
	}

	public void setShine(boolean isShine) {
		this.isShine = isShine;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void initTexture(GL gl2) {
		if (hasTexture() == false) return;
		try {
				InputStream stream = getClass().getResourceAsStream("/" + textureName);
				
	            TextureData data = TextureIO.newTextureData(gl2.getGLProfile(), stream, false, "png");
	            texture = TextureIO.newTexture(data);
	        }
	        catch (IOException exc) {
	            exc.printStackTrace();
	            System.exit(2);
	        }
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getVz() {
		return vz;
	}

	public void setVz(double vz) {
		this.vz = vz;
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
		z += vz;
		
		rotagteAngle += rotateSpeed;
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
		// Draw sphere (possible styles: FILL, LINE, POINT).
		
		gl2.glTranslatef(scale(x), scale(y), scale(z));
		gl2.glRotated(rotagteAngle, 0, 0, rotateAxisAngle);
		
		if (isShine) setLight(gl2);
		if (hasTexture()) drawTexture(gl2);
		
        GLUquadric glBody = glu.gluNewQuadric();
        glu.gluQuadricTexture(glBody, true);
        glu.gluQuadricDrawStyle(glBody, hasTexture() ? GLU.GLU_FILL : GLU.GLU_LINE);
        glu.gluQuadricNormals(glBody, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(glBody, GLU.GLU_OUTSIDE);
        final float radius = (float)r / 100 /*6.378f*/;
        final int slices = 32;
        final int stacks = 32;
        glu.gluSphere(glBody, radius, slices, stacks);
        glu.gluDeleteQuadric(glBody);
        
        gl2.glRotated(-rotagteAngle, 0, 0, rotateAxisAngle);
        gl2.glTranslatef(scale(-x), scale(-y), scale(-z));
	}
	
	private void drawTexture(GL2 gl2) {
        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl2.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, (isShine) ? 0f : 0.5f);
		
        texture.enable(gl2);
	    texture.bind(gl2);		
	}

	private boolean hasTexture() {
		return !(textureName == null || "".equals(textureName));
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
        
        // Enable lighting in GL.
        gl2.glEnable(GL2.GL_LIGHT1);
        gl2.glEnable(GL2.GL_LIGHTING);
		
	}

	private float scale(double n) {
		return (float) (0.01f * n); 
	}
	
}

package kerberos.solar.system;


public class CPoint {
	int x = 0;
	int y = 0;
	int cameraDist = 10;
	
	
	public int getCameraDist() {
		return cameraDist;
	}
	public void setCameraDist(int cameraDist) {
		this.cameraDist = cameraDist;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	float getCenterX() {
		return (float) x / 800 * cameraDist;
	}
	float getCenterY() {
		return (float) y / 800 * cameraDist;
	}
	float getCenterZ() {
		return (float) x - y / 800 * cameraDist;
	}
	
	void moveCameraDist(int shift) {
		cameraDist += shift;
	}
	
	double getEyeX() {
		return Math.sin((float) x / 360) * 30;
	}
	
	double getEyeY() {
		return Math.cos((float) y / 360) * 30;
	}
	
	double getEyeZ() {
		return 0;
	}
	
}

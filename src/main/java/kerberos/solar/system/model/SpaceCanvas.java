package kerberos.solar.system.model;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class SpaceCanvas extends JPanel implements MouseListener {

	private static final long serialVersionUID = 7193789365744431923L;
		
	SolarSystem sc = new SolarSystem();      // Contains the image to draw on MyCanvas

		
	
	    public SpaceCanvas(){
	        // Initialize img here.
	        this.addMouseListener(this);
	        
	       // new ThreadMover().run();
	    }

	    public void paintComponent(Graphics g){
	        // Draws the image to the canvas
	       // g.drawOval(x, y, width, height);// drawImage(img, 0, 0, null);
	        
	    	g.clearRect(0, 0, getWidth(), getHeight());
	    	sc.draw(g);
	    }

	    public void mouseClicked(MouseEvent e){
	       // int mx = e.getX();
	        //int my = e.getY();

	        //System.out.println("mouse clicked x:" + mx + " y:" + my);
	        
	        sc.calcNewPositions();
	        
	        //System.out.println(sc.toString());
	        
	        
	        paintComponent(this.getGraphics());
	        //sc.g.ge
	        
	        //Graphics g = img.getGraphics();
	        //g.fillOval(x, y, 3, 3);
	        //g.dispose();
	    }

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	    // ... other MouseListener methods ... //
	
}

package kerberos.solar.system;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import kerberos.solar.system.model.SolarSystem;
/*
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
*/
/**
 * A minimal JOGL demo.
 * 
 * @author <a href="mailto:kain@land-of-kain.de">Kai Ruhl</a>
 * @since 26 Feb 2009
 */
public class SolarSystemApplication extends GLCanvas implements GLEventListener, MouseWheelListener, MouseListener, MouseMotionListener {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;
    
    private SolarSystem solarSystem;// = new SolarSystem();
    
    //int cameraDist = 10;
    //Point camPosition = new Point(0, 0);
    
    CPoint camPosition = new CPoint();

    /**
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public SolarSystemApplication(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
        addMouseWheelListener(this);
        addMouseListener(this);
        
        addMouseMotionListener(this);
    }

    /**
     * @return Some standard GL capabilities (with alpha).
     */
    private static GLCapabilities createGLCapabilities() {
        GLCapabilities capabilities = new GLCapabilities(null);
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        
        return capabilities;
    }

    /**
     * Sets up the screen.
     * 
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
        final GL gl_ = drawable.getGL();
        final GL gl2 = gl_.getGL2();//drawable.getGL();
        // Enable z- (depth) buffer for hidden surface removal. 
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL2.GL_LEQUAL);

        // Enable smooth shading.
        //gl2. glShadeModel(GL.GL_SMOOTH_LINE_WIDTH_RANGE);

        // Define "clear" color.
        gl2.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
        gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Create GLU.
        glu = new GLU();
        
        solarSystem = new SolarSystem(gl2);

        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    /**
     * The only method that you should implement by yourself.
     * 
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    public void display(GLAutoDrawable drawable) {
    	if (!animator.isAnimating()) return;

    	final GL2 gl2 = drawable.getGL().getGL2();//  drawable.

    	solarSystem.calcNewPositions();
    	
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        setCamera(gl2, glu);
    	
    	solarSystem.draw3D(gl2, glu);
    }

    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.getGL2().glViewport(0, 0, width, height);
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     * @param distance The distance from the screen.
     */
    
    double iter = 0;
    
    double getXPosition(){
    	return Math.cos(iter++ / 360) * 10;
    }
    double getYPosition(){
    	return Math.sin(iter++ / 360) * 10;
    }
    
    private void setCamera(GL gl_, GLU glu) {
        // Change to projection matrix.
    	GL2 gl2 = gl_.getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1 , 4000 );
        glu.gluLookAt(camPosition.getEyeX(), camPosition.getCenterY(), camPosition.getCameraDist(),
        		camPosition.getCenterX(), 
        		camPosition.getCenterY(), 
        		0, 
        			0, 1, 0);

        // Change back to model view matrix.
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }

    /**
     * Starts the JOGL mini demo.
     * 
     * @param args Command line args.
     */
    public final static void main(String[] args) {
    	
    	JFrame frame = new JFrame("Border Layout");
		JButton button,button1, button2, button3,button4;
		button = new JButton("left");
		button1 = new JButton("right");
		button2 = new JButton("top");
		button3 = new JButton("bottom");
		
		frame.add(button,BorderLayout.WEST);
		frame.add(button1, BorderLayout.EAST);
		frame.add(button2, BorderLayout.NORTH);
		frame.add(button3, BorderLayout.SOUTH);
		frame.setSize(1920, 1080);
		//frame.add(button4, BorderLayout.CENTER);
		
		//frame.setSize(300,300);  
		//frame.setVisible(true);  
    	
		JFrame frameC = new JFrame("center");
		GLCapabilities capabilities = createGLCapabilities();
        SolarSystemApplication canvas = new SolarSystemApplication(capabilities, 3840, 2000);
        canvas.setSize(1920, 1080);
        //JFrame frame = new JFrame("Solar system JOGL ");
        frameC.setSize(canvas.getPreferredSize()); 
        frame.getContentPane().add(canvas, BorderLayout.CENTER);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();
        
        
        
    }

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}



	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//cameraDist += e.getWheelRotation();
		camPosition.moveCameraDist(e.getWheelRotation());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	Point p = new Point(0, 0);
	@Override
	public void mousePressed(MouseEvent e) {
		p.set(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		camPosition.setX(p.getX() - e.getX());
		camPosition.setY(-(p.getY() - e.getY()));
		
		p.set(0, 0);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		camPosition.setX(p.getX() - e.getX());
		camPosition.setY(-(p.getY() - e.getY()));
		//camPosition.setZ(-(p.getY() - e.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
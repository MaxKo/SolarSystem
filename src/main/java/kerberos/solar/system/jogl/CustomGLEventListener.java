package kerberos.solar.system.jogl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class CustomGLEventListener implements GLEventListener {

    
    @Override
    public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
        OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
        
    }
    
    @Override
    public void init( GLAutoDrawable glautodrawable ) {
    }
    
    @Override
    public void dispose( GLAutoDrawable glautodrawable ) {
    }
    
    @Override
    public void display( GLAutoDrawable glautodrawable ) {
        //OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
    	//OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
    	OneTriangle.render( glautodrawable.getGL().getGL3(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
    }
}

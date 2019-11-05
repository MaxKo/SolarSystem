package kerberos.solar.system;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import kerberos.solar.system.model.SpaceCanvas;

@SpringBootApplication
public class SwingApp extends JFrame {

    public SwingApp() {

        initUI();
    }

    private void initUI() {
        var quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

//        createLayout(quitButton);

        setTitle("Quit button");
        setSize(2000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        SpaceCanvas spaceCanvas = new SpaceCanvas();
        add(spaceCanvas);
        
    }

    private void createLayout(JComponent... arg) {

        var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );
    }

    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(SwingApp.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            var ex = ctx.getBean(SwingApp.class);
            ex.setVisible(true);
        });
    }
}
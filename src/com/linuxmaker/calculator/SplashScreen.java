/**
 * 
 */
package com.linuxmaker.calculator;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class SplashScreen extends JWindow implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void run() {
		setSize(365, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        
        try {
        	Thread.sleep(6000);
		} catch (InterruptedException e) {
			dispose();
		}
        dispose();
	}
	
	public void paint(Graphics g) {
		//Image splashImage = getToolkit().getImage("resources/screen.jpg");
		BufferedImage splashImage;
		try {
			splashImage = ImageIO.read(new File("resources/screen.jpg"));
			g.drawImage(splashImage, 0, 0, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

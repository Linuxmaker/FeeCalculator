/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.FileNotFoundException;

import com.linuxmaker.calculator.gui.views.AllinFeeFrame;


/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		Thread splashThread = new Thread(new SplashScreen());
		splashThread.start();
		
		AllinFeeFrame allinFee = new AllinFeeFrame();
		allinFee.main(null);
	}
}

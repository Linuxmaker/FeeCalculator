/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.File;
import com.linuxmaker.calculator.gui.views.AllinFeeFrame;
import com.linuxmaker.calculator.gui.views.SettingsFrame;


/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Thread splashThread = new Thread(new SplashScreen());
		splashThread.start();
		SettingsFrame settings = new SettingsFrame();
		
		if (!new File(System.getProperties().getProperty("user.home")+File.separator+".settings.cfg").exists()) {
			settings.main(null);
			
		} else {
			AllinFeeFrame allinFee = new AllinFeeFrame();
			allinFee.main(null);
		}
		AllinFeeFrame allinFee = new AllinFeeFrame();
		allinFee.main(null);
	}

}

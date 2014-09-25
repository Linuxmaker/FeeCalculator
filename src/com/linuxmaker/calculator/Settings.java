/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.linuxmaker.calculator.Constants.DIRECTORY;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Settings {
	private static String setting;
	
	String defaultFile = "resources"+File.separator+"settings.cfg";
	String directory;
	public void generateSettings(String workingHours,
								String startCity, 
								String maxDistance, 
								String minFee, 
								String drivingTime, 
								String carConsumption,
								String publicTransport,
								Double railBonus)
	{
		Properties settings = new Properties();
		settings.setProperty("workinghours", workingHours);
		settings.setProperty("pointOfDeparture", startCity);
		settings.setProperty("maxdistance", maxDistance);
		settings.setProperty("minFee", minFee);
		settings.setProperty("publicTransport", publicTransport);
		settings.setProperty("railCard", String.valueOf(railBonus));
		
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			directory = DIRECTORY;
			setting = "settings.cfg";
		} else {
			directory = "."+DIRECTORY;
			setting = ".settings.cfg";
		}
		
		FileWriter writer;
		try {
			writer = new FileWriter(new File(System.getProperties().getProperty("user.home")+File.separator+directory+File.separator+setting));
			settings.store(writer, null);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readSettings(String key) {
		Properties properties = new Properties();
		FileInputStream in;
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			directory = DIRECTORY;
			setting = "settings.cfg";
		} else {
			directory = "."+DIRECTORY;
			setting = ".settings.cfg";
		}
		try {
			if (new File(System.getProperties().getProperty("user.home")+File.separator+directory+File.separator+setting).exists()) {
				in = new FileInputStream(new File(System.getProperties().getProperty("user.home")+File.separator+setting));
				properties.load(in);
				in.close();
			} else {
				in = new FileInputStream(defaultFile);
				properties.load(in);
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return properties.getProperty(key);
	}
}
/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Settings {
	String file = System.getProperties().getProperty("user.home")+File.separator+".settings.cfg";
	String defaultFile = "resources"+File.separator+"settings.cfg";
	public void generateSettings(String workingHours, 
								String directory, 
								String startCity, 
								String maxDistance, 
								String minFee, 
								String drivingTime, 
								String carConsumption, 
								String fuel, 
								Double railBonus)
	{
		Properties settings = new Properties();
		settings.setProperty("workinghours", workingHours);
		settings.setProperty("directory", directory);
		settings.setProperty("pointOfDeparture", startCity);
		settings.setProperty("maxdistance", maxDistance);
		settings.setProperty("minFee", minFee);
		settings.setProperty("drivingTime", drivingTime);
		settings.setProperty("consumption", carConsumption);
		settings.setProperty("fuel", fuel);
		settings.setProperty("railCard", String.valueOf(railBonus));
		
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			settings.store(writer, null);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readSettings(String key) {
		Properties properties = new Properties();
		FileInputStream in;
		try {
			if (new File(System.getProperties().getProperty("user.home")+File.separator+".settings.cfg").exists()) {
				in = new FileInputStream(file);
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
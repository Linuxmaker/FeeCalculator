/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Settings {
	private String file = System.getProperties().getProperty("user.home")+File.separator+".settings.cfg";
	public void generateSettings(String workingHours, String directory, String startCity, String maxDistance){
		Properties settings = new Properties();
		settings.setProperty("workinghours", workingHours);
		settings.setProperty("directory", directory);
		settings.setProperty("pointOfDeparture", startCity);
		settings.setProperty("maxdistance", maxDistance);
		
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			settings.store(writer, null);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readSettings(String key) throws IOException {
		Properties properties = new Properties();
		FileInputStream in = new FileInputStream(file);
		properties.load(in);
		in.close();
		return properties.getProperty(key);
	}
}
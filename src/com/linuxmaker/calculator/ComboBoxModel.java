/**
 * DefaultComboBoxModel.java
 * Read from xml-file to the JComboBox and sort the read list
 */
package com.linuxmaker.calculator;

import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class ComboBoxModel extends DefaultComboBoxModel<String> {

	private static final long serialVersionUID = 1L;
	private XMLCreator cityList = new XMLCreator();
	
	
	public void reload() {
		removeAllElements();

		Vector<String> sortedCityList = new Vector<String>();
		for (int i = 0; i < cityList.CityList().size(); i++) {
			sortedCityList.add(cityList.CityList().get(i));
		}
		Collections.sort(sortedCityList);
		
		for (int i = 0; i < sortedCityList.size(); i++) {
			addElement("" + sortedCityList.get(i));
		}
	}

}

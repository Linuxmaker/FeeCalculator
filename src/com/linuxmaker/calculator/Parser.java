/**
 * 
 */
package com.linuxmaker.calculator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Parser {
	public double parseduration(String start, String destination) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {
		double duration = 0;
		try {
			if (connectInternet()) {
				URL myUrl = new URL("http://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + start + "&destinations=" + "\"" + destination + "\""  + "&mode=driving&language=de-DE&sensor=false");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(myUrl.openStream());
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expression = xpath.compile("/DistanceMatrixResponse/row/element/duration/value");
				duration = (Double.valueOf(expression.evaluate(document, XPathConstants.STRING).toString()))/3600;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return duration;
	}
	
	public double parsedistance(String start, String destination) throws SAXException, IOException, XPathExpressionException {
		double distance = 0;
		try {
			if (connectInternet()) {
				URL myUrl = new URL("http://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + start + "&destinations=" + "\"" + destination + "\"" + "&mode=driving&language=de-DE&sensor=false");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(myUrl.openStream());
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expression = xpath.compile("/DistanceMatrixResponse/row/element/distance/value");
				distance = (Double.valueOf(expression.evaluate(document, XPathConstants.STRING).toString()))/1000;
			}
		} catch (ParserConfigurationException e) {
			System.out.println("Der Server ist leider nicht erreichbar!");
		}
		return distance;
	}
	
	private Boolean connectInternet() {
			try {
				try {
					URL url = new URL("http://www.google.de");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.connect();
					if (con.getResponseCode() == 200) {
						return(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Kann keine Verbindung herstellen.\n Sorgen Sie für eine stabile Internetverbindung!", "Verbindungsfehler", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

}

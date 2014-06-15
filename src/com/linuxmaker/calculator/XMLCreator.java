/**
 * 
 */
package com.linuxmaker.calculator;

import static com.linuxmaker.calculator.Constants.ATTRIBUTE_NAME;
import static com.linuxmaker.calculator.Constants.ELEMENT_CITY;
import static com.linuxmaker.calculator.Constants.ELEMENT_DISTANCE;
import static com.linuxmaker.calculator.Constants.ELEMENT_DURATION;
import static com.linuxmaker.calculator.Constants.ELEMENT_FLIGHTCOST;
import static com.linuxmaker.calculator.Constants.ELEMENT_HOTEL;
import static com.linuxmaker.calculator.Constants.ELEMENT_TICKET;
import static com.linuxmaker.calculator.Constants.ELEMENT_DTICKET;
import static com.linuxmaker.calculator.Constants.ORIGIN_CITY;
import static com.linuxmaker.calculator.Constants.PATH;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPath;
import org.xml.sax.SAXException;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class XMLCreator {
	private String path = System.getProperties().getProperty("user.home")+File.separator+PATH+File.separator+ORIGIN_CITY+"City.xml";
	/*
	 * Abfrage, ob xml-Datei bereits existiert!!!
	 */
	private File xmlFile = new File(path);
	private Namespace ns = Namespace.getNamespace("http://www.linuxmaker.com/Preiskalkulator");
	String origin = "Stuttgart";
	String target;
	String ticket = "0.0";
	String hotel = "0.0";
	String flightcost = "0.0";

	public XMLCreator() {
		super();
	}
	
	public void writeXML(String target, String ticket, String dticket, String hotel, String flightcost) throws XPathExpressionException, SAXException, ParserConfigurationException, JDOMException {
		Parser distance = new Parser();
		Parser duration = new Parser();
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(xmlFile);

			Element xmlRoot = document.getRootElement();
			Element city = new Element(ELEMENT_CITY);
			city.setAttribute(ATTRIBUTE_NAME, target);
			city.addContent(new Element(ELEMENT_DISTANCE).setText(String.valueOf(distance.parsedistance(origin, target))));
			city.addContent(new Element(ELEMENT_DURATION).setText(String.valueOf(duration.parseduration(origin, target))));
			city.addContent(new Element(ELEMENT_TICKET).setText(ticket));
			city.addContent(new Element(ELEMENT_DTICKET).setText(dticket));
			city.addContent(new Element(ELEMENT_HOTEL).setText(hotel));
			city.addContent(new Element(ELEMENT_FLIGHTCOST).setText(flightcost));
			xmlRoot.addContent(city);

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, new FileWriter(path));

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<String> readXML(String target) {
		List<String> results  = new ArrayList<String>();
		Document doc;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(xmlFile);

			List<Element> nodes;
			nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
				
			for (Element element : nodes) {
				if (target.equals(element.getAttributeValue("name"))) {
					results.add(element.getAttributeValue("name"));
					results.add(element.getChildText("distance"));
					results.add(element.getChildText("duration"));
					results.add(element.getChildText("monthlyticket"));
					results.add(element.getChildText("roundtripdticket"));
					results.add(element.getChildText("hotelcosts"));
					results.add(element.getChildText("flightcost"));	
				} 
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return results;				
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void changeXML(String target, String value) {
		List<String> elements  = new ArrayList<String>();
		Document doc;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(xmlFile);
			List<Element> nodes;
			nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
			
			for (Element element : nodes) {
				if (target.equals(element.getAttributeValue("name"))) {
					elements.set(5, value);
				}
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(path));
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<String> CityList() {
		Document doc;
		List<String> CityList = new ArrayList<String>();
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(xmlFile);

			List<Element> nodes;
			nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
			for (Element element : nodes) {
				CityList.add(element.getAttribute("name").getValue());
			}
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return CityList;
	}
	
}

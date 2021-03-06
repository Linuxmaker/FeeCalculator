/**
 * 
 */
package com.linuxmaker.calculator;

import static com.linuxmaker.calculator.Constants.ATTRIBUTE_NAME;
import static com.linuxmaker.calculator.Constants.ELEMENT_CITY;
import static com.linuxmaker.calculator.Constants.ELEMENT_DISTANCE;
import static com.linuxmaker.calculator.Constants.ELEMENT_DURATION;
import static com.linuxmaker.calculator.Constants.ELEMENT_HOTEL;
import static com.linuxmaker.calculator.Constants.ELEMENT_TICKET;
import static com.linuxmaker.calculator.Constants.ELEMENT_DTICKET;
import static com.linuxmaker.calculator.Constants.ELEMENT_PUBLICTRANSPORT;
import static com.linuxmaker.calculator.Constants.DIRECTORY;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
@SuppressWarnings("deprecation")
public class XMLCreator {

	
	if (System.getProperty("os.name").indexOf("Windows") != -1) {
		directory = DIRECTORY;
	} else {
		directory = "."+DIRECTORY;
	}
	private String directory;
	private String path = System.getProperties().getProperty("user.home")+File.separator+directory+File.separator+new Settings().readSettings("pointOfDeparture")+"City.xml";
	/*
	 * Abfrage, ob xml-Datei bereits existiert!!!
	 */
	private File xmlFile = new File(path);
	private Namespace ns = Namespace.getNamespace("http://www.linuxmaker.com/Preiskalkulator");
	private File defaultXmlFile = new File("resources"+File.separator+"City.xml");
	String origin = new Settings().readSettings("pointOfDeparture");
	String cityName;
	String ticket = "0.0";
	String hotel = "0.0";
	String publictransport;

	public XMLCreator() {
		super();
	}
	
	public void writeXML(String cityName, String ticket, String dticket, String hotel, String publictransport) throws XPathExpressionException, SAXException, ParserConfigurationException, JDOMException {
		Parser distance = new Parser();
		Parser duration = new Parser();
		try {
			if (ticket.length() == 0) {
				ticket = "0.00";
			}
			if (dticket.length() == 0) {
				dticket = "0.00";
			}
			if (hotel.length() == 0) {
				hotel = "0.00";
			}
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(xmlFile);

			Element xmlRoot = document.getRootElement();
			Element city = new Element(ELEMENT_CITY);
			city.setAttribute(ATTRIBUTE_NAME, cityName);
			city.addContent(new Element(ELEMENT_DISTANCE).setText(String.valueOf(distance.parsedistance(origin, cityName))));
			city.addContent(new Element(ELEMENT_DURATION).setText(String.valueOf(duration.parseduration(origin, cityName))));
			city.addContent(new Element(ELEMENT_TICKET).setText(ticket));
			city.addContent(new Element(ELEMENT_DTICKET).setText(dticket));
			city.addContent(new Element(ELEMENT_HOTEL).setText(hotel));
			city.addContent(new Element(ELEMENT_PUBLICTRANSPORT).setText(publictransport));
			xmlRoot.addContent(city);

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, new FileWriter(path));

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<String> readXML(String cityName) {
		List<String> results  = new ArrayList<String>();
		Document doc;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(xmlFile);

			List<Element> nodes;
			nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
				
			for (Element element : nodes) {
				if (cityName.equals(element.getAttributeValue("name"))) {
					results.add(element.getAttributeValue("name"));
					results.add(element.getChildText(ELEMENT_DISTANCE));
					results.add(element.getChildText(ELEMENT_DURATION));
					results.add(element.getChildText(ELEMENT_TICKET));
					results.add(element.getChildText(ELEMENT_DTICKET));
					results.add(element.getChildText(ELEMENT_HOTEL));
					results.add(element.getChildText(ELEMENT_PUBLICTRANSPORT));
				} 
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return results;				
	}

	public void changeXML(String cityName, String varMonthlyTicket, String varRoundTripTicket, String varHotel, String varPublictransport) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(xmlFile);

			Element costCalculator = doc.getRootElement();
			
			Iterator<?> cityList = costCalculator.getChildren(ELEMENT_CITY).iterator();
			while (cityList.hasNext()) {
				Element city = (Element) cityList.next();
				if (cityName.equals(city.getAttribute("name").getValue())) {
					Element monthlyTicket = new Element(ELEMENT_TICKET);
					monthlyTicket.addContent(varMonthlyTicket);
					city.removeChild(ELEMENT_TICKET);
					city.addContent(monthlyTicket);
					Element roundTripTicket = new Element(ELEMENT_DTICKET);
					roundTripTicket.addContent(varRoundTripTicket);
					city.removeChild(ELEMENT_DTICKET);
					city.addContent(roundTripTicket);
					Element hotelCost = new Element(ELEMENT_HOTEL);
					hotelCost.addContent(varHotel);
					city.removeChild(ELEMENT_HOTEL);
					city.addContent(hotelCost);
					Element publicTransport = new Element(ELEMENT_PUBLICTRANSPORT);
					publicTransport.addContent(varPublictransport);
					city.removeChild(ELEMENT_PUBLICTRANSPORT);
					city.addContent(publicTransport);
				}
				
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(path));
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeElement(String cityName) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(xmlFile);
			Element costCalculator = doc.getRootElement();
			Iterator<Element> cities = costCalculator.getChildren("city").iterator();
			while (cities.hasNext()) {
				Element city = (Element) cities.next();
				if (cityName.equals(city.getAttribute("name").getValue())) {
					cities.remove();
				}	
			}			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(xmlFile));			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<String> CityList() {
		Document doc;
		List<String> CityList = new ArrayList<String>();
		try {
			if (new File(System.getProperties().getProperty("user.home")+File.separator+".settings.cfg").exists()) {
				SAXBuilder builder = new SAXBuilder();
				doc = (Document) builder.build(xmlFile);

				List<Element> nodes;
				nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
				for (Element element : nodes) {
					CityList.add(element.getAttribute("name").getValue());
				}
			} else {
				SAXBuilder builder = new SAXBuilder();
				doc = (Document) builder.build(defaultXmlFile);

				List<Element> nodes;
				nodes = (List<Element>) XPath.selectNodes(doc, "/costcalculator/city");
				for (Element element : nodes) {
					CityList.add(element.getAttribute("name").getValue());
				}
			}
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return CityList;
	}
	
}

/**
 * 
 */
package com.linuxmaker.calculator;

import static com.linuxmaker.calculator.Constants.ELEMENT_COSTCALCULATOR;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class XmlFileWriter {
	public void xmlWrite(String town) throws IOException, DOMException, XPathExpressionException, SAXException, TransformerException {
		String directory;
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			directory = DIRECTORY;
		} else {
			directory = "."+DIRECTORY;
		}
		String fileName = System.getProperties().getProperty("user.home")+ File.separator + directory + File.separator + town + "City.xml";
		try (OutputStream output = new FileOutputStream(new File(fileName))) {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(ELEMENT_COSTCALCULATOR);
			doc.appendChild(rootElement);
			
			Element city = doc.createElement(ELEMENT_CITY);
			rootElement.appendChild(city);
			
			Attr attr = doc.createAttribute(ATTRIBUTE_NAME);
			attr.setValue(town);
			city.setAttributeNode(attr);
			
			Element distance = doc.createElement(ELEMENT_DISTANCE);
			distance.appendChild(doc.createTextNode(String.valueOf((new Parser().parsedistance(town, town)))));
			city.appendChild(distance);
			
			Element duration = doc.createElement(ELEMENT_DURATION);
			duration.appendChild(doc.createTextNode(String.valueOf((new Parser().parseduration(town, town)))));
			city.appendChild(duration);
			
			Element monthlyticket = doc.createElement(ELEMENT_TICKET);
			monthlyticket.appendChild(doc.createTextNode("0.00"));
			city.appendChild(monthlyticket);
			
			Element roundtripticket = doc.createElement(ELEMENT_DTICKET);
			roundtripticket.appendChild(doc.createTextNode("0.00"));
			city.appendChild(roundtripticket);
			
			Element hotelcosts = doc.createElement(ELEMENT_HOTEL);
			hotelcosts.appendChild(doc.createTextNode("0.00"));
			city.appendChild(hotelcosts);
			
			Element publictransport = doc.createElement(ELEMENT_PUBLICTRANSPORT);
			publictransport.appendChild(doc.createTextNode("false"));
			city.appendChild(publictransport);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult target = new StreamResult(output);
				
			transformer.transform(source, target);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

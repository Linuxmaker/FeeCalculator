/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import com.linuxmaker.calculator.Compare;
import com.linuxmaker.calculator.Parser;
import com.linuxmaker.calculator.Settings;
import com.linuxmaker.calculator.XMLCreator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JCheckBox;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class AddFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3383005760615272827L;
	private JPanel contentPane;
	private JTextField cityTextField;
	private JTextField railTicketTwoTextField;
	private JTextField railTicketMonthTextField;
	private JTextField hotelCostsTextField;
	private JLabel eur1Label;
	private JLabel eur2Label;
	private JLabel eur3Label;
	private JLabel cityLabel;
	private JLabel resultLabel;
	private JLabel railTicketMonthLabel;
	private JLabel railTicketTwoLabel;
	private JLabel hotelCostsLabel;
	private JCheckBox publicTransportCheckBox;
	private JButton addButton;
	private JButton clearButton;
	private JButton closeButton;
	private JButton searchButton;
	private String originCity = new Settings().readSettings("pointOfDeparture");
	private String drivingTime = new Settings().readSettings("drivingTime");
	private String maxDistance = new Settings().readSettings("maxdistance");
	private String distancePublicTransport = new Settings().readSettings("publicTransport");
	private Compare cityCompare = new Compare();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddFrame() {
		setBackground(new Color(250, 250, 210));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
		setResizable(false);
		setTitle("Reisedaten hinzufügen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 388, 261);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		publicTransportCheckBox.setEnabled(false);
		initializeGuiObjects();
		contentPane.setLayout(createLayout());
	}
	
	/**
	 * Initializes the GUI elements
	 */
	private void initializeGuiObjects(){
		cityLabel = new JLabel("Projekt-Stadt");
		cityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		cityTextField = new JTextField();
		cityTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		cityTextField.setColumns(10);
		
		searchButton = new JButton("Suchen");
		searchButton.setFont(new Font("Dialog", Font.BOLD, 12));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cityCompare.compare(cityTextField.getText())) {
					resultLabel.setText("Die Stadt existiert bereits!");
					resultLabel.setFont(new Font("Dialog", Font.BOLD, 9));
					resultLabel.setVisible(true);
				} else {
					Parser search = new Parser();
					try {
						if (search.parsedistance(originCity, cityTextField.getText()) <= Double.parseDouble(maxDistance)) {
							railTicketMonthTextField.setEnabled(true);
							railTicketTwoTextField.setEnabled(true);
							if (search.parsedistance(originCity, cityTextField.getText()) <= Double.parseDouble(distancePublicTransport)) {
								publicTransportCheckBox.setEnabled(true);
							}							
						} else {
							railTicketMonthTextField.setEnabled(false);
							railTicketTwoTextField.setEnabled(true);
							publicTransportCheckBox.setEnabled(false);
						}
						if (search.parseduration(originCity, cityTextField.getText()) <= Double.parseDouble(drivingTime)) {
							hotelCostsTextField.setEnabled(false);
						} else {
							hotelCostsTextField.setEnabled(true);
							publicTransportCheckBox.setEnabled(false);
						}
					} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		railTicketTwoLabel = new JLabel("Bahnticket");
		railTicketTwoLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railTicketTwoTextField = new JTextField();
		railTicketTwoTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		railTicketTwoTextField.setEnabled(false);
		railTicketTwoTextField.setToolTipText("Betrag für die Hin- und Rückfahrt der DB AG");
		railTicketTwoTextField.setColumns(10);
		
		railTicketMonthLabel = new JLabel("Monatsfahrkarte");
		railTicketMonthLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railTicketMonthTextField = new JTextField();
		railTicketMonthTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		railTicketMonthTextField.setEnabled(false);
		railTicketMonthTextField.setColumns(10);
		
		hotelCostsLabel = new JLabel("Hotelkosten");
		hotelCostsLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		hotelCostsTextField = new JTextField();
		hotelCostsTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		hotelCostsTextField.setEnabled(false);
		hotelCostsTextField.setColumns(10);
		
		eur1Label = new JLabel("EUR");		
		eur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		eur2Label = new JLabel("EUR");		
		eur2Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		eur3Label = new JLabel("EUR");		
		eur3Label.setFont(new Font("Dialog", Font.PLAIN, 12));

		addButton = new JButton("Hinzufügen");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator writer = new XMLCreator();
				try {
					writer.writeXML(
							cityTextField.getText(), 
							railTicketMonthTextField.getText().replace(',', '.'), 
							railTicketTwoTextField.getText().replace(',', '.'), 
							hotelCostsTextField.getText().replace(',', '.'), 
							String.valueOf(publicTransportCheckBox.isSelected()));
					resultLabel.setVisible(true);
				} catch (XPathExpressionException | SAXException
						| ParserConfigurationException | JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reset();
			}
		});
		addButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		clearButton = new JButton("Löschen");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				resultLabel.setVisible(false);
			}
		});
		clearButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		closeButton = new JButton("Beenden");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Container frame = closeButton.getParent();
				do
					frame = frame.getParent();
				while (!(frame instanceof JFrame));
				((JFrame) frame).dispose();
			}
		});
		closeButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		resultLabel = new JLabel("Erfolgreich angelegt!");
		resultLabel.setVisible(false);
		resultLabel.setForeground(new Color(0, 0, 205));
		resultLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		publicTransportCheckBox = new JCheckBox("Liegt im ÖPNV");
		publicTransportCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
	}
	
	/**
	 * Creates the GUI layout
	 */
	private LayoutManager createLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(railTicketTwoLabel)
								.addComponent(railTicketMonthLabel)
								.addComponent(hotelCostsLabel)
								.addComponent(cityLabel)
								.addComponent(resultLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(hotelCostsTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(railTicketMonthTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(railTicketTwoTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(eur3Label)
										.addComponent(eur2Label)
										.addComponent(eur1Label)))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(searchButton)
									.addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
								.addComponent(publicTransportCheckBox)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(addButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clearButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(closeButton)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cityLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchButton)
						.addComponent(resultLabel))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(railTicketTwoTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eur1Label)
						.addComponent(railTicketTwoLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(railTicketMonthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(eur2Label))
						.addComponent(railTicketMonthLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hotelCostsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eur3Label)
						.addComponent(hotelCostsLabel))
					.addGap(5)
					.addComponent(publicTransportCheckBox)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(addButton)
						.addComponent(clearButton)
						.addComponent(closeButton))
					.addContainerGap())
		);
		return gl_contentPane;
	}

	protected void reset() {
		cityTextField.setText("");
		railTicketTwoTextField.setEnabled(false);
		railTicketMonthTextField.setEnabled(false);
		hotelCostsTextField.setEnabled(false);
	}
}

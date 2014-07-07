/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import javax.swing.InputVerifier;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.linuxmaker.calculator.ComboBoxModel;
import com.linuxmaker.calculator.Fee;
import com.linuxmaker.calculator.Settings;
import com.linuxmaker.calculator.XMLCreator;
import com.linuxmaker.calculator.XmlFileWriter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class AllinFeeFrame extends JFrame implements ListDataListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8189297737183218936L;
	private static final InputVerifier OnlyDigitsVerifier = null;
	private String originCity = new Settings().readSettings("pointOfDeparture");
	private String path = new Settings().readSettings("directory");
	private Double workingHours = Double.parseDouble(new Settings().readSettings("workinghours"));
	private String minFee = new Settings().readSettings("minFee");
	private String drivingTime = new Settings().readSettings("drivingTime");
	private String maxDistance = new Settings().readSettings("maxdistance");
	private JPanel contentPane;
	private JTextField originCityTextField;
	private JTextField feeTextField;
	private JComboBox<String> targetCityComboBox;
	private ComboBoxModel myComboBoxModel;
	private JLabel cur2Label;
	private JLabel cur3Label;
	private JLabel cur4Label;
	private JLabel cur5Label;
	private JLabel dayFlightHonorarLabel;
	private JLabel hourFlightHonorarLabel;
	private JLabel dayPriceLabel;
	private JLabel hourPriceLabel;
	private JLabel hourHonorarLabel;
	private JLabel dayHonorarLabel;
	private JCheckBox scontoCheckBox;
	private JComboBox<String> scontoComboBox;
	private Double sconto;
	private Double railBonus;
	private JComboBox<String> overnightStayComboBox;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton railBonus25RadioButton;
	private JRadioButton railBonus50RadioButton;
	private JRadioButton railBonus100RadioButton;
	private JTextField hoursPerDayTextField;
	private final JComboBox<String> daysProjectComboBox;
	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllinFeeFrame frame = new AllinFeeFrame();
					frame.setVisible(true);
					if (originCity.equals("Musterstadt")) {
						JOptionPane.showMessageDialog(frame, "Bitte machen Sie zuerst Ihre Einstellungen.", "Fehlende Einstellungen", JOptionPane.WARNING_MESSAGE);
						new SettingsFrame().main(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AllinFeeFrame() {
		setBackground(new Color(250, 235, 215));
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 12));
		setForeground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AllinFeeFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
		setTitle("Freelancer - AllIn-Honorar Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 438);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.setBackground(new Color(240, 230, 140));
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Datei");
		menuFile.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.add(menuFile);
		
		JMenuItem menuItemExit = new JMenuItem("Beenden");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItemExit.setFont(new Font("Dialog", Font.PLAIN, 11));
		menuFile.add(menuItemExit);
		
		JMenu menuEdit = new JMenu("Bearbeiten");
		menuEdit.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.add(menuEdit);
		
		JMenuItem menuItemAdd = new JMenuItem("Hinzufügen");
		menuItemAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame.main(null);
			}
		});
		menuEdit.add(menuItemAdd);
		
		JMenuItem menuItemChange = new JMenuItem("Ändern");
		menuItemChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangeFrame.main(null);
			}
		});
		menuItemChange.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuEdit.add(menuItemChange);
		
		JMenuItem menuItemDelete = new JMenuItem("Löschen");
		menuItemDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Löschen");
			}
		});
		menuItemDelete.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuEdit.add(menuItemDelete);
		
		JMenu menuExtra = new JMenu("Extras");
		menuExtra.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.add(menuExtra);
		
		JMenuItem menuItemNetFer = new JMenuItem("Nettohonorar");
		menuItemNetFer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NetFeeFrame.main(null);
			}
		});
		menuItemNetFer.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuExtra.add(menuItemNetFer);
		
		JMenu menuSettings = new JMenu("Einstellungen");
		menuSettings.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.add(menuSettings);
		
		JMenuItem menuItemSettings = new JMenuItem("Einstellungen");
		menuItemSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingsFrame settings = new SettingsFrame();
				settings.main(null);
			}
		});
		menuItemSettings.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuSettings.add(menuItemSettings);
		
		JMenuItem menuItemCity = new JMenuItem("Standardstadt");
		menuItemCity.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuSettings.add(menuItemCity);
		
		JMenu menuInfo = new JMenu("Info");
		menuInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuBar.add(menuInfo);
		
		JMenuItem menuItemHelp = new JMenuItem("Hilfe");
		menuItemHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuInfo.add(menuItemHelp);
		
		JMenuItem menuItemAbout = new JMenuItem("Über...");
		menuItemAbout.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuInfo.add(menuItemAbout);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		JLabel originCityLabel = new JLabel("Ausgangsstadt");
		originCityLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		
		originCityTextField = new JTextField();
		originCityTextField.setText(originCity);
		originCityTextField.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		originCityTextField.setColumns(10);
		
		JLabel targetCityLabel = new JLabel("Projekteinsatz");
		targetCityLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));

		targetCityComboBox = new JComboBox<String>();
		targetCityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				myComboBoxModel.reload();
				targetCityComboBox.setModel(myComboBoxModel);
			}
		});
		targetCityComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator xmlelement = new XMLCreator();
				String city = (String) targetCityComboBox.getSelectedItem();
				if (Double.parseDouble(xmlelement.readXML(city).get(1)) < Double.parseDouble(maxDistance) && Double.parseDouble(xmlelement.readXML(city).get(2)) <= Double.parseDouble(drivingTime)) {
					daysProjectComboBox.setEnabled(true);
					overnightStayComboBox.setEnabled(false);
				} else if (Double.parseDouble(xmlelement.readXML(city).get(1)) < Double.parseDouble(maxDistance) && Double.parseDouble(xmlelement.readXML(city).get(2)) > Double.parseDouble(drivingTime)) {
					daysProjectComboBox.setEnabled(false);
					overnightStayComboBox.setEnabled(true);
				}else {
					daysProjectComboBox.setEnabled(false);
					overnightStayComboBox.setEnabled(true);
					overnightStayComboBox.setSelectedIndex(4);
				}
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				hourHonorarLabel.setVisible(false);
				dayHonorarLabel.setVisible(false);
				dayFlightHonorarLabel.setVisible(false);
				hourFlightHonorarLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
				cur4Label.setVisible(false);
				cur5Label.setVisible(false);
			}
		});
		
		/*
		 * ComboBoxModel erzeugen
		 */
		myComboBoxModel = new ComboBoxModel();
	//	myComboBoxModel.addListDataListener(this);
		/*
		 * ComboBoxModel setzen
		 */
		myComboBoxModel.reload();
		targetCityComboBox.setModel(myComboBoxModel);
		
		
		
		targetCityComboBox.setEditable(false);
		targetCityComboBox.setMaximumRowCount(10);
		targetCityComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		JLabel feeLabel = new JLabel("Netto-Honorar");
		feeLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		
		feeTextField = new JTextField();
		feeTextField.setText("0.00");
		feeTextField.setToolTipText("Tageshonorar oder Stundensatz");
		feeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		feeTextField.setColumns(10);
		
		JLabel daysProjectLabel = new JLabel("Projekttage Monat");
		daysProjectLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		
		daysProjectComboBox = new JComboBox<String>();
		daysProjectComboBox.setToolTipText("Projekttage im Monat");
		daysProjectComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		daysProjectComboBox.setSelectedIndex(21);
		daysProjectComboBox.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		daysProjectComboBox.setEnabled(true);
		
		dayPriceLabel = new JLabel("Tageshonorar");
		dayPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dayPriceLabel.setVisible(false);
		
		hourPriceLabel = new JLabel("Stundenhonorar");
		hourPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		hourPriceLabel.setVisible(false);
		
		dayHonorarLabel = new JLabel("Honorar");
		dayHonorarLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dayHonorarLabel.setHorizontalAlignment(JLabel.RIGHT);
		dayHonorarLabel.setVisible(false);
		
		hourHonorarLabel = new JLabel("Honorar");
		hourHonorarLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		hourHonorarLabel.setHorizontalAlignment(JLabel.RIGHT);
		hourHonorarLabel.setVisible(false);
		
		JButton calculateButton = new JButton("AllIn-Honorar");
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator xmlelement = new XMLCreator();
				Fee price = new Fee();
				DecimalFormat f = new DecimalFormat("#0.00");
				String city = (String) targetCityComboBox.getSelectedItem();
				String fileName = System.getProperties().getProperty("user.home")+ File.separator + path + File.separator + originCityTextField.getText() + "City.xml";
				File xmlFile = new File(fileName);
				Double fee = Double.parseDouble(feeTextField.getText());
				Double travelDistance = Double.parseDouble(xmlelement.readXML(city).get(1));
				Double travelTime = Double.parseDouble(xmlelement.readXML(city).get(2));
				Double monthlyTicket = Double.parseDouble(xmlelement.readXML(city).get(3));
				Double roundTripTicket = Double.parseDouble(xmlelement.readXML(city).get(4));
				Double hotelCosts = Double.parseDouble(xmlelement.readXML(city).get(5));
				Double flightTicket = Double.parseDouble(xmlelement.readXML(city).get(6));
				int overnightStay = Integer.valueOf((String) overnightStayComboBox.getSelectedItem());
				Double hoursPerDay = Double.parseDouble(hoursPerDayTextField.getText());
				Double factorWorkingHours = price.factorWorkingHours(workingHours, hoursPerDay);
				int projektdays = Integer.valueOf((String) daysProjectComboBox.getSelectedItem());
				Double sconto = Double.parseDouble((String) scontoComboBox.getSelectedItem());
				/*
				 * Überprüfung, ob die XML-Datei der ausgewählten Stadt bereits existiert
				 */
				if (xmlFile.exists()) {
					/*
					 * Überprüfung, ob Skonto ausgewählt wurde und Zuweisung des betreffenden Wertes
					 */
					if (scontoCheckBox.isSelected()) {
						sconto = Double.parseDouble((String) scontoComboBox.getSelectedItem())/100 + 1;
					} else {
						sconto = 1.0;
					}
					
					/*
					 * Überprüfung, ob die Bahncard-Option ausgewählt ist.
					 */
					if (railBonus25RadioButton.isSelected()) {
						railBonus = 0.75;
					} else if (railBonus50RadioButton.isSelected()) {
						railBonus = 0.5;
					} else if (railBonus100RadioButton.isSelected()) {
						railBonus = 0.0;
					} else {
						railBonus = 1.0;
					}
					
					/*
					 * Preisbildung unter der Prämisse, dass ein Monatsticket (< 420 km) und mit Pendeln (<= 2.5 h) oder mit Hotelbuchung möglich ist.
					 * Alle anderen Strecken benötigen ein Normalticket und verursachen Hotelkosten.
					 * Zusätzlich wird geprüft, ob das Honorar als Stundensatz (bis 399,00) oder als Tagessatz eingeben wurde
					 */
					if (travelDistance <= Double.parseDouble(maxDistance)) {
						// Monatsticket kommt zum Einsatz
						if (travelTime <= Double.parseDouble(drivingTime)) {
							// Verwendung des Monatstickets und täglichem Pendeln
							// Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz erfolgt
							if (fee < Double.parseDouble(minFee)) {
								// Stundensatz
								// Test, ab wann das Normalticket(4) günstiger ist, als das Monatsticket(3): Normalticket > Monatsticket/Projektage
								if (roundTripTicket > monthlyTicket/projektdays) {
									// Verwendung des Monatstickets(3)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, monthlyTicket, sconto, projektdays))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, monthlyTicket, sconto, projektdays)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									// Verwendung des Normaltickets(4)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							} else {
								// Tagessatz
								// Test, ab wann das Normalticket(4) günstiger ist, als das Monatsticket(3): Normalticket > Monatsticket/Projektage
								if (roundTripTicket > monthlyTicket/projektdays) {
									// Verwendung des Monatstickets(3)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, monthlyTicket, sconto, projektdays))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, monthlyTicket, sconto, projektdays)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									// Verwendung des Normaltickets(4)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							}							
						} else {
							// Verwendung des Monatstickets mit Übernachtung und wöchentlichem Pendeln
							// Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz erfolgt
							if (fee < Double.parseDouble(minFee)) {
								// Stundensatz
								// Test, ab wann das Normalticket(4) günstiger ist, als das Monatsticket(3): Normalticket > Monatsticket/Projektage
								if (roundTripTicket > monthlyTicket/projektdays) {
									// Verwendung des Monatstickets(3)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, monthlyTicket, sconto, hotelCosts, projektdays))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, monthlyTicket, sconto, hotelCosts, projektdays)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									// Verwendung des Normaltickets(4)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							} else {
								// Tagessatz
								// Test, ab wann das Normalticket(4) günstiger ist, als das Monatsticket(3): Normalticket > Monatsticket/Projektage
								if (roundTripTicket > monthlyTicket/projektdays) {
									// Verwendung des Monatstickets(3)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, monthlyTicket, sconto, hotelCosts, projektdays))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, monthlyTicket, sconto, hotelCosts, projektdays)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									// Verwendung des Normaltickets(4)
									dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
									hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
									dayPriceLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									dayHonorarLabel.setVisible(true);
									hourHonorarLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							}
						}
					} else {
						// Normalticket kommt zum Einsatz, da die Strecke für Monatsticket zu gross ist
						// Alternative Einbeziehung des Flugtickets, wenn Bahnfahrt mehr als 5 Stunden beträgt
						if (travelTime <= 5.0) {
							// Normalticket(4) mit Übernachtung(5)
							// Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz erfolgt
							if (fee < Double.parseDouble(minFee)) {
								// Stundensatz
								dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
								hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
								dayPriceLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								dayHonorarLabel.setVisible(true);
								hourHonorarLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
							} else {
								// Tagessatz
								dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
								hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
								dayPriceLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								dayHonorarLabel.setVisible(true);
								hourHonorarLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
							}
						} else {
							// Normalticket(4) mit Übernachtung(5) und Flugticket(6)
							// Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz erfolgt
							if (fee < Double.parseDouble(minFee)) {
								// Stundensatz
								dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
								hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
								dayFlightHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, flightTicket, sconto, 1.0, hotelCosts, overnightStay))));
								hourFlightHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*hoursPerDay, flightTicket, sconto, 1.0, hotelCosts, overnightStay)/hoursPerDay)));
								dayPriceLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								dayHonorarLabel.setVisible(true);
								hourHonorarLabel.setVisible(true);
								dayFlightHonorarLabel.setVisible(true);
								hourFlightHonorarLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
								cur4Label.setVisible(true);
								cur5Label.setVisible(true);
							} else {
								// Tagessatz
								dayHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay))));
								hourHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay)/hoursPerDay)));
								dayFlightHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, flightTicket, sconto, 1.0, hotelCosts, overnightStay))));
								hourFlightHonorarLabel.setText(String.valueOf(f.format(price.calculateHonorar(fee*factorWorkingHours, flightTicket, sconto, 1.0, hotelCosts, overnightStay)/hoursPerDay)));
								dayPriceLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								dayHonorarLabel.setVisible(true);
								hourHonorarLabel.setVisible(true);
								dayFlightHonorarLabel.setVisible(true);
								hourFlightHonorarLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
								cur4Label.setVisible(true);
								cur5Label.setVisible(true);
							}
						}	
					}
				} else {
					XmlFileWriter newCity = new XmlFileWriter();
					try {
						newCity.xmlWrite(originCityTextField.getText());
						JOptionPane.showMessageDialog(null, "Für die Stadt " + originCityTextField.getText() + " existieren noch keine Einträge!");
					} catch (DOMException
							| XPathExpressionException | IOException
							| SAXException | TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		calculateButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton addButton = new JButton("Hinzufügen");
		addButton.setFont(new Font("Dialog", Font.BOLD, 12));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame.main(null);
			}
		});
		
		JButton changeButton = new JButton("Ändern");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeFrame.main(null);
			}
		});
		changeButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton endButton = new JButton("Beenden");
		endButton.setFont(new Font("Dialog", Font.BOLD, 12));
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		cur2Label = new JLabel("EUR");
		cur2Label.setVisible(false);
		cur2Label.setFont(new Font("Dialog", Font.BOLD, 12));
		
		cur3Label = new JLabel("EUR");
		cur3Label.setVisible(false);
		cur3Label.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel scontoLabel = new JLabel("% Skonto");
		scontoLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoComboBox = new JComboBox();
		scontoComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}));
		scontoComboBox.setSelectedIndex(2);
		scontoComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoCheckBox = new JCheckBox("");
		scontoCheckBox.setSelected(false);
		scontoCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));

		JLabel overnightStayLabel = new JLabel("Übernachtungen");
		overnightStayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		overnightStayComboBox = new JComboBox();
		overnightStayComboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		overnightStayComboBox.setSelectedIndex(0);
		overnightStayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus25RadioButton = new JRadioButton("Bahncard25");
		buttonGroup.add(railBonus25RadioButton);
		railBonus25RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus50RadioButton = new JRadioButton("Bahncard50");
		buttonGroup.add(railBonus50RadioButton);
		railBonus50RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus100RadioButton = new JRadioButton("Bahncard100");
		buttonGroup.add(railBonus100RadioButton);
		railBonus100RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		dayFlightHonorarLabel = new JLabel("Flight");
		dayFlightHonorarLabel.setForeground(new Color(0, 128, 0));
		dayFlightHonorarLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dayFlightHonorarLabel.setHorizontalAlignment(JLabel.RIGHT);
		dayFlightHonorarLabel.setVisible(false);
		
		hourFlightHonorarLabel = new JLabel("Flight");
		hourFlightHonorarLabel.setForeground(new Color(0, 128, 0));
		hourFlightHonorarLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		hourFlightHonorarLabel.setHorizontalAlignment(JLabel.RIGHT);
		hourFlightHonorarLabel.setVisible(false);
		
		cur4Label = new JLabel("EUR, Flug");
		cur4Label.setForeground(new Color(0, 128, 0));
		cur4Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur4Label.setVisible(false);
		
		cur5Label = new JLabel("EUR, Flug");
		cur5Label.setForeground(new Color(0, 128, 0));
		cur5Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur5Label.setVisible(false);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				feeTextField.setText("0.00");
				daysProjectComboBox.setSelectedIndex(19);
				overnightStayComboBox.setSelectedIndex(4);
				scontoComboBox.setSelectedIndex(2);
				scontoCheckBox.setSelected(true);
				buttonGroup.clearSelection();
				hoursPerDayTextField.setText(String.valueOf(workingHours));
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				hourHonorarLabel.setVisible(false);
				dayHonorarLabel.setVisible(false);
				dayFlightHonorarLabel.setVisible(false);
				hourFlightHonorarLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
				cur4Label.setVisible(false);
				cur5Label.setVisible(false);
			}
		});
		resetButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel hoursPerDayLabel = new JLabel("Stunden pro Tag");
		hoursPerDayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		hoursPerDayTextField = new JTextField();
		hoursPerDayTextField.setText(String.valueOf(workingHours));
		hoursPerDayTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		hoursPerDayTextField.setColumns(10);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(calculateButton)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(addButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(changeButton)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(resetButton)
							.addContainerGap(161, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addComponent(railBonus50RadioButton)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(114)
										.addComponent(scontoCheckBox)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scontoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scontoLabel)
										.addGap(11)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(dayPriceLabel)
										.addComponent(hourPriceLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(hourHonorarLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGap(18)
											.addComponent(cur3Label))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(dayHonorarLabel)
											.addGap(18)
											.addComponent(cur2Label)))
									.addGap(35)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(hourFlightHonorarLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(cur5Label))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(dayFlightHonorarLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(cur4Label))
										.addComponent(endButton, Alignment.TRAILING))
									.addGap(55)))
							.addGap(49))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(49)
					.addComponent(railBonus25RadioButton)
					.addContainerGap(350, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(feeLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
								.addComponent(daysProjectLabel, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(targetCityLabel)
							.addGap(34)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(originCityTextField, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(feeTextField, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cur1Label))
									.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(overnightStayLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(railBonus100RadioButton)
								.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
							.addGap(71))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(originCityLabel, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
					.addGap(319))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(hoursPerDayLabel)
					.addGap(18)
					.addComponent(hoursPerDayTextField, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
					.addGap(307))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(originCityLabel)
						.addComponent(originCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(targetCityLabel)
						.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(feeLabel)
							.addComponent(scontoLabel)
							.addComponent(scontoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(feeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cur1Label))
						.addComponent(scontoCheckBox))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(daysProjectLabel)
						.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(overnightStayLabel)
						.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hoursPerDayLabel)
						.addComponent(hoursPerDayTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(railBonus25RadioButton)
						.addComponent(railBonus50RadioButton)
						.addComponent(railBonus100RadioButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(dayPriceLabel)
						.addComponent(dayFlightHonorarLabel)
						.addComponent(cur4Label)
						.addComponent(cur2Label)
						.addComponent(dayHonorarLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hourPriceLabel)
						.addComponent(cur3Label)
						.addComponent(hourFlightHonorarLabel)
						.addComponent(cur5Label)
						.addComponent(hourHonorarLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(calculateButton)
						.addComponent(endButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(addButton)
						.addComponent(changeButton)
						.addComponent(resetButton))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*private void registerValidators() {
		this.feeTextField.setInputVerifier(this.OnlyDigitsVerifier);
	}*/

}
/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import static com.linuxmaker.calculator.Constants.ORIGIN_CITY;
import static com.linuxmaker.calculator.Constants.PATH;
import static com.linuxmaker.calculator.Constants.WORKINGDAY;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import com.linuxmaker.calculator.Fee;
import com.linuxmaker.calculator.XMLCreator;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class NetFeeFrame extends JFrame implements ListDataListener {

	private JPanel contentPane;
	private JTextField originCityTextField;
	private JComboBox targetCityComboBox;
	private DefaultComboBoxModel comboBoxModel;
	private JLabel allInFeeLabel;
	private JTextField allInFeeTextField;
	private JLabel cur1Label;
	private JLabel cur2Label;
	private JLabel cur3Label;
	private JLabel cur4Label;
	private JLabel cur5Label;
	private JLabel scontoLabel;
	private JLabel hourFlightHonorarLabel;
	private JLabel dayFlightHonorarLabel;
	private JLabel daysProjectLabel;
	private JComboBox daysProjectComboBox;
	private JLabel overnightStayLabel;
	private JComboBox overnightStayComboBox;
	private JLabel dayPriceLabel;
	private JLabel hourPriceLabel;
	private JLabel dayNetLabel;
	private JLabel hourNetLabel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Double sconto;
	private Double railBonus;
	private JRadioButton railBonus25RadioButton;
	private JRadioButton railBonus50RadioButton;
	private JRadioButton railBonus100RadioButton;
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
					NetFeeFrame frame = new NetFeeFrame();
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
	public NetFeeFrame() {
		setTitle("Freelancer - Netto-Honorar");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(NetFeeFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 324);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel originCityLabel = new JLabel("Ausgangsstadt");
		originCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		originCityTextField = new JTextField();
		originCityTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		originCityTextField.setColumns(10);
		originCityTextField.setText(ORIGIN_CITY);
		
		JLabel targetCityLabel = new JLabel("Projektstadt");
		targetCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		targetCityComboBox = new JComboBox<String>();
		/*
		 * Städte in Vector einlesen
		 */
		Vector<String> cities = new Vector<>();
		XMLCreator cityList = new XMLCreator();
		for (int i = 0; i < cityList.CityList().size(); i++) {
			cities.add(cityList.CityList().get(i));
		}
		/*
		 * ComboBoxModel erzeugen
		 */
		comboBoxModel = new DefaultComboBoxModel<String>(cities);
		comboBoxModel.addListDataListener(this);
		/*
		 * ComboBoxModel setzen
		 */
		targetCityComboBox.setModel(comboBoxModel);
		
		targetCityComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		allInFeeLabel = new JLabel("AllIn-Honorar");
		allInFeeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		allInFeeTextField = new JTextField();
		allInFeeTextField.setToolTipText("AllIn-Honorar als Tagessatz oder Stundensatz");
		allInFeeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		allInFeeTextField.setColumns(10);
		
		cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		final JCheckBox scontoCheckBox = new JCheckBox("");
		scontoCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		final JComboBox scontoComboBox = new JComboBox();
		scontoComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}));
		scontoComboBox.setSelectedIndex(2);
		scontoCheckBox.setSelected(false);
		scontoComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoLabel = new JLabel("% Skonto");
		scontoLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		daysProjectLabel = new JLabel("Projekttage Monat");
		daysProjectLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		daysProjectComboBox = new JComboBox();
		daysProjectComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		daysProjectComboBox.setSelectedIndex(19);
		daysProjectComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		overnightStayLabel = new JLabel("Übernachtungen");
		overnightStayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		overnightStayComboBox = new JComboBox();
		overnightStayComboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		overnightStayComboBox.setSelectedIndex(4);
		overnightStayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		dayPriceLabel = new JLabel("Netto-Tagessatz");
		dayPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		hourPriceLabel = new JLabel("Netto-Stundensatz");
		hourPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		dayNetLabel = new JLabel("Day");
		dayNetLabel.setVisible(false);
		dayNetLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		cur2Label = new JLabel("EUR");
		cur2Label.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton calculateButton = new JButton("Netto-Honorar");
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator xmlelement = new XMLCreator();
				Fee price = new Fee();
				DecimalFormat f = new DecimalFormat("#0.00");
				String city = (String) targetCityComboBox.getSelectedItem();
				String fileName = System.getProperties().getProperty("user.home") + File.separator + PATH + File.separator + originCityTextField.getText() + "City.xml";
				File xmlFile = new File(fileName);
				Double fee = Double.parseDouble(allInFeeTextField.getText());
				Double travelDistance = Double.parseDouble(xmlelement.readXML(city).get(1));
				Double travelTime = Double.parseDouble(xmlelement.readXML(city).get(2));
				Double monthlyTicket = Double.parseDouble(xmlelement.readXML(city).get(3));
				Double roundTripTicket = Double.parseDouble(xmlelement.readXML(city).get(4));
				Double hotelCosts = Double.parseDouble(xmlelement.readXML(city).get(5));
				Double flightTicket = Double.parseDouble(xmlelement.readXML(city).get(6));
				int overnightStay = Integer.valueOf((String) overnightStayComboBox.getSelectedItem());
				Double hoursPerDay = WORKINGDAY;
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
					if (travelDistance <= 420.0) { // Monatsticket
						if (travelTime <= 2.5) {
							// Verwendung des Monatstickets und täglichem Pendeln
							if (fee < 400.0) { // Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz
								// Stundensatz
								// Test, ab wann das Normalticket(4) günstiger ist als das Monatsticket(3)
								if (roundTripTicket > monthlyTicket/projektdays) {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, monthlyTicket, projektdays, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, monthlyTicket, projektdays, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, railBonus, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, railBonus, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							} else {
								// Tagessatz
								// Test, ab wann das Normalticket(4) günstiger ist als das Monatsticket(3)
								if (roundTripTicket > monthlyTicket/projektdays) {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, monthlyTicket, projektdays, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, monthlyTicket, projektdays, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, railBonus, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, railBonus, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							}							
						} else {
							// Verwendung des Monatstickets mit Übernachtung und wöchentlichem Pendeln
							if (fee < 400.0) { // Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz
								// Stundensatz
								// Test, ab wann das Normalticket(4) günstiger ist als das Monatsticket(3)
								if (roundTripTicket > monthlyTicket/projektdays) {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, monthlyTicket, hotelCosts, projektdays, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, monthlyTicket, hotelCosts, projektdays, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							} else {
								// Tagessatz
								// Test, ab wann das Normalticket(4) günstiger ist als das Monatsticket(3)
								if (roundTripTicket > monthlyTicket/projektdays) {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, monthlyTicket, hotelCosts, projektdays, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, monthlyTicket, hotelCosts, projektdays, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								} else {
									dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
									hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
									dayNetLabel.setVisible(true);
									hourPriceLabel.setVisible(true);
									cur2Label.setVisible(true);
									cur3Label.setVisible(true);
								}
							}
						}
					} else { // Hier wird zwischen Normalticket und Flugticket unterschieden, da keine Bahnzeitkarte mehr möglich ist
						if (travelTime <= 5.0) {
							// Normalticket mit Übernachtung, da die Strecke unter dem Zeitlimit liegt
							if (fee < 400.0) { // Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz
								// Stundensatz
								dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
								hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
								dayNetLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
							} else {
								// Tagessatz
								dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
								hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
								dayNetLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
							}
						} else {
							// Hier wird alternativ ein Honorar mit Flugticket und Übernachtung ermittelt, da die Strecke sehr lang ist
							if (fee < 400.0) { // Überprüfung der Eingabe, ob als Stundensatz oder als Tagessatz
								// Stundensatz
								dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
								hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
								dayFlightHonorarLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, 1.0, overnightStay, sconto))));
								hourFlightHonorarLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee*hoursPerDay, roundTripTicket, hotelCosts, 1.0, overnightStay, sconto)/hoursPerDay)));
								dayNetLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
								dayFlightHonorarLabel.setVisible(true);
								hourFlightHonorarLabel.setVisible(true);
								cur4Label.setVisible(true);
								cur5Label.setVisible(true);
							} else {
								// Tagessatz
								dayNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto))));
								hourNetLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto)/hoursPerDay)));
								dayFlightHonorarLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, 1.0, overnightStay, sconto))));
								hourFlightHonorarLabel.setText(String.valueOf(f.format(price.reCalculateHonorar(fee, roundTripTicket, hotelCosts, 1.0, overnightStay, sconto)/hoursPerDay)));
								dayNetLabel.setVisible(true);
								hourPriceLabel.setVisible(true);
								cur2Label.setVisible(true);
								cur3Label.setVisible(true);
								dayFlightHonorarLabel.setVisible(true);
								hourFlightHonorarLabel.setVisible(true);
								cur4Label.setVisible(true);
								cur5Label.setVisible(true);
							}
						}
					}
				} else {
					JOptionPane.showOptionDialog(null, "Für diese Stadt gibt es noch keine Daten. Sollen diese jetzt angelegt werden?","Fehlende Daten",
			                JOptionPane.YES_NO_CANCEL_OPTION,
			                JOptionPane.WARNING_MESSAGE, null, 
			                new String[]{"A", "B", "C"}, "B");
				}
			}
		});
		calculateButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allInFeeTextField.setText("0.00");
				buttonGroup.clearSelection();
				scontoComboBox.setSelectedIndex(2);
				scontoCheckBox.setSelected(false);
				overnightStayComboBox.setSelectedIndex(4);
				dayNetLabel.setVisible(false);
				hourNetLabel.setVisible(false);
				dayFlightHonorarLabel.setVisible(false);
				hourFlightHonorarLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
				cur4Label.setVisible(false);
				cur5Label.setVisible(false);
			}
		});
		resetButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton endButton = new JButton("Beenden");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		endButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		cur3Label = new JLabel("EUR");
		cur3Label.setFont(new Font("Dialog", Font.BOLD, 12));
		
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
		dayFlightHonorarLabel.setVisible(false);
		
		hourFlightHonorarLabel = new JLabel("Flight");
		hourFlightHonorarLabel.setForeground(new Color(0, 128, 0));
		hourFlightHonorarLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		hourFlightHonorarLabel.setVisible(false);
		
		cur4Label = new JLabel("EUR (Flug)");
		cur4Label.setForeground(new Color(0, 128, 0));
		cur4Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur4Label.setVisible(false);
		
		cur5Label = new JLabel("EUR (Flug)");
		cur5Label.setForeground(new Color(0, 128, 0));
		cur5Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur5Label.setVisible(false);
		
		hourNetLabel = new JLabel("Hour");
		hourNetLabel.setForeground(new Color(0, 0, 0));
		hourNetLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(calculateButton)
							.addGap(18)
							.addComponent(resetButton)
							.addGap(18)
							.addComponent(endButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(originCityLabel)
										.addComponent(targetCityLabel)
										.addComponent(allInFeeLabel)
										.addComponent(daysProjectLabel))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(20)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
													.addComponent(originCityTextField)
													.addComponent(targetCityComboBox, 0, 250, Short.MAX_VALUE))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(allInFeeTextField, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cur1Label)
													.addGap(18)
													.addComponent(scontoCheckBox)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(scontoComboBox, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(scontoLabel))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(overnightStayLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(133)
											.addComponent(railBonus50RadioButton))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(hourPriceLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(dayNetLabel)
													.addGap(18)
													.addComponent(cur2Label))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(hourNetLabel)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(cur3Label)))))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(33)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(hourFlightHonorarLabel)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cur5Label))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(dayFlightHonorarLabel)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cur4Label))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(21)
											.addComponent(railBonus100RadioButton))))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(dayPriceLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(railBonus25RadioButton)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(originCityLabel)
						.addComponent(originCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(targetCityLabel)
						.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(allInFeeLabel)
							.addComponent(allInFeeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cur1Label))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(scontoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(scontoLabel))
							.addComponent(scontoCheckBox)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(daysProjectLabel)
						.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(overnightStayLabel)
						.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(railBonus25RadioButton)
						.addComponent(railBonus50RadioButton)
						.addComponent(railBonus100RadioButton))
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(dayPriceLabel)
						.addComponent(dayNetLabel)
						.addComponent(dayFlightHonorarLabel)
						.addComponent(cur4Label)
						.addComponent(cur2Label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hourPriceLabel)
						.addComponent(hourFlightHonorarLabel)
						.addComponent(cur5Label)
						.addComponent(hourNetLabel)
						.addComponent(cur3Label))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(calculateButton)
						.addComponent(resetButton)
						.addComponent(endButton))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void contentsChanged(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalAdded(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

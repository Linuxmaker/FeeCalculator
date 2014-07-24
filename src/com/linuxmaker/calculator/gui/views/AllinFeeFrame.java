/**
 * AllinFeeFrame.java
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
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class AllinFeeFrame extends JFrame implements ListDataListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8189297737183218936L;
	private String originCity = new Settings().readSettings("pointOfDeparture");
	private String path = new Settings().readSettings("directory");
	private Double workingHours = Double.parseDouble(new Settings().readSettings("workinghours"));
	private Double maxDistance = Double.parseDouble(new Settings().readSettings("maxdistance"));
	private Double drivingTime = Double.parseDouble(new Settings().readSettings("drivingTime"));
	private JPanel contentPane;
	private JTextField originCityTextField;
	private JTextField feeTextField;
	private JComboBox<String> targetCityComboBox;
	private ComboBoxModel myComboBoxModel;
	private JLabel cur2Label;
	private JLabel cur3Label;
	private JLabel dayPriceLabel;
	private JLabel hourPriceLabel;
	private JLabel hourHonorarLabel;
	private JLabel dayHonorarLabel;
	private JCheckBox scontoCheckBox;
	private JComboBox<String> scontoComboBox;
	private JComboBox<String> overnightStayComboBox;
	private JTextField hoursPerDayTextField;
	private final JComboBox<String> daysProjectComboBox;
	private JCheckBox carCheckBox;
	
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
		setBounds(100, 100, 450, 400);
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
		targetCityComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					XMLCreator xmlelement = new XMLCreator();
					String city = (String) targetCityComboBox.getSelectedItem();
					if (Double.parseDouble(xmlelement.readXML(city).get(1)) < maxDistance && Double.parseDouble(xmlelement.readXML(city).get(2)) <= drivingTime) {
						daysProjectComboBox.setEnabled(true);
						overnightStayComboBox.setEnabled(false);
					} else if (Double.parseDouble(xmlelement.readXML(city).get(1)) < maxDistance && Double.parseDouble(xmlelement.readXML(city).get(2)) > drivingTime) {
						daysProjectComboBox.setEnabled(false);
						overnightStayComboBox.setEnabled(true);
					} else {
						daysProjectComboBox.setEnabled(false);
						overnightStayComboBox.setEnabled(true);
						overnightStayComboBox.setSelectedIndex(4);
					}
				}
			}
		});
		targetCityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				myComboBoxModel.reload();
				targetCityComboBox.setModel(myComboBoxModel);				
			}
		});
		targetCityComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				hourHonorarLabel.setVisible(false);
				dayHonorarLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
			}
		});
		
		/*
		 * ComboBoxModel erzeugen
		 */
		myComboBoxModel = new ComboBoxModel();

		/*
		 * ComboBoxModel setzen
		 */
		myComboBoxModel.reload();
		targetCityComboBox.setModel(myComboBoxModel);
		
		
		
		targetCityComboBox.setEditable(false);
		targetCityComboBox.setMaximumRowCount(10);
		targetCityComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		JLabel feeLabel = new JLabel("Netto-Honorar");
		feeLabel.setToolTipText("Keine Eingabe ermittelt die Reisekosten");
		feeLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		
		feeTextField = new JTextField();
		feeTextField.setText("0.00");
		feeTextField.setToolTipText("Tageshonorar oder Stundensatz");
		feeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		feeTextField.setColumns(10);
		
		JLabel daysProjectLabel = new JLabel("Projekttage Monat");
		daysProjectLabel.setToolTipText("Anzahl der tatsächlichen Projekttage bei Zeitfahrkarten");
		daysProjectLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		
		daysProjectComboBox = new JComboBox<String>();
		daysProjectComboBox.setToolTipText("Projekttage im Monat");
		daysProjectComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
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
				int overnightStay = Integer.valueOf((String) overnightStayComboBox.getSelectedItem());
				Double hoursPerDay = Double.parseDouble(hoursPerDayTextField.getText());
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
					dayHonorarLabel.setText(String.valueOf(f.format(price.feeCalculator(
							city, 
							travelDistance, 
							fee, 
							hoursPerDay, 
							sconto, 
							projektdays, 
							overnightStay,
							carCheckBox.isSelected()))));
					hourHonorarLabel.setText(String.valueOf(f.format(price.feeCalculator(
							city, 
							travelDistance, 
							fee, 
							hoursPerDay, 
							sconto, 
							projektdays, 
							overnightStay,
							carCheckBox.isSelected())/hoursPerDay)));
					if (feeTextField.getText().equals("0.00")) {
						dayPriceLabel.setText("Reisekosten");
						hourPriceLabel.setText("Reisekosten/h");
					} else {
						dayPriceLabel.setText("Tageshonorar");
						hourPriceLabel.setText("Stundenhonorar");
					}
						
					dayPriceLabel.setVisible(true);
					hourPriceLabel.setVisible(true);
					dayHonorarLabel.setVisible(true);
					hourHonorarLabel.setVisible(true);
					cur2Label.setVisible(true);
					cur3Label.setVisible(true);
					
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
		
		scontoComboBox = new JComboBox<String>();
		scontoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}));
		scontoComboBox.setSelectedIndex(2);
		scontoComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoCheckBox = new JCheckBox("");
		scontoCheckBox.setSelected(false);
		scontoCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));

		JLabel overnightStayLabel = new JLabel("Übernachtungen");
		overnightStayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		overnightStayComboBox = new JComboBox<String>();
		overnightStayComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"0", "1", "2", "3", "4", "5"}));
		overnightStayComboBox.setSelectedIndex(0);
		overnightStayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				feeTextField.setText("0.00");
				daysProjectComboBox.setSelectedIndex(19);
				overnightStayComboBox.setSelectedIndex(4);
				scontoComboBox.setSelectedIndex(2);
				scontoCheckBox.setSelected(true);
				hoursPerDayTextField.setText(String.valueOf(workingHours));
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				hourHonorarLabel.setVisible(false);
				dayHonorarLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
			}
		});
		resetButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JLabel hoursPerDayLabel = new JLabel("Stunden pro Tag");
		hoursPerDayLabel.setToolTipText("Änderungsmöglichkeit bei Abweichungen bei der Regelarbeitszeit");
		hoursPerDayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		hoursPerDayTextField = new JTextField();
		hoursPerDayTextField.setText(String.valueOf(workingHours));
		hoursPerDayTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		hoursPerDayTextField.setColumns(10);
		
		carCheckBox = new JCheckBox("Autobenutzung");
		carCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					daysProjectComboBox.setEnabled(false);
					overnightStayComboBox.setEnabled(true);
					overnightStayComboBox.setSelectedIndex(1);
				} else {
					daysProjectComboBox.setEnabled(true);
				}
			}
		});
		carCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(dayPriceLabel)
												.addComponent(hourPriceLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(hourHonorarLabel, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
													.addGap(18)
													.addComponent(cur3Label))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(dayHonorarLabel)
													.addGap(18)
													.addComponent(cur2Label)))
											.addGap(35))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(calculateButton)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(resetButton)
											.addGap(82)))
									.addGap(44)
									.addComponent(endButton))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(scontoCheckBox)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(scontoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(daysProjectLabel)
											.addGap(12)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(hoursPerDayTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
												.addComponent(daysProjectComboBox, Alignment.LEADING, 0, 51, Short.MAX_VALUE))
											.addGap(6)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(overnightStayLabel)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
												.addComponent(hoursPerDayLabel))))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scontoLabel)))
							.addGap(60))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(feeLabel, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(targetCityLabel)
											.addGap(34)))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(originCityLabel, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
									.addGap(3)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(feeTextField, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cur1Label))
								.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
								.addComponent(originCityTextField, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(30, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(carCheckBox)
							.addContainerGap(325, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(originCityLabel)
						.addComponent(originCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(carCheckBox)
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(daysProjectLabel)
						.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(overnightStayLabel)
						.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hoursPerDayTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hoursPerDayLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(dayPriceLabel)
						.addComponent(cur2Label)
						.addComponent(dayHonorarLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hourPriceLabel)
						.addComponent(cur3Label)
						.addComponent(hourHonorarLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(calculateButton)
						.addComponent(resetButton)
						.addComponent(endButton))
					.addContainerGap(9, Short.MAX_VALUE))
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
}
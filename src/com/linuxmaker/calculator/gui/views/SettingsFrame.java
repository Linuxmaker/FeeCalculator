package com.linuxmaker.calculator.gui.views;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Toolkit;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.linuxmaker.calculator.Settings;
import com.linuxmaker.calculator.XmlFileWriter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SettingsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField defaultCityTextField;
	private JTextField workingHoursTextField;
	private JTextField minDayFeeTextField;	
	private JTextField maxDistanceTextField;
	private JTextField folderTextField;
	private JLabel drivingTimeLabel;
	private JTextField drivingTimeTextField;
	private JTextField consumptionTextField;
	private JTextField fuelCostTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton railBonus25RadioButton;
	private JRadioButton railBonus50RadioButton;
	private JRadioButton railBonus100RadioButton;
	private JRadioButton railBonusNoRadioButton;
	private Double railBonus;
	private JTextField fuelConsumptionTextField;
	private JButton closeButton;
	private JButton okayButton;
	private JButton changeButton;
	private JTextField publicDistanceTextField;
	private JLabel unit8Label;
	private JLabel defaultCityLabel;
	private JLabel workingHoursLabel;
	private JLabel minDayFeeLabel;
	private JLabel consumptionLabel;
	private JLabel fuelConsumptionLabel;
	private JLabel maxDistanceLabel;
	private JLabel fuelCostLabel;
	private JLabel publicTransportLabel;
	private JLabel folderLabel;
	private JLabel unit1Label;
	private JLabel unit2Label;
	private JLabel unit3Label;
	private JLabel unit4Label;
	private JLabel unit5Label;
	private JLabel unit6Label;
	private JLabel unit7Label;
	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame();
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
	public SettingsFrame() {
		setTitle("Einstellungen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 388, 478);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		initializeGuiObjects();
		contentPane.setLayout(createLayout());
	}

	/**
	 * Initializes the GUI elements
	 */
	private void initializeGuiObjects(){
		defaultCityLabel = new JLabel("Ausgangsstadt");
		defaultCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		defaultCityTextField = new JTextField();
		defaultCityTextField.setEnabled(false);
		defaultCityTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		defaultCityTextField.setColumns(10);
		
		workingHoursLabel = new JLabel("Arbeitsstunden pro Tag");
		workingHoursLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		workingHoursTextField = new JTextField();
		workingHoursTextField.setEnabled(false);
		workingHoursTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		workingHoursTextField.setColumns(10);
		
		minDayFeeLabel = new JLabel("Mindest-Tageshonorar");
		minDayFeeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		minDayFeeTextField = new JTextField();
		minDayFeeTextField.setEnabled(false);
		minDayFeeTextField.setToolTipText("Welcher ist Ihr niedrigster Tagessatz?");
		minDayFeeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		minDayFeeTextField.setColumns(10);
		
		unit2Label = new JLabel("EUR");
		unit2Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		maxDistanceLabel = new JLabel("Maximalstrecke für Monatsticket");
		maxDistanceLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		unit3Label = new JLabel("km");
		unit3Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		maxDistanceTextField = new JTextField();
		maxDistanceTextField.setEnabled(false);
		maxDistanceTextField.setToolTipText("MaximalStrecke für Zeitkarten der DB AG");
		maxDistanceTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxDistanceTextField.setColumns(10);
		

		drivingTimeLabel = new JLabel("Max. Fahrzeit pro Tag");
		drivingTimeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		drivingTimeTextField = new JTextField();
		drivingTimeTextField.setEnabled(false);
		drivingTimeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		drivingTimeTextField.setColumns(10);
		
		folderLabel = new JLabel("Ablageort");
		folderLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		folderTextField = new JTextField();
		folderTextField.setEnabled(false);
		folderTextField.setColumns(10);
		
		okayButton = new JButton("OK");
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (railBonus25RadioButton.isSelected()) {
					railBonus = 0.75;
				} else if (railBonus50RadioButton.isSelected()) {
					railBonus = 0.5;
				} else if (railBonus100RadioButton.isSelected()) {
					railBonus = 0.0;
				} else if (railBonusNoRadioButton.isSelected()) {
					railBonus = 1.0;
				}
				Settings setValue = new Settings();
				File folder = new File(System.getProperties().getProperty("user.home")+ File.separator + folderTextField.getText());
				setValue.generateSettings(workingHoursTextField.getText(), 
						folderTextField.getText(), 
						defaultCityTextField.getText(), 
						maxDistanceTextField.getText().replace(',', '.'), 
						minDayFeeTextField.getText().replace(',', '.'), 
						drivingTimeTextField.getText().replace(',', '.'), 
						consumptionTextField.getText().replace(',', '.'), 
						fuelCostTextField.getText().replace(',', '.'),
						fuelConsumptionTextField.getText().replace(',', '.'),
						publicDistanceTextField.getText().replace(',', '.'),
						railBonus);
				
				if (!folder.isDirectory()) {
					folder.mkdir();
				}
				
				if (!new File(System.getProperties().getProperty("user.home")+ File.separator + folderTextField.getText() + File.separator + defaultCityTextField.getText() + "City.xml").exists()) {
					XmlFileWriter city = new XmlFileWriter();
					try {
						city.xmlWrite(defaultCityTextField.getText());
					} catch (DOMException
							| XPathExpressionException | IOException | SAXException
							| TransformerException e) {
						e.printStackTrace();
					}
				}
				
				Container frame = okayButton.getParent();
				do
					frame = frame.getParent();
				while (!(frame instanceof JFrame));
				((JFrame) frame).dispose();
			}
		});
		okayButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		closeButton = new JButton("Abbruch");
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
				
		unit7Label = new JLabel("Stunden");
		unit7Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		consumptionLabel = new JLabel("Fahrtkosten");
		consumptionLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		consumptionTextField = new JTextField();
		consumptionTextField.setEnabled(false);
		consumptionTextField.setToolTipText("Berechnet mit aktuellen Treibstoffpreisen und 15.000 km Laufleistung pro Jahr");
		consumptionTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		consumptionTextField.setColumns(10);
		
		unit4Label = new JLabel("ct/km");
		unit4Label.setToolTipText("Informationen auf www.adac.de");
		unit4Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		fuelCostLabel = new JLabel("Kraftstoffkosten");
		fuelCostLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		fuelCostTextField = new JTextField();
		fuelCostTextField.setEnabled(false);
		fuelCostTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		fuelCostTextField.setColumns(10);
		
		unit5Label = new JLabel("EUR/Liter");
		unit5Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus25RadioButton = new JRadioButton("BahnCard25");
		railBonus25RadioButton.setEnabled(false);
		buttonGroup.add(railBonus25RadioButton);
		railBonus25RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus50RadioButton = new JRadioButton("BahnCard50");
		railBonus50RadioButton.setEnabled(false);
		buttonGroup.add(railBonus50RadioButton);
		railBonus50RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus100RadioButton = new JRadioButton("BahnCard100");
		railBonus100RadioButton.setEnabled(false);
		buttonGroup.add(railBonus100RadioButton);
		railBonus100RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonusNoRadioButton = new JRadioButton("Ohne BahnCard");
		railBonusNoRadioButton.setEnabled(false);
		buttonGroup.add(railBonusNoRadioButton);
		railBonusNoRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		fuelConsumptionTextField = new JTextField();
		fuelConsumptionTextField.setEnabled(false);
		fuelConsumptionTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		fuelConsumptionTextField.setColumns(10);
		
		fuelConsumptionLabel = new JLabel("Kraftstoffverbrauch");
		fuelConsumptionLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		unit6Label = new JLabel("Liter/100 km");
		unit6Label.setFont(new Font("Dialog", Font.PLAIN, 12));

		unit1Label = new JLabel("Stunden");
		unit1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		publicTransportLabel = new JLabel("Umkreisradius für ÖPNV");
		publicTransportLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		publicDistanceTextField = new JTextField();
		publicDistanceTextField.setEnabled(false);
		publicDistanceTextField.setColumns(10);
		
		unit8Label = new JLabel("km");
		unit8Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		if (new File(System.getProperties().getProperty("user.home")+File.separator+".settings.cfg").exists()) {
			Settings getValue = new Settings();
			defaultCityTextField.setText(getValue.readSettings("pointOfDeparture"));
			workingHoursTextField.setText(getValue.readSettings("workinghours").replace('.', ','));
			minDayFeeTextField.setText(getValue.readSettings("minFee").replace('.', ','));
			maxDistanceTextField.setText(getValue.readSettings("maxdistance").replace('.', ','));
			consumptionTextField.setText(getValue.readSettings("consumption").replace('.', ','));
			fuelCostTextField.setText(getValue.readSettings("fuelprice").replace('.', ','));
			fuelConsumptionTextField.setText(getValue.readSettings("fuelConsumption").replace('.', ','));
			drivingTimeTextField.setText(getValue.readSettings("drivingTime").replace('.', ','));
			publicDistanceTextField.setText(getValue.readSettings("publicTransport").replace('.', ','));
			folderTextField.setText(getValue.readSettings("directory"));			
			switch (getValue.readSettings("railCard")) {
			case "0.75":
				railBonus25RadioButton.setSelected(true);
				break;
			case "0.5":
				railBonus50RadioButton.setSelected(true);
				break;
			case "0.0":
				railBonus100RadioButton.setSelected(true);
				break;
			default:
				railBonusNoRadioButton.setSelected(true);
				break;
			}
		} else {
			workingHoursTextField.setText("8");
			minDayFeeTextField.setText("400.00");
			maxDistanceTextField.setText("420.00");
			folderTextField.setText("Data");
			drivingTimeTextField.setText("2.5");
			railBonusNoRadioButton.setSelected(true);
		}
		
		changeButton = new JButton("Ändern");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				defaultCityTextField.setEnabled(true);
				workingHoursTextField.setEnabled(true);
				minDayFeeTextField.setEnabled(true);
				maxDistanceTextField.setEnabled(true);
				consumptionTextField.setEnabled(true);
				fuelCostTextField.setEnabled(true);
				fuelConsumptionTextField.setEnabled(true);
				drivingTimeTextField.setEnabled(true);
				folderTextField.setEnabled(true);
				railBonus25RadioButton.setEnabled(true);
				railBonus50RadioButton.setEnabled(true);
				railBonus100RadioButton.setEnabled(true);
				railBonusNoRadioButton.setEnabled(true);
				publicDistanceTextField.setEnabled(true);
				changeButton.setEnabled(false);
			}
		});
		changeButton.setFont(new Font("Dialog", Font.BOLD, 12));
	}
	
	/**
	 * Creates the GUI layout
	 */
	private LayoutManager createLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		//gl_contentPane.setAutoCreateGaps(true); // Makes problems here
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(defaultCityLabel)
										.addComponent(workingHoursLabel)
										.addComponent(minDayFeeLabel)
										.addComponent(consumptionLabel)
										.addComponent(fuelConsumptionLabel)
										.addComponent(maxDistanceLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(defaultCityTextField, Alignment.LEADING)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(workingHoursTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(unit1Label))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
														.addComponent(maxDistanceTextField, 0, 0, Short.MAX_VALUE)
														.addComponent(minDayFeeTextField, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(unit3Label)
														.addComponent(unit2Label)))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(6)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(fuelCostTextField, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
												.addComponent(fuelConsumptionTextField, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
												.addComponent(consumptionTextField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(unit5Label)
												.addComponent(unit4Label)
												.addComponent(unit6Label)
												.addComponent(unit7Label))
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(19))
								.addComponent(fuelCostLabel)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(drivingTimeLabel)
										.addComponent(publicTransportLabel))
									.addGap(64)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(drivingTimeTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(publicDistanceTextField, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(unit8Label)))
									.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(railBonusNoRadioButton)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(railBonus25RadioButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(railBonus50RadioButton)
									.addGap(18)
									.addComponent(railBonus100RadioButton))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(folderLabel)
									.addGap(18)
									.addComponent(folderTextField, 156, 156, 156))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(changeButton)
									.addGap(18)
									.addComponent(okayButton)
									.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
									.addComponent(closeButton)))
							.addGap(16))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(defaultCityLabel)
						.addComponent(defaultCityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(workingHoursLabel)
						.addComponent(unit1Label)
						.addComponent(workingHoursTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(minDayFeeLabel)
						.addComponent(minDayFeeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(unit2Label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(maxDistanceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(unit3Label)
						.addComponent(maxDistanceLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(consumptionLabel)
						.addComponent(consumptionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(unit4Label))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fuelCostLabel)
						.addComponent(unit5Label)
						.addComponent(fuelCostTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fuelConsumptionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fuelConsumptionLabel)
						.addComponent(unit6Label))
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(drivingTimeLabel)
						.addComponent(drivingTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(unit7Label))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(publicTransportLabel)
						.addComponent(publicDistanceTextField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(unit8Label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(folderLabel)
						.addComponent(folderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(railBonus25RadioButton)
						.addComponent(railBonus50RadioButton)
						.addComponent(railBonus100RadioButton))
					.addGap(18)
					.addComponent(railBonusNoRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(closeButton)
						.addComponent(changeButton)
						.addComponent(okayButton))
					.addContainerGap())
		);
		return gl_contentPane;	
	}
}

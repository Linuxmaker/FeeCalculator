package com.linuxmaker.calculator.gui.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

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

import java.awt.Component;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SettingsFrame extends JFrame {

	private JPanel contentPane;
	private JTextField defaultCityTextField;
	private JTextField workingHoursTextField;
	private JTextField minDayFeeTextField;
	private JLabel cur2Label;
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
		setBounds(100, 100, 388, 429);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel defaultCityLabel = new JLabel("Ausgangsstadt");
		defaultCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		defaultCityTextField = new JTextField();
		defaultCityTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		defaultCityTextField.setColumns(10);
		
		JLabel workingHoursLabel = new JLabel("Stunden pro Tag");
		workingHoursLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		workingHoursTextField = new JTextField();
		workingHoursTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		workingHoursTextField.setColumns(10);
		
		JLabel minDayFeeLabel = new JLabel("Mindest-Tageshonorar");
		minDayFeeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		minDayFeeTextField = new JTextField();
		minDayFeeTextField.setToolTipText("Welcher ist Ihr niedrigster Tagessatz?");
		minDayFeeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		minDayFeeTextField.setColumns(10);
		
		JLabel cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel maxDistanceLabel = new JLabel("Maximalstrecke für Monatsticket");
		maxDistanceLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		cur2Label = new JLabel("km");
		cur2Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		maxDistanceTextField = new JTextField();
		maxDistanceTextField.setToolTipText("MaximalStrecke für Zeitkarten der DB AG");
		maxDistanceTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxDistanceTextField.setColumns(10);
		

		drivingTimeLabel = new JLabel("Max. Fahrzeit pro Tag");
		drivingTimeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		drivingTimeTextField = new JTextField();
		drivingTimeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		drivingTimeTextField.setColumns(10);
		
		JLabel folderLabel = new JLabel("Ablageort");
		folderLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		folderTextField = new JTextField();
		folderTextField.setColumns(10);
		
		if (new File(System.getProperties().getProperty("user.home")+File.separator+".settings.cfg").exists()) {
			Settings getValue = new Settings();
			defaultCityTextField.setText(getValue.readSettings("pointOfDeparture"));
			workingHoursTextField.setText(getValue.readSettings("workinghours"));
			minDayFeeTextField.setText(getValue.readSettings("minFee"));
			maxDistanceTextField.setText(getValue.readSettings("maxdistance"));
			folderTextField.setText(getValue.readSettings("directory"));
			drivingTimeTextField.setText(getValue.readSettings("drivingTime"));
		//	consumptionTextField.setText(getValue.readSettings("consumption"));
		//	fuelCostTextField.setText(getValue.readSettings("fuel"));
			if (getValue.readSettings("railCard")=="0.75") {
				railBonus25RadioButton.setSelected(true);
			} else if (getValue.readSettings("railCard")=="0.5") {
				railBonus50RadioButton.setSelected(true);
			} else if (getValue.readSettings("railCard")=="0.0") {
				railBonus100RadioButton.setSelected(true);
			} else if (getValue.readSettings("railCard")=="1.0") {
				railBonusNoRadioButton.setSelected(true);
			}
		} else {
			workingHoursTextField.setText("8");
			minDayFeeTextField.setText("400.00");
			maxDistanceTextField.setText("420.00");
			folderTextField.setText("Data");
			drivingTimeTextField.setText("2.5");
			railBonusNoRadioButton.setSelected(true);
		}
		
		final JButton okayButton = new JButton("OK");
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
				setValue.generateSettings(workingHoursTextField.getText(), folderTextField.getText(), defaultCityTextField.getText(), maxDistanceTextField.getText(), minDayFeeTextField.getText(), drivingTimeTextField.getText(), consumptionTextField.getText(), fuelCostTextField.getText(), railBonus);
				
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
						// TODO Auto-generated catch block
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
		
		final JButton closeButton = new JButton("Abbruch");
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
				
		JLabel cur3Label = new JLabel("Stunden");
		cur3Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel consumptionLabel = new JLabel("Fahrtkosten / km");
		consumptionLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		consumptionTextField = new JTextField();
		consumptionTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		consumptionTextField.setColumns(10);
		
		JLabel cur4Label = new JLabel("ct/km");
		cur4Label.setToolTipText("Informationen auf www.adac.de");
		cur4Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel fuelCostLabel = new JLabel("Treibstoffpreis pro Liter");
		fuelCostLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		fuelCostTextField = new JTextField();
		fuelCostTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		fuelCostTextField.setColumns(10);
		
		JLabel cur6Label = new JLabel("EUR");
		
		railBonus25RadioButton = new JRadioButton("BahnCard25");
		buttonGroup.add(railBonus25RadioButton);
		railBonus25RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus50RadioButton = new JRadioButton("BahnCard50");
		buttonGroup.add(railBonus50RadioButton);
		railBonus50RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonus100RadioButton = new JRadioButton("BahnCard100");
		buttonGroup.add(railBonus100RadioButton);
		railBonus100RadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		railBonusNoRadioButton = new JRadioButton("Ohne BahnCard");
		buttonGroup.add(railBonusNoRadioButton);
		railBonusNoRadioButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(defaultCityLabel)
								.addComponent(workingHoursLabel)
								.addComponent(minDayFeeLabel)
								.addComponent(maxDistanceLabel)
								.addComponent(consumptionLabel))
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(maxDistanceTextField, 0, 0, Short.MAX_VALUE)
											.addComponent(minDayFeeTextField, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
											.addComponent(workingHoursTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(cur2Label)
											.addComponent(cur1Label)))
									.addComponent(defaultCityTextField, Alignment.LEADING))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(folderTextField, 0, 0, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(drivingTimeTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cur3Label))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(fuelCostTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
												.addComponent(consumptionTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(cur6Label)
												.addComponent(cur4Label))))))
							.addContainerGap(10, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(folderLabel)
							.addContainerGap(309, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(drivingTimeLabel)
							.addContainerGap(237, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(fuelCostLabel)
							.addContainerGap(224, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(okayButton)
							.addPreferredGap(ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
							.addComponent(closeButton)
							.addGap(37))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(railBonus25RadioButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(railBonus50RadioButton)
							.addGap(18)
							.addComponent(railBonus100RadioButton)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(railBonusNoRadioButton)
							.addContainerGap(239, Short.MAX_VALUE))))
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
						.addComponent(workingHoursTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(minDayFeeLabel)
						.addComponent(minDayFeeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cur1Label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(maxDistanceLabel)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(maxDistanceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cur2Label)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(consumptionLabel)
						.addComponent(cur4Label)
						.addComponent(consumptionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fuelCostLabel)
						.addComponent(fuelCostTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cur6Label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(drivingTimeLabel)
						.addComponent(drivingTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cur3Label))
					.addGap(14)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(folderLabel)
						.addComponent(folderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(railBonus50RadioButton)
						.addComponent(railBonus25RadioButton)
						.addComponent(railBonus100RadioButton))
					.addGap(10)
					.addComponent(railBonusNoRadioButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(okayButton)
						.addComponent(closeButton))
					.addGap(34))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

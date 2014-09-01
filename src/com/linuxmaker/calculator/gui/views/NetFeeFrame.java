/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import static com.linuxmaker.calculator.Constants.ORIGIN_CITY;
import static com.linuxmaker.calculator.Constants.WORKINGDAY;

import com.linuxmaker.calculator.ComboBoxModel;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.LayoutManager;

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
import java.util.Collections;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import com.linuxmaker.calculator.Fee;
import com.linuxmaker.calculator.Settings;
import com.linuxmaker.calculator.XMLCreator;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class NetFeeFrame extends JFrame implements ListDataListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String originCity = new Settings().readSettings("pointOfDeparture");
	private String path = new Settings().readSettings("directory");
	private Double workingHours = Double.parseDouble(new Settings().readSettings("workinghours"));
	private Double maxDistance = Double.parseDouble(new Settings().readSettings("maxdistance"));
	private Double drivingTime = Double.parseDouble(new Settings().readSettings("drivingTime"));
	private JPanel contentPane;
	private JCheckBox carCheckBox;
	private JCheckBox scontoCheckBox;
	private JComboBox daysProjectComboBox;
	private JComboBox overnightStayComboBox;
	private JComboBox scontoComboBox;
	private JComboBox<String> targetCityComboBox;
	private ComboBoxModel myComboBoxModel;
	private JTextField hoursPerDayTextField;
	private JTextField originCityTextField;
	private JTextField allInFeeTextField;
	private JLabel allInFeeLabel;
	private JLabel cur1Label;
	private JLabel cur2Label;
	private JLabel cur3Label;
	private JLabel scontoLabel;
	private JLabel daysProjectLabel;
	private JLabel overnightStayLabel;
	private JLabel targetCityLabel;
	private JLabel originCityLabel;
	private JLabel hoursPerDayLabel;
	private JLabel dayPriceLabel;
	private JLabel hourPriceLabel;
	private JLabel dayAmountLabel;
	private JLabel hourAmountLabel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton closeButton;
	private JButton resetButton;
	private JButton calculateButton;
	private Double fee;
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
		setBounds(100, 100, 450, 345);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(getParent());
		initializeGuiObjects();		
		contentPane.setLayout(createLayout());
	}
	
	/**
	 * Initializes the GUI elements
	 */	
	private void initializeGuiObjects(){
		originCityLabel = new JLabel("Ausgangsstadt");
		originCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		originCityTextField = new JTextField();
		originCityTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		originCityTextField.setColumns(10);
		originCityTextField.setText(ORIGIN_CITY);
		
	    targetCityLabel = new JLabel("Projektstadt");
		targetCityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
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
			public void actionPerformed(ActionEvent e) {
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				dayAmountLabel.setVisible(false);
				hourAmountLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);
			}
		});
		
		/*
		 * Creates ComboBoxModel
		 */
		myComboBoxModel = new ComboBoxModel();

		/*
		 * Sets ComboBoxModel
		 */
		myComboBoxModel.reload();
		targetCityComboBox.setModel(myComboBoxModel);
		
		
		targetCityComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		allInFeeLabel = new JLabel("AllIn-Honorar");
		allInFeeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		allInFeeTextField = new JTextField();
		allInFeeTextField.setToolTipText("AllIn-Honorar als Tagessatz oder Stundensatz");
		allInFeeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		allInFeeTextField.setColumns(10);
		
		cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoCheckBox = new JCheckBox("");
		scontoCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		scontoComboBox = new JComboBox();
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
		overnightStayComboBox.setSelectedIndex(0);
		overnightStayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		dayPriceLabel = new JLabel("Netto-Tagessatz");
		dayPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dayPriceLabel.setVisible(false);
		
		hourPriceLabel = new JLabel("Netto-Stundensatz");
		hourPriceLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dayPriceLabel.setVisible(false);
		
		dayAmountLabel = new JLabel("dAmount");
		dayAmountLabel.setVisible(false);
		dayAmountLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		cur2Label = new JLabel("EUR");
		cur2Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur2Label.setVisible(false);
		
		calculateButton = new JButton("Netto-Honorar");
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator xmlelement = new XMLCreator();
				Fee price = new Fee();
				DecimalFormat f = new DecimalFormat("#0.00");
				String city = (String) targetCityComboBox.getSelectedItem();
				String fileName = System.getProperties().getProperty("user.home")+ File.separator + path + File.separator + originCityTextField.getText() + "City.xml";
				File xmlFile = new File(fileName);
				if (allInFeeTextField.getText().replace(',', '.').matches("\\d+([.]{1}\\d+)?")) {
					fee = Double.parseDouble(allInFeeTextField.getText().replace(',', '.'));
				} else {
					JOptionPane.showMessageDialog(null, "Es sind nur Zahlenwerte erlaubt!");
				}
				Double travelDistance = Double.parseDouble(xmlelement.readXML(city).get(1));
				int overnightStay = Integer.valueOf((String) overnightStayComboBox.getSelectedItem());
				Double hoursPerDay = Double.parseDouble(hoursPerDayTextField.getText());
				int projektdays = Integer.valueOf((String) daysProjectComboBox.getSelectedItem());
				Double sconto = Double.parseDouble((String) scontoComboBox.getSelectedItem());
				/*
				 * Checking whether the XML file of the selected city already exists
				 */				
				if (xmlFile.exists()) {
					/*
					 * Check if discount was selected and allocation of that value
					 */
					if (scontoCheckBox.isSelected()) {
						sconto = Double.parseDouble((String) scontoComboBox.getSelectedItem())/100 + 1;
					} else {
						sconto = 1.0;
					}
					dayPriceLabel.setText("Netto-Tagessatz");
					dayPriceLabel.setVisible(true);
					hourPriceLabel.setText("Netto-Stundensatz");
					hourPriceLabel.setVisible(true);
					dayAmountLabel.setText(String.valueOf(f.format(price.netFeeCalculator(
							city, 
							travelDistance, 
							fee, 
							hoursPerDay, 
							sconto, 
							projektdays, 
							overnightStay,
							carCheckBox.isSelected()))));
					dayAmountLabel.setVisible(true);
					hourAmountLabel.setText(String.valueOf(f.format(price.netFeeCalculator(
							city, 
							travelDistance, 
							fee, 
							hoursPerDay, 
							sconto, 
							projektdays, 
							overnightStay,
							carCheckBox.isSelected())/hoursPerDay)));
					hourAmountLabel.setVisible(true);
					cur2Label.setVisible(true);
					cur3Label.setVisible(true);
				} else {
					JOptionPane.showOptionDialog(null, "Für diese Stadt gibt es noch keine Daten. Sollen diese jetzt angelegt werden?","Fehlende Daten",
			                JOptionPane.YES_NO_CANCEL_OPTION,
			                JOptionPane.WARNING_MESSAGE, null, 
			                new String[]{"A", "B", "C"}, "B");
				}
			}
		});
		calculateButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allInFeeTextField.setText("0.00");
				buttonGroup.clearSelection();
				scontoComboBox.setSelectedIndex(2);
				scontoCheckBox.setSelected(false);
				overnightStayComboBox.setSelectedIndex(4);
				dayAmountLabel.setVisible(false);
				hourAmountLabel.setVisible(false);
				dayPriceLabel.setVisible(false);
				hourPriceLabel.setVisible(false);
				cur2Label.setVisible(false);
				cur3Label.setVisible(false);

			}
		});
		resetButton.setFont(new Font("Dialog", Font.BOLD, 12));
		
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
		
		cur3Label = new JLabel("EUR");
		cur3Label.setFont(new Font("Dialog", Font.BOLD, 12));
		cur3Label.setVisible(false);
		
		hourAmountLabel = new JLabel("hAmount");
		hourAmountLabel.setForeground(new Color(0, 0, 0));
		hourAmountLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		hourAmountLabel.setVisible(false);
		
		carCheckBox = new JCheckBox("Autobenutzung");
		carCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		hoursPerDayTextField = new JTextField();
		hoursPerDayTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		hoursPerDayTextField.setText(String.valueOf(workingHours));
		hoursPerDayTextField.setColumns(10);
		
		hoursPerDayLabel = new JLabel("Stunden pro Tag");
		hoursPerDayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
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
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(originCityLabel)
									.addComponent(targetCityLabel)
									.addComponent(allInFeeLabel))
								.addGap(41)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
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
								.addComponent(calculateButton)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(resetButton)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(closeButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(hourPriceLabel)
								.addComponent(dayPriceLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(dayAmountLabel)
								.addComponent(hourAmountLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cur3Label)
								.addComponent(cur2Label)))
						.addComponent(carCheckBox)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(daysProjectLabel)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(hoursPerDayTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(daysProjectComboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(overnightStayLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(hoursPerDayLabel))))
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
					.addComponent(carCheckBox)
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(daysProjectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(overnightStayLabel)
						.addComponent(overnightStayComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(daysProjectLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hoursPerDayTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hoursPerDayLabel))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(dayPriceLabel)
						.addComponent(dayAmountLabel)
						.addComponent(cur2Label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hourPriceLabel)
						.addComponent(hourAmountLabel)
						.addComponent(cur3Label))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(closeButton)
						.addComponent(resetButton)
						.addComponent(calculateButton))
					.addContainerGap(8, Short.MAX_VALUE))
		);
		return gl_contentPane;
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

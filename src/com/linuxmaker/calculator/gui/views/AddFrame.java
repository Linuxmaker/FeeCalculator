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
import javax.swing.JOptionPane;
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
	private JTextField singleTrainTicketTextField;
	private JTextField monthlyTrainTicketTextField;
	private JTextField hotelCostsTextField;
	private JLabel eur1Label;
	private JLabel eur2Label;
	private JLabel eur3Label;
	private JLabel cityLabel;
	private JLabel resultLabel;
	private JLabel monthlyTrainTicketLabel;
	private JLabel singleTrainTicketLabel;
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
							monthlyTrainTicketTextField.setEnabled(true);
							singleTrainTicketTextField.setEnabled(true);
							if (search.parsedistance(originCity, cityTextField.getText()) <= Double.parseDouble(distancePublicTransport)) {
								publicTransportCheckBox.setEnabled(true);
							}							
						} else {
							monthlyTrainTicketTextField.setEnabled(false);
							monthlyTrainTicketTextField.setText("0.00");
							singleTrainTicketTextField.setEnabled(true);
							publicTransportCheckBox.setEnabled(false);
						}
						if (search.parseduration(originCity, cityTextField.getText()) <= Double.parseDouble(drivingTime)) {
							hotelCostsTextField.setEnabled(false);
							hotelCostsTextField.setText("0.00");
						} else {
							hotelCostsTextField.setEnabled(true);
							publicTransportCheckBox.setEnabled(false);
						}
					} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		singleTrainTicketLabel = new JLabel("Bahnticket");
		singleTrainTicketLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		singleTrainTicketTextField = new JTextField();
		singleTrainTicketTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		singleTrainTicketTextField.setEnabled(false);
		singleTrainTicketTextField.setToolTipText("Betrag für die Hin- und Rückfahrt der DB AG");
		singleTrainTicketTextField.setColumns(10);
		
		monthlyTrainTicketLabel = new JLabel("Monatsfahrkarte");
		monthlyTrainTicketLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		monthlyTrainTicketTextField = new JTextField();
		monthlyTrainTicketTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		monthlyTrainTicketTextField.setEnabled(false);
		monthlyTrainTicketTextField.setColumns(10);
		
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
				if (singleTrainTicketTextField.getText().replace(',', '.').matches("\\d+([.]{1}\\d+)?")) {
					if (monthlyTrainTicketTextField.getText().replace(',', '.').matches("\\d+([.]{1}\\d+)?")) {
						System.out.println("Monatsticket ist aktiviert");
						if (hotelCostsTextField.isEnabled() && hotelCostsTextField.getText().replace(',', '.').matches("\\d+([.]{1}\\d+)?")) {
							try {
								writeData();
							} catch (XPathExpressionException | SAXException
									| ParserConfigurationException
									| JDOMException e) {
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Es sind unter \"Hotelkosten\" nur Zahlenwerte erlaubt!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Es sind unter \"Monatsfahrkarte\" nur Zahlenwerte erlaubt!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Es sind unter \"Bahnticket\" nur Zahlenwerte erlaubt!");
					singleTrainTicketTextField.setFocusable(true);
				}
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
		publicTransportCheckBox.setEnabled(false);
	}
	
	/**
	 * Creates the GUI layout
	 */
	private LayoutManager createLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setAutoCreateGaps(true);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(singleTrainTicketLabel)
								.addComponent(monthlyTrainTicketLabel)
								.addComponent(hotelCostsLabel)
								.addComponent(cityLabel)
								.addComponent(resultLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(hotelCostsTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(monthlyTrainTicketTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(singleTrainTicketTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
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
						.addComponent(singleTrainTicketTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eur1Label)
						.addComponent(singleTrainTicketLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(monthlyTrainTicketTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(eur2Label))
						.addComponent(monthlyTrainTicketLabel))
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
	
	protected void writeData() throws XPathExpressionException, SAXException, ParserConfigurationException, JDOMException {
		XMLCreator writer = new XMLCreator();
		writer.writeXML(
				cityTextField.getText(), 
				singleTrainTicketTextField.getText().replace(',', '.'),
				monthlyTrainTicketTextField.getText().replace(',', '.'),  
				hotelCostsTextField.getText().replace(',', '.'), 
				String.valueOf(publicTransportCheckBox.isSelected()));
		resultLabel.setVisible(true);
		reset();
	}

	protected void reset() {
		cityTextField.setText("");
		singleTrainTicketTextField.setEnabled(false);
		monthlyTrainTicketTextField.setEnabled(false);
		hotelCostsTextField.setEnabled(false);
		publicTransportCheckBox.setEnabled(false);
	}
}

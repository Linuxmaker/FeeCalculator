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

import com.linuxmaker.calculator.Parser;
import com.linuxmaker.calculator.Settings;
import com.linuxmaker.calculator.XMLCreator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;

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
	public JTextField cityTextField;
	private JLabel railTicketTwoLabel;
	private JTextField railTicketTwoTextField;
	private JLabel railTicketMonthLabel;
	private JTextField railTicketMonthTextField;
	private JLabel hotelCostsLabel;
	private JTextField hotelCostsTextField;
	private JLabel flightTicketLabel;
	private JTextField flightTicketTextField;
	private JLabel eur1Label;
	private JLabel eur2Label;
	private JLabel eur3Label;
	private JLabel eur4Label;
	private JLabel resultLabel;
	private String originCity = new Settings().readSettings("pointOfDeparture");
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
		
		final JLabel cityLabel = new JLabel("Projekt-Stadt");
		cityLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		cityTextField = new JTextField();
		cityTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		cityTextField.setColumns(10);
		
		JButton searchButton = new JButton("Suchen");
		searchButton.setFont(new Font("Dialog", Font.BOLD, 11));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parser search = new Parser();
				try {
					if (search.parsedistance(originCity, cityTextField.getText()) <= 420.00) {
						railTicketMonthTextField.setEnabled(true);
						railTicketTwoTextField.setEnabled(true);
					} else {
						railTicketMonthTextField.setEnabled(false);
						railTicketTwoTextField.setEnabled(true);	
					}
					if (search.parseduration(originCity, cityTextField.getText()) <= 2.5) {
						hotelCostsTextField.setEnabled(false);
					} else {
						hotelCostsTextField.setEnabled(true);
						if (search.parseduration(originCity, cityTextField.getText()) > 5) {
							flightTicketTextField.setEnabled(true);
						}
					}
				} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		railTicketTwoLabel = new JLabel("Bahnticket");
		railTicketTwoLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		railTicketTwoTextField = new JTextField();
		railTicketTwoTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		railTicketTwoTextField.setEnabled(false);
		railTicketTwoTextField.setText("0.00");
		railTicketTwoTextField.setToolTipText("Betrag für die Hin- und Rückfahrt der DB AG");
		railTicketTwoTextField.setColumns(10);
		
		railTicketMonthLabel = new JLabel("Monatsfahrkarte");
		railTicketMonthLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		railTicketMonthTextField = new JTextField();
		railTicketMonthTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		railTicketMonthTextField.setEnabled(false);
		railTicketMonthTextField.setText("0.00");
		railTicketMonthTextField.setColumns(10);
		
		hotelCostsLabel = new JLabel("Hotelkosten");
		hotelCostsLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		hotelCostsTextField = new JTextField();
		hotelCostsTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		hotelCostsTextField.setEnabled(false);
		hotelCostsTextField.setText("0.00");
		hotelCostsTextField.setColumns(10);
		
		flightTicketLabel = new JLabel("Flugticket");
		flightTicketLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		flightTicketTextField = new JTextField();
		flightTicketTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		flightTicketTextField.setEnabled(false);
		flightTicketTextField.setText("0.00");
		flightTicketTextField.setColumns(10);
		
		eur1Label = new JLabel("EUR");		
		eur1Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		eur2Label = new JLabel("EUR");		
		eur2Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		eur3Label = new JLabel("EUR");		
		eur3Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		eur4Label = new JLabel("EUR");
		eur4Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JButton addButton = new JButton("Hinzufügen");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator writer = new XMLCreator();
				//if (cityTextField.getText().equals(true)) {
					Parser search = new Parser();
					try {
						writer.writeXML(cityTextField.getText(), railTicketMonthTextField.getText(), railTicketTwoTextField.getText(), hotelCostsTextField.getText(), flightTicketTextField.getText());
						resultLabel.setVisible(true);
					} catch (XPathExpressionException | SAXException
							| ParserConfigurationException | JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			/*	} else {
					JOptionPane.showMessageDialog(null, "Bitte den Ort des Projektes angeben!");
				}*/
				reset();
			}
		});
		addButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JButton clearButton = new JButton("Löschen");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				resultLabel.setVisible(false);
			}
		});
		clearButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		final JButton closeButton = new JButton("Beenden");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Container frame = closeButton.getParent();
				do
					frame = frame.getParent();
				while (!(frame instanceof JFrame));
				((JFrame) frame).dispose();
			}
		});
		closeButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		resultLabel = new JLabel("Erfolgreich angelegt!");
		resultLabel.setVisible(false);
		resultLabel.setForeground(new Color(0, 0, 205));
		resultLabel.setFont(new Font("Dialog", Font.BOLD, 11));
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
								.addComponent(flightTicketLabel)
								.addComponent(cityLabel)
								.addComponent(resultLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(flightTicketTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(hotelCostsTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(railTicketMonthTextField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(railTicketTwoTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(eur4Label)
										.addComponent(eur3Label)
										.addComponent(eur2Label)
										.addComponent(eur1Label)))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(searchButton)
									.addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(addButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clearButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(closeButton)))
					.addContainerGap(27, Short.MAX_VALUE))
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(flightTicketTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(eur4Label)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(flightTicketLabel)
							.addGap(18)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(addButton)
						.addComponent(clearButton)
						.addComponent(closeButton))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

	protected void reset() {
		cityTextField.setText("");
		railTicketTwoTextField.setEnabled(false);
		railTicketTwoTextField.setText("0.00");
		railTicketMonthTextField.setEnabled(false);
		railTicketMonthTextField.setText("0.00");
		hotelCostsTextField.setEnabled(false);
		hotelCostsTextField.setText("0.00");
		flightTicketTextField.setEnabled(false);
		flightTicketTextField.setText("0.00");
	}
}

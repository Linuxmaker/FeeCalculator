/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class ChangeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField RailTicketNormalTextField;
	private JTextField HotelCostTextField;
	private JTextField FlightTicketTextField;
	private String city;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String city = args[0];
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeFrame frame = new ChangeFrame();
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
	public ChangeFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangeFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
		setTitle("Ändern der Inhalte");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 322, 240);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel CityLabel = new JLabel("Projekt-Stadt");
		CityLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JComboBox CityComboBox = new JComboBox();
		CityComboBox.setModel(new DefaultComboBoxModel(new String[] {"Stuttgart", "Ulm", "Ingolstadt"}));
		CityComboBox.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel RailTicketNormalLabel = new JLabel("Hin-/Rückfahrt-Ticket");
		RailTicketNormalLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		RailTicketNormalTextField = new JTextField();
		RailTicketNormalTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		RailTicketNormalTextField.setColumns(10);
		RailTicketNormalTextField.setText((String) city);
		
		JLabel RailTicketMonthLabel = new JLabel("Monatsticket");
		RailTicketMonthLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel HotelCostLabel = new JLabel("Hotelkosten");
		HotelCostLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		HotelCostTextField = new JTextField();
		HotelCostTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		HotelCostTextField.setColumns(10);
		
		JLabel FlightTicketLabel = new JLabel("Flugticket");
		FlightTicketLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		FlightTicketTextField = new JTextField();
		FlightTicketTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		FlightTicketTextField.setColumns(10);
		
		JLabel cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel cur2Label = new JLabel("EUR");
		cur2Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel cur3Label = new JLabel("EUR");
		cur3Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel cur4Label = new JLabel("EUR");
		cur4Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JButton ChangeButton = new JButton("Ändern");
		ChangeButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JButton DeleteButton = new JButton("Löschen");
		DeleteButton.setToolTipText("Löschen des Eintrages");
		DeleteButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JButton ExitButton = new JButton("Beenden");
		ExitButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JFormattedTextField RailTicketMonthFTextField = new JFormattedTextField(new DecimalFormat("#,##"));
		RailTicketMonthFTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(CityLabel)
							.addGap(18)
							.addComponent(CityComboBox, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(RailTicketNormalLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addComponent(HotelCostLabel)
								.addComponent(RailTicketMonthLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(RailTicketMonthFTextField)
								.addComponent(HotelCostTextField, 0, 0, Short.MAX_VALUE)
								.addComponent(RailTicketNormalTextField, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
								.addComponent(FlightTicketTextField, 0, 0, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cur3Label)
								.addComponent(cur1Label)
								.addComponent(cur2Label)
								.addComponent(cur4Label))))
					.addGap(107))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(FlightTicketLabel, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
					.addGap(213))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ChangeButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(DeleteButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ExitButton)
					.addGap(43))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(CityLabel)
						.addComponent(CityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(RailTicketNormalLabel)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(RailTicketNormalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cur1Label))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cur2Label)
								.addComponent(RailTicketMonthLabel)
								.addComponent(RailTicketMonthFTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(HotelCostTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cur3Label)
								.addComponent(HotelCostLabel))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(FlightTicketLabel)
							.addComponent(FlightTicketTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(cur4Label))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ChangeButton)
						.addComponent(DeleteButton)
						.addComponent(ExitButton))
					.addGap(31))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

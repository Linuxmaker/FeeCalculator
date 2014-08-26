/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import com.linuxmaker.calculator.ComboBoxModel;
import com.linuxmaker.calculator.XMLCreator;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class ChangeFrame extends JFrame implements ListDataListener, ActionListener  {

	private JPanel contentPane;
	private JFormattedTextField RailTicketMonthFTextField;
	private JTextField RailTicketNormalTextField;
	private JTextField HotelCostTextField;
	private JComboBox<String> targetCityComboBox;
	private ComboBoxModel myComboBoxModel;
	private XMLCreator element;

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
		
		targetCityComboBox = new JComboBox<String>();
		targetCityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				myComboBoxModel.reload();
				targetCityComboBox.setModel(myComboBoxModel);				
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
		
		element = new XMLCreator();
		
		JLabel RailTicketNormalLabel = new JLabel("Hin-/Rückfahrt-Ticket");
		RailTicketNormalLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		RailTicketNormalTextField = new JTextField();
		RailTicketNormalTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		RailTicketNormalTextField.setColumns(10);
		RailTicketNormalTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(4));
		
		JLabel RailTicketMonthLabel = new JLabel("Monatsticket");
		RailTicketMonthLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel HotelCostLabel = new JLabel("Hotelkosten");
		HotelCostLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		HotelCostTextField = new JTextField();
		HotelCostTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		HotelCostTextField.setColumns(10);
		HotelCostTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(5));
		
		JLabel cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel cur2Label = new JLabel("EUR");
		cur2Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel cur3Label = new JLabel("EUR");
		cur3Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JButton ChangeButton = new JButton("Ändern");
		ChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XMLCreator change = new XMLCreator();
				change.changeXML((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(0), RailTicketMonthFTextField.getText(), RailTicketNormalTextField.getText(), HotelCostTextField.getText());
			}

		});
		ChangeButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JButton DeleteButton = new JButton("Löschen");
		DeleteButton.setToolTipText("Löschen des Eintrages");
		DeleteButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
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
		
		RailTicketMonthFTextField = new JFormattedTextField(new DecimalFormat("#,##"));
		RailTicketMonthFTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		RailTicketMonthFTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(3));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(CityLabel)
							.addGap(18)
							.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(RailTicketNormalLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addComponent(HotelCostLabel)
								.addComponent(RailTicketMonthLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(RailTicketMonthFTextField)
								.addComponent(HotelCostTextField, 0, 0, Short.MAX_VALUE)
								.addComponent(RailTicketNormalTextField, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cur3Label)
								.addComponent(cur1Label)
								.addComponent(cur2Label))))
					.addGap(107))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ChangeButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(DeleteButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(closeButton)
					.addGap(43))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(CityLabel)
						.addComponent(targetCityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ChangeButton)
						.addComponent(DeleteButton)
						.addComponent(closeButton))
					.addGap(31))
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
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * ActionListener setzt die passenden Werte und setzt die Felder aktiv/inaktiv
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		RailTicketMonthFTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(3));
		RailTicketNormalTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(4));
		HotelCostTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(5));
	}
}

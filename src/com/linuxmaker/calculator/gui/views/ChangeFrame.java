/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.LayoutManager;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JCheckBox;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class ChangeFrame extends JFrame implements ListDataListener, ActionListener  {

	private JPanel contentPane;
	private JTextField RailTicketMonthFTextField;
	private JTextField RailTicketNormalTextField;
	private JTextField HotelCostTextField;
	private JComboBox<String> targetCityComboBox;
	private ComboBoxModel myComboBoxModel;
	private JCheckBox publicTransportCheckBox;
	private JLabel publicTransportValueLabel;
	private XMLCreator element;
	private JLabel cur1Label;
	private JLabel cur2Label;
	private JLabel cur3Label;
	private JLabel CityLabel;
	private JLabel RailTicketNormalLabel;
	private JLabel RailTicketMonthLabel;
	private JLabel HotelCostLabel;
	private JButton ChangeButton;
	private JButton DeleteButton;
	private JButton closeButton;
	

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
		initializeGuiObjects();
		contentPane.setLayout(createLayout(CityLabel, 
										   RailTicketNormalLabel, 
										   RailTicketMonthLabel,
										   HotelCostLabel,
										   cur1Label,
										   cur2Label,
										   cur3Label,
										   ChangeButton,
										   DeleteButton,
										   closeButton));
	}
	
	/**
	 * Initializes the GUI elements
	 */
	private void initializeGuiObjects(){
		CityLabel = new JLabel("Projekt-Stadt");
		CityLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		targetCityComboBox = new JComboBox<String>();
		targetCityComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					RailTicketMonthFTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(3).replace('.', ','));
					RailTicketNormalTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(4).replace('.', ','));
					HotelCostTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(5).replace('.', ','));
					publicTransportValueLabel.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(6));
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
		/*
		 * Creates ComboBoxModel
		 */
		myComboBoxModel = new ComboBoxModel();

		/*
		 * Sets ComboBoxModel
		 */
		myComboBoxModel.reload();
		targetCityComboBox.setModel(myComboBoxModel);
		
		element = new XMLCreator();
		
		RailTicketNormalLabel = new JLabel("Hin-/Rückfahrt-Ticket");
		RailTicketNormalLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		RailTicketNormalTextField = new JTextField();
		RailTicketNormalTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		RailTicketNormalTextField.setColumns(10);
		RailTicketNormalTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(4).replace('.', ','));
		
		RailTicketMonthLabel = new JLabel("Monatsticket");
		RailTicketMonthLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		HotelCostLabel = new JLabel("Hotelkosten");
		HotelCostLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		HotelCostTextField = new JTextField();
		HotelCostTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		HotelCostTextField.setColumns(10);
		HotelCostTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(5).replace('.', ','));
		
		publicTransportCheckBox = new JCheckBox("Liegt im ÖPNV");
		publicTransportCheckBox.setSelected(false);
		publicTransportCheckBox.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		publicTransportValueLabel = new JLabel();
		publicTransportValueLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		publicTransportValueLabel.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(6));
		
		cur1Label = new JLabel("EUR");
		cur1Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		cur2Label = new JLabel("EUR");
		cur2Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		cur3Label = new JLabel("EUR");
		cur3Label.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		ChangeButton = new JButton("Ändern");
		ChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XMLCreator change = new XMLCreator();
				change.changeXML(
						(String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(0), 
						RailTicketMonthFTextField.getText().replace(',', '.'), 
						RailTicketNormalTextField.getText().replace(',', '.'), 
						HotelCostTextField.getText().replace(',', '.'),
						String.valueOf(publicTransportCheckBox.isSelected())
						);
			}

		});
		ChangeButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		DeleteButton = new JButton("Löschen");
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XMLCreator delete = new XMLCreator();
				delete.removeElement((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(0));
			}
		});
		DeleteButton.setToolTipText("Löschen des Eintrages");
		DeleteButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
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
		closeButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		RailTicketMonthFTextField = new JTextField();
		RailTicketMonthFTextField.setFont(new Font("Dialog", Font.PLAIN, 11));
		RailTicketMonthFTextField.setText((String) element.readXML((String) targetCityComboBox.getSelectedItem()).get(3));
	}
	
	/**
	 * Creates the GUI layout
	 */
	private LayoutManager createLayout(JLabel CityLabel,
									   JLabel RailTicketNormalLabel,
									   JLabel RailTicketMonthLabel,
									   JLabel HotelCostLabel,JLabel cur1Label,
									   JLabel cur2Label,
									   JLabel cur3Label,JButton ChangeButton,
									   JButton DeleteButton,
									   JButton closeButton) {
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
								.addComponent(cur2Label)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(ChangeButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(DeleteButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(closeButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(publicTransportCheckBox)
							.addGap(18)
							.addComponent(publicTransportValueLabel)))
					.addGap(107))
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
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(publicTransportCheckBox)
						.addComponent(publicTransportValueLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ChangeButton)
						.addComponent(DeleteButton)
						.addComponent(closeButton))
					.addGap(31))
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

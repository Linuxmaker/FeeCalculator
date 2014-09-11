package com.linuxmaker.calculator.gui.views;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class AboutFrame extends JDialog {

   private static final long serialVersionUID = 1L;
   private JTextPane licenseJTextPane;
   private JScrollPane scroller;
   private JEditorPane editorPane;
   private JPanel contentPanel;
   private JButton licenseButton;
   private JButton okayButton;

   public AboutFrame(JFrame frame) {
	   super(frame, true);
	   getContentPane().setBackground(new Color(250, 250, 210));
	   setIconImage(Toolkit.getDefaultToolkit().getImage(AboutFrame.class.getResource("/com/linuxmaker/calculator/gui/resources/images16x16/currency_euro_yellow.png")));
	   try {
		   start();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }

   private void start() throws IOException {
	   setTitle("Über FeeCalculator");
	   setSize(450, 400);
	   setLocationRelativeTo(null);
	   getContentPane().setLayout(null);
	   setResizable(true);
	   initializeGui();
	   getContentPane().add(scroller);
	   getContentPane().add(okayButton);
	   getContentPane().add(contentPanel);
	   getContentPane().add(licenseButton);
	   getContentPane().add(editorPane);
   }
   
   private void initializeGui() throws IOException {
	   editorPane = new JEditorPane();
	   editorPane.setBackground(new Color(250, 250, 210));
	   editorPane.setBounds(5, 16, 435, 180);
	   editorPane.setPage("file:resources/about.html");
      
	   contentPanel = new JPanel();
	   contentPanel.setBackground(new Color(250, 250, 210));
	   contentPanel.setBounds(5, 160, 435, 160);
	   contentPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 128), 1, true), "License agreement", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 128)));
	   contentPanel.setVisible(false);
      
	   licenseJTextPane = new JTextPane();
	   licenseJTextPane.setForeground(new Color(0, 0, 128));
	   licenseJTextPane.setFont(new Font("Dialog", Font.PLAIN, 11));
	   licenseJTextPane.setEditable(false);
	   licenseJTextPane.setContentType("text/plain");
	   licenseJTextPane.setContentType("text/html");
	   licenseJTextPane.setPage("file:resources/license");
	   licenseJTextPane.setVisible(false);
      
	   scroller = new JScrollPane(licenseJTextPane);
	   scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	   scroller.setBounds(15, 180, 415, 120);
	   scroller.setVisible(false);
      
	   okayButton = new JButton("Ok");
	   okayButton.setBounds(357, 332, 75, 28);
	   okayButton.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   	Container frame = okayButton.getParent();
			   	do
				   frame = frame.getParent();
			   	while (!(frame instanceof JFrame));
			   	((JFrame) frame).dispose();
		   }
	   });
	   licenseButton = new JButton("Lizenz");
	   licenseButton.setBounds(15, 332, 100, 28);
	   licenseButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
      		contentPanel.setVisible(true);
      		scroller.setVisible(true);
      		licenseJTextPane.setVisible(true);
       }
      });
}

private static void createAndShowUI() {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      AboutFrame about = new AboutFrame(frame);
      about.setVisible(true);
      
      frame.dispose();
   }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}

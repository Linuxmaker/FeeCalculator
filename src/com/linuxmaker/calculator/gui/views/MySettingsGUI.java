/**
 * 
 */
package com.linuxmaker.calculator.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
class MySettingsGUI extends JFrame implements ActionListener {

    private JTextField tf_Name;
    
    public MySettingsGUI() {
        super.setTitle("Settings");
    }

    public void showForm() {
        // Laden von MySettings
        try {
            File file = new File("settings/default.txt");
            MySettings.load(file);
        } catch (Exception e) {
            System.out.println("Fehler beim Laden: " + e.getMessage());
        }

        // Komponenten des GUI
        JPanel contentPane = new JPanel();
        JLabel label1 = new JLabel("Name:");
        tf_Name = new JTextField(MySettings.getProperty("name"), 10);
        JButton b_Save = new JButton("Okay");
        b_Save.addActionListener(this);

        contentPane.add(label1);
        contentPane.add(tf_Name);
        contentPane.add(b_Save);

        this.setContentPane(contentPane);
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Okay")) {
            try {
                String name = tf_Name.getDocument().getText(0, tf_Name.getDocument().getLength());
                MySettings.putProperty("name", name);
            } catch (Exception e) {
                System.out.println("Fehler beim Auslesen des Wertes: " + e.getMessage());
            }
            
            try {
                MySettings.store();
            } catch (Exception e) {
                System.out.println("Fehler beim Speichern: " + e.getMessage());
            }
            this.setVisible(false);
        }
    }
}

class MySettings {

    private static final Properties props = new Properties();
    private static File file = null;

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static void putProperty(String key, String value) {
        props.put(key, value);
    }

    public static void load(File file) throws Exception {
        MySettings.file = file;
        FileInputStream fis = new FileInputStream(file);
        props.load(fis);
        fis.close();
    }

    public static void store() throws Exception {
        FileOutputStream fos = new FileOutputStream(MySettings.file);
        props.store(fos, "Comments for MySettings");
        fos.flush();
        fos.close();
    }
}

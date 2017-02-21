package com.vocabtester.application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;

public class Settings extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
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
	public Settings() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 363, 337);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(10, 46, 63, 14);
		contentPane.add(lblTime);

		final JSpinner spinner = new JSpinner();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("settings.properties");
			prop.load(input);
			// turn timer for app on
			spinner.setModel(new SpinnerNumberModel(new Integer(prop.getProperty("time")), 5, null, new Integer(1)));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException f) {
					f.printStackTrace();
				}
			}
		}
		spinner.setBounds(134, 43, 63, 20);
		contentPane.add(spinner);

		JLabel lblNewLabel = new JLabel("Rounds:");
		lblNewLabel.setBounds(10, 71, 63, 14);
		contentPane.add(lblNewLabel);

		final JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(134, 68, 63, 20);
		spinner_1.setModel(new SpinnerNumberModel(new Integer(prop.getProperty("round")), 0, null, new Integer(1)));

		contentPane.add(spinner_1);

		JButton btnSaveAndExit = new JButton("Save");
		btnSaveAndExit.setBackground(Color.LIGHT_GRAY);
		btnSaveAndExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				OutputStream output = null;
				try {
					output = new FileOutputStream("settings.properties");
					prop.setProperty("time", String.valueOf(spinner.getValue()));
					prop.setProperty("round", String.valueOf(spinner_1.getValue()));
					prop.store(output, null);
				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException f) {
							f.printStackTrace();
						}
					}

				}
				dispose();
			}
		});
		btnSaveAndExit.setBounds(257, 235, 80, 23);
		contentPane.add(btnSaveAndExit);

		JButton btnClose = new JButton("Cancel");
		btnClose.setBackground(Color.LIGHT_GRAY);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(257, 264, 80, 23);
		contentPane.add(btnClose);
	}
}

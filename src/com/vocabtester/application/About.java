package com.vocabtester.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.awt.SystemColor;

public class About extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			About dialog = new About();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public About() {
		setBounds(100, 100, 494, 391);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JEditorPane dtrpnVocabTester = new JEditorPane();
		dtrpnVocabTester.setBackground(SystemColor.menu);
		dtrpnVocabTester.setForeground(Color.BLACK);
		dtrpnVocabTester.setEditable(false);
		dtrpnVocabTester.setText("Vocab Tester 2017 is created by Brian Naik.\r\n\r\nVocab Tester allows you to test your knowledge of certain vocabulary over and over till you master it. \r\n\r\nThe idea was created so the creator could work on his own knowledge of IT terminology, but then he decided to make it for general use to help others as well.\r\n\r\nThis product is the demo version which contains two IT vocabulary lists (Java/Puppet)\r\n\r\nThe full version allows you to add/delete your own vocabulary lists.");
		dtrpnVocabTester.setBounds(10, 26, 458, 265);
		contentPanel.add(dtrpnVocabTester);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}

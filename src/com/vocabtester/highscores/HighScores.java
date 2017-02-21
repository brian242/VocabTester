package com.vocabtester.highscores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class HighScores extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HighScores dialog = new HighScores(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public HighScores(List<Person> scores) {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("High Scores");
		setBounds(100, 100, 445, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		/*
		 * final JTextArea txtrKk = new JTextArea();
		 * 
		 * txtrKk.setForeground(Color.WHITE); txtrKk.setFont(new Font(
		 * "Lucida Sans Typewriter", Font.PLAIN, 20));
		 * txtrKk.setBackground(Color.BLACK); txtrKk.setEditable(false); for
		 * (int i = 0; i < scores.size(); i++) { if (i == 0) {
		 * txtrKk.append(String.valueOf(scores.get(i))); } else {
		 * txtrKk.append("\n\n" + String.valueOf(scores.get(i))); } }
		 * txtrKk.setBounds(82, 64, 275, 275); contentPanel.add(txtrKk);
		 */

		final JTable table = new JTable();
		table.setShowGrid(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel dtm = new DefaultTableModel(0, 0);
		String header[] = new String[] { "Place", "Name", "Score" };
		dtm.setColumnIdentifiers(header);

		for (int i = 0; i < scores.size(); i++) {
			dtm.addRow(new Object[] { (i + 1) + ".", scores.get(i).getName(), String.valueOf(scores.get(i).getScore()) });
		}
		table.setModel(dtm);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setForeground(new Color(255, 255, 255));
		table.setBounds(20, 64, 399, 275);
		table.setBackground(new Color(0, 0, 0));

		contentPanel.add(table);

		JLabel lblHighScores = new JLabel("HIGH SCORES");
		lblHighScores.setHorizontalAlignment(SwingConstants.CENTER);
		lblHighScores.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 31));
		lblHighScores.setForeground(new Color(255, 255, 0));
		lblHighScores.setBounds(10, 11, 409, 42);
		contentPanel.add(lblHighScores);
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

				JButton btnClear = new JButton("CLEAR");
				btnClear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dtm.setRowCount(0);
						try {
							PrintWriter writer = new PrintWriter("scores.txt");
							writer.print("");
							writer.close();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}

					}
				});
				buttonPane.add(btnClear);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
package com.vocabtester.application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.vocabtester.bean.Word;
import com.vocabtester.dao.WordsDao;
import com.vocabtester.highscores.HighScores;
import com.vocabtester.highscores.Person;

public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnFinish;
	private JTextField[] choiceText = new JTextField[4];
	private JButton[] choiceButtons = new JButton[4];
	private boolean[] choiceClick = new boolean[4];
	private JComboBox<String> comboBox;
	private File correct = new File("correct.wav");
	private File fail = new File("fail.wav");
	private Timer t;
	private Timer timer;
	private JTextField txtFieldScore;
	private JTextField txtFieldTimer;
	private JTextArea textArea;
	private int timerVal = 0;
	private Integer score = 0;
	private Integer maxTime;
	private JMenu mnSettings;

	// Allow these to be changed in settings
	private Integer round = 0;
	// maxTime is set to 20 seconds below in the code, allow this to be changed
	// in settings too using DAO

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setTitle("Vocab Tester");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 491, 465);

		JMenuBar menuBar_1 = new JMenuBar();
		setJMenuBar(menuBar_1);

		JMenu mnFile = new JMenu("File");
		menuBar_1.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		mnSettings = new JMenu("Settings");
		menuBar_1.add(mnSettings);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings settings = new Settings();
				settings.setVisible(true);
			}
		});

		JMenuItem mntmHighScores = new JMenuItem("High Scores");
		mnSettings.add(mntmHighScores);
		mntmHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				highScoreStuff();
			}
		});
		mnSettings.add(mntmSettings);

		JMenu mnHelp = new JMenu("Help");
		menuBar_1.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About about = new About();
				about.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		int y = 202;
		for (int i = 0; i < 4; i++) {
			choiceText[i] = new JTextField();
			choiceText[i].setEditable(false);
			choiceText[i].setBounds(96, y, 232, 40);
			contentPane.add(choiceText[i]);
			choiceText[i].setColumns(10);
			y += 51;
		}

		choiceButtons[0] = new JButton("A");
		choiceButtons[0].setEnabled(false);
		choiceButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBtnsDisabled();
				if (choiceClick[0]) {
					playSound(correct);
					choiceText[0].setBackground(Color.green);
					score += 10;
					txtFieldScore.setText(score.toString());
				} else {
					playSound(fail);
					choiceText[0].setBackground(Color.red);
					if (score > 0) {
						score -= 5;
					}
					txtFieldScore.setText(score.toString());
					showCorrectAnswer();
				}
				delayBeforeNextRound();
			}
		});
		choiceButtons[0].setBackground(SystemColor.control);
		choiceButtons[0].setBounds(10, 201, 76, 40);
		contentPane.add(choiceButtons[0]);

		choiceButtons[1] = new JButton("B");
		choiceButtons[1].setEnabled(false);
		choiceButtons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBtnsDisabled();
				if (choiceClick[1]) {
					playSound(correct);
					choiceText[1].setBackground(Color.green);
					score += 10;
					txtFieldScore.setText(score.toString());
				} else {
					playSound(fail);
					choiceText[1].setBackground(Color.red);
					if (score > 0) {
						score -= 5;
					}
					txtFieldScore.setText(score.toString());
					showCorrectAnswer();
				}
				delayBeforeNextRound();
			}
		});
		choiceButtons[1].setBackground(SystemColor.control);
		choiceButtons[1].setBounds(10, 252, 76, 40);
		contentPane.add(choiceButtons[1]);

		choiceButtons[2] = new JButton("C");
		choiceButtons[2].setEnabled(false);
		choiceButtons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBtnsDisabled();
				if (choiceClick[2]) {
					playSound(correct);
					choiceText[2].setBackground(Color.green);
					score += 10;
					txtFieldScore.setText(score.toString());
				} else {
					playSound(fail);
					choiceText[2].setBackground(Color.red);
					if (score > 0) {
						score -= 5;
					}
					txtFieldScore.setText(score.toString());
					showCorrectAnswer();
				}
				delayBeforeNextRound();
			}
		});
		choiceButtons[2].setBackground(SystemColor.control);
		choiceButtons[2].setBounds(10, 303, 76, 40);
		contentPane.add(choiceButtons[2]);

		choiceButtons[3] = new JButton("D");
		choiceButtons[3].setEnabled(false);
		choiceButtons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBtnsDisabled();
				if (choiceClick[3]) {
					playSound(correct);
					choiceText[3].setBackground(Color.green);
					score += 10;
					txtFieldScore.setText(score.toString());

				} else {
					playSound(fail);
					choiceText[3].setBackground(Color.red);
					if (score > 0) {
						score -= 5;
					}
					txtFieldScore.setText(score.toString());
					showCorrectAnswer();
				}
				delayBeforeNextRound();
			}
		});
		choiceButtons[3].setBackground(SystemColor.control);
		choiceButtons[3].setBounds(10, 354, 76, 40);
		contentPane.add(choiceButtons[3]);

		txtFieldTimer = new JTextField();
		txtFieldTimer.setBackground(SystemColor.activeCaption);
		txtFieldTimer.setHorizontalAlignment(SwingConstants.CENTER);
		txtFieldTimer.setText(String.valueOf(timerVal));
		txtFieldTimer.setFont(new Font("Tahoma", Font.BOLD, 35));
		txtFieldTimer.setBounds(372, 312, 86, 82);
		contentPane.add(txtFieldTimer);
		txtFieldTimer.setColumns(10);

		textArea = new JTextArea();
		textArea.setForeground(new Color(0, 0, 128));
		textArea.setBackground(SystemColor.activeCaption);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(10, 71, 448, 93);
		contentPane.add(textArea);

		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBtnsDisabled();
				btnFinish.setEnabled(true);
				t = new Timer(500 * 1, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Properties prop = new Properties();
						InputStream input = null;
						try {
							input = new FileInputStream("settings.properties");
							prop.load(input);
							// turn timer for app on
							round = Integer.parseInt(prop.getProperty("round"));
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
						startRound();
					}
				});
				t.start();
			}
		});
		btnStart.setBackground(new Color(0, 204, 0));
		btnStart.setBounds(372, 202, 86, 39);
		contentPane.add(btnStart);

		btnFinish = new JButton("FINISH");
		btnFinish.setEnabled(false);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				finishTheApp();

			}
		});
		btnFinish.setBackground(new Color(204, 0, 0));
		btnFinish.setBounds(372, 252, 89, 40);
		contentPane.add(btnFinish);

		comboBox = new JComboBox();
		comboBox.setBounds(10, 171, 76, 20);
		contentPane.add(comboBox);
		comboBox.addItem("Java");
		comboBox.addItem("Puppet");

		JLabel lblVocabTester = new JLabel("Vocab Tester!");
		lblVocabTester.setForeground(SystemColor.textHighlight);
		lblVocabTester.setBackground(SystemColor.textHighlight);
		lblVocabTester.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 28));
		lblVocabTester.setBounds(10, 11, 216, 26);
		contentPane.add(lblVocabTester);

		txtFieldScore = new JTextField();
		txtFieldScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtFieldScore.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtFieldScore.setBackground(Color.YELLOW);
		txtFieldScore.setEditable(false);
		txtFieldScore.setText("0");
		txtFieldScore.setBounds(413, 21, 45, 39);
		contentPane.add(txtFieldScore);
		txtFieldScore.setColumns(10);

		JLabel lblScore = new JLabel("Score:");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblScore.setBounds(358, 33, 45, 14);
		contentPane.add(lblScore);
	}

	public void startRound() {

		btnStart.setEnabled(false);
		mnSettings.setEnabled(false);
		comboBox.setEnabled(false);
		if (t.isRunning()) {
			t.stop();
		}
		choiceText[0].setBackground(Color.white);
		choiceText[1].setBackground(Color.white);
		choiceText[2].setBackground(Color.white);
		choiceText[3].setBackground(Color.white);
		setBtnsEnabled();
		choiceClick[0] = false;
		choiceClick[1] = false;
		choiceClick[2] = false;
		choiceClick[3] = false;

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("settings.properties");
			prop.load(input);
			// turn timer for app on
			maxTime = Integer.parseInt(prop.getProperty("time"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFieldTimer.setText(maxTime.toString());
				if (maxTime > 0) {
					maxTime -= 1;
				} else {
					for (int i = 0; i < 4; i++) {
						if (choiceClick[i]) {
							choiceText[i].setBackground(Color.green);
						} else {
							choiceText[i].setBackground(Color.red);
						}
					}
					setBtnsDisabled();
					playSound(fail);
					delayBeforeNextRound();
				}
			}
		});
		timer.start();

		WordsDao dao = new WordsDao();

		String word1, word2, word3, word4 = null;

		String vocabWords = "";

		if (comboBox.getSelectedItem().equals("Java")) {
			vocabWords = "javawords";

		} else if (comboBox.getSelectedItem().equals("Puppet")) {
			vocabWords = "puppetwords";

		} else {
			System.out.println("some other words!");
		}

		// gets max id of vocab list
		int sizeOfList = dao.getAllWords(vocabWords).size();

		// select random ids for the 4 textfields
		Random rand = new Random();
		int id1 = rand.nextInt(sizeOfList) + 1;
		int id2 = rand.nextInt(sizeOfList) + 1;
		int id3 = rand.nextInt(sizeOfList) + 1;
		int id4 = rand.nextInt(sizeOfList) + 1;

		// make sure that no ids match each other (otherwise their will be
		// two
		// of the same words in two differeen fields)
		while (id4 == id3 || id4 == id2 || id4 == id1) {
			id4 = rand.nextInt(sizeOfList) + 1;
		}
		while (id3 == id4 || id3 == id2 || id3 == id1) {
			id3 = rand.nextInt(sizeOfList) + 1;
		}
		while (id2 == id4 || id2 == id3 || id2 == id1) {
			id2 = rand.nextInt(sizeOfList) + 1;
		}
		while (id1 == id4 || id1 == id3 || id1 == id2) {
			id1 = rand.nextInt(sizeOfList) + 1;
		}

		// nneed to -1 after ids, because the ids are one ahead due to the 0
		// value
		word1 = dao.getAllWords(vocabWords).get(id1 - 1).getWord();
		word2 = dao.getAllWords(vocabWords).get(id2 - 1).getWord();
		word3 = dao.getAllWords(vocabWords).get(id3 - 1).getWord();
		word4 = dao.getAllWords(vocabWords).get(id4 - 1).getWord();

		// Make an array of the 4 words, and choose one as the correct one
		int[] arr = { id1, id2, id3, id4 };
		Random random = new Random();
		int correctId = random.nextInt(arr.length);

		// get desc of correct word
		Word desc = dao.getWordById(arr[correctId], vocabWords);

		textArea.setText(desc.getDescription());
		choiceText[0].setText(word1);
		choiceText[1].setText(word2);
		choiceText[2].setText(word3);
		choiceText[3].setText(word4);

		/// if an id of a word is the correct one
		// then make appropriate btn (one next to it) correct if clicked
		if (arr[correctId] == id1) {
			choiceClick[0] = true;
		}
		if (arr[correctId] == id2) {
			choiceClick[1] = true;
		}
		if (arr[correctId] == id3) {
			choiceClick[2] = true;
		}
		if (arr[correctId] == id4) {
			choiceClick[3] = true;
		}

	}

	void delayBeforeNextRound() {
		if (round <= 0) {
			finishTheApp();
		} else {
			round -= 1;
			t = new Timer(1000 * 2, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startRound();
				}
			});
			t.start();
		}
	}

	void setBtnsEnabled() {
		for (JButton jButton : choiceButtons) {
			jButton.setEnabled(true);
		}
	}

	void setBtnsDisabled() {
		for (JButton jButton : choiceButtons) {
			jButton.setEnabled(false);
		}
	}

	void playSound(File sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.stop();
	}

	void showCorrectAnswer() {
		for (int i = 0; i < 4; i++) {
			if (choiceClick[i]) {
				choiceText[i].setBackground(Color.green);
			}
		}
	}

	void finishTheApp() {
		t.stop();
		timer.stop();
		btnStart.setEnabled(true);
		btnFinish.setEnabled(false);
		mnSettings.setEnabled(true);
		comboBox.setEnabled(true);
		setBtnsDisabled();
		txtFieldTimer.setText("0");
		textArea.setText("");
		for (int i = 0; i < 4; i++) {
			choiceText[i].setText("");
			choiceText[i].setBackground(Color.getColor("240, 240, 240"));
		}
		showHighScores(score);
		score = 0;
		txtFieldScore.setText(score.toString());
	}

	void showHighScores(int score) {

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(rootPane, panel, "Game over", JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		String name = "Undefined";
		if (selectedOption == 0) {
			if (!txt.getText().equals("")) {
				name = txt.getText();
			}
		}

		// append score and name to txt/data file
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt", true)))) {
			out.println(name + ":" + score);
		} catch (IOException e) {
			System.err.println(e);
		}

		highScoreStuff();
	}

	private void highScoreStuff() {
		// convert txt/data file to List<String>
		Scanner s = null;
		try {
			s = new Scanner(new File("scores.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<String> stringArray = new ArrayList<String>();
		while (s.hasNext()) {
			stringArray.add(s.nextLine());
		}
		s.close();

		// convert List<String> to List<Person> call it "scores"
		List<Person> scores = new ArrayList<Person>();
		String theName = null;
		Integer theScore = null;

		String fieldSeparator = ":";
		for (String item : stringArray) {
			String[] parts = item.split(Pattern.quote(fieldSeparator));
			theName = parts[0];
			theScore = Integer.parseInt(parts[1]);

			scores.add(new Person(theName, theScore));
		}

		Collections.sort(scores, Collections.reverseOrder(new Person.ScoreComparator()));
		HighScores highScores = new HighScores(scores);
		highScores.setVisible(true);
	}
}

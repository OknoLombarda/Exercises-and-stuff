package me.hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import me.hangman.HangmanUtils;

public class HangmanFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	private ArrayDeque<JLabel> ansLetters = new ArrayDeque<>();
	private String currentWord;
	private int check;
	
	private JPanel answerPanel;
	private JButton[] letterButtons;
	private HangmanPanel hangman;
	
	public HangmanFrame() {
		super("Hangman");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.BLACK);
		
		answerPanel = new JPanel();
		answerPanel.setOpaque(false);
		
		JPanel alphabet = new JPanel();
		alphabet.setPreferredSize(new Dimension(270, 230));
		alphabet.setOpaque(false);
		letterButtons = new JButton[26];
		char c = 'A';
		LetterAction action;
		MouseHandler handler;
		InputMap imap = alphabet.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = alphabet.getActionMap();
		for (int i = 0; i < letterButtons.length; i++) {
			letterButtons[i] = new JButton("<html><p color=\"white\">".concat(String.valueOf(c)).concat("</p></html>"));
			letterButtons[i].setBackground(Color.BLACK);
			letterButtons[i].setFont(new Font(Font.SERIF, Font.BOLD, 30));
			letterButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			letterButtons[i].setPreferredSize(new Dimension(40, 40));
			letterButtons[i].setFocusPainted(false);
			handler = new MouseHandler(letterButtons[i]);
			letterButtons[i].addMouseListener(handler);
			action = new LetterAction(letterButtons[i], c);
			letterButtons[i].addActionListener(action);
			imap.put(KeyStroke.getKeyStroke(c), String.valueOf(c));
			amap.put(String.valueOf(c), action);
			alphabet.add(letterButtons[i]);
			c = (char) (c + 1);
		}
		
		hangman = new HangmanPanel();
		
		add(answerPanel, new GBC(0, 0, 2, 1).setInsets(50, 0, 0, 0).setAnchor(GBC.NORTH).setWeight(0, 100));
		add(alphabet, new GBC(0, 1, 1, 1).setInsets(0, 70, 0, 0).setAnchor(GBC.NORTHWEST).setWeight(100, 100));
		add(hangman, new GBC(1, 1, 1, 1).setAnchor(GBC.NORTHWEST).setWeight(100, 0));
		
		updateWord();
	}
	
	private void updateWord() {
		check = 0;
		hangman.reset();
		currentWord = HangmanUtils.getWord();
		
		for (JButton b : letterButtons) {
			b.setEnabled(true);
			b.setBackground(Color.BLACK);
		}
		
		while (ansLetters.size() < currentWord.length()) {
			JLabel l = new JLabel();
			l.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			l.setOpaque(false);
			l.setPreferredSize(new Dimension(40, 50));
			ansLetters.add(l);
			answerPanel.add(l);
		}
		while (ansLetters.size() > currentWord.length()) {
			answerPanel.remove(ansLetters.removeLast());
		}
		
		ansLetters.forEach(l -> l.setText("<html><p color=\"white\">_</p></html>"));
	}
	
	private class LetterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LetterAction(JButton button, char letter) {
			putValue("button", button);
			putValue("letter", letter);
		}
		
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton) getValue("button");
			button.setBackground(Color.WHITE);
			button.setEnabled(false);
			char letter = (char) getValue("letter");
			String upperCase = currentWord.toUpperCase();
			if (upperCase.contains(String.valueOf(letter))) {
				Iterator<JLabel> iter = ansLetters.iterator();
				for (int i = 0; i < ansLetters.size(); i++) {
					JLabel l = iter.next();
					if (upperCase.charAt(i) == letter) {
						String text = l.getText();
						l.setText(text.substring(0, text.indexOf("_")).concat(String.valueOf(currentWord.charAt(i)))
								.concat(text.substring(text.indexOf("_") + 1)));
						check++;
					}
				}
				if (check == currentWord.length()) {
					JOptionPane.showMessageDialog(null, "You win");
					updateWord();
				}
			} else {
				hangman.drawNext();
				if (hangman.getDrawnCount() > 8) {
					JOptionPane.showMessageDialog(null, "You lose");
					updateWord();
				}
			}
		}
	}
	
	private class MouseHandler extends MouseAdapter {
		private JButton button;
		
		public MouseHandler(JButton button) {
			this.button = button;
		}
		
		public void mouseEntered(MouseEvent event) {
			if (button.isEnabled())
				button.setBackground(Color.DARK_GRAY);
		}
		
		public void mouseExited(MouseEvent event) {
			if (button.isEnabled())
				button.setBackground(Color.BLACK);
		}
	}
}

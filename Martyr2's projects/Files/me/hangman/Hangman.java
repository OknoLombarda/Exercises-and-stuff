package me.hangman;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.hangman.gui.HangmanFrame;

public class Hangman {
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		HangmanUtils.readData();
		EventQueue.invokeLater(() -> {
			HangmanFrame frame = new HangmanFrame();
			frame.setVisible(true);
		});
	}
}

package me.books;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import me.books.gui.BMFrame;

public class BookManager {
	public static void main(String[] args) {
		try {
			BMTools.initializeFiles();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		try {
			BMTools.loadSave();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Save file not found", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		EventQueue.invokeLater(() -> {
			BMFrame mainFrame = new BMFrame();
			mainFrame.setVisible(true);
		});
	}
}

package me.books.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import me.books.BMTools;

public class BMFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton left;
	private JButton right;
	private JButton delete;
	private JLabel position;
	private ReplaceDialog repl;
	
	public BMFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		JMenuBar menuBar = new JMenuBar();
		
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic('E');
		JMenuItem replace = new JMenuItem("Replace");
		replace.setMnemonic('R');
		replace.addActionListener(event -> {
			if (repl == null)
				repl = new ReplaceDialog(this);
			repl.showDialog();
			if (repl.hasAccess()) {
				String forr = repl.getForr();
				String replacement = repl.getRepl();
				if (!forr.isBlank() && !replacement.isBlank()) {
					try {
						BMTools.replace(forr, replacement);
					}
					catch (IOException e) {
						showError();
					}
					getCurrentBook();
				}
				else if (!replacement.isBlank())
					JOptionPane.showMessageDialog(this, "Wrong input", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		replace.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		edit.add(replace);
		
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic('E');
		exit.addActionListener(event -> System.exit(0));
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic('S');
		save.addActionListener(event -> {
			try {
				BMTools.printSave();
			}
			catch (FileNotFoundException e) {
				showError();
			}
		});
		save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		JMenuItem backup = new JMenuItem("Backup files");
		backup.setMnemonic('B');
		backup.addActionListener(event -> {
			try {
				BMTools.backupFiles();
			}
			catch(IOException e) {
				showError();
			}
		});
		JMenuItem loadFromBackup = new JMenuItem("Load from backup");
		loadFromBackup.setMnemonic('L');
		loadFromBackup.addActionListener(event -> {
			try {
				BMTools.loadFromBackup();
				getCurrentBook();
			} catch (IOException e) {
				showError();
			}
		});
		file.add(save);
		file.addSeparator();
		file.add(backup);
		file.add(loadFromBackup);
		file.addSeparator();
		file.add(exit);
		
		menuBar.add(file);
		menuBar.add(edit);
		setJMenuBar(menuBar);

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		JScrollPane scrollPane = new JScrollPane(textArea);
	
		left = new JButton("<");
		right = new JButton(">");
		delete = new JButton("Delete");
		
		ActionListener previous = event -> {
			String text = "";
			try {
				text = BMTools.getPrevious();
			}
			catch(IOException e) {
				showError();
			}
			textArea.setText(text);
			left.setEnabled(BMTools.hasPrevious());
			right.setEnabled(BMTools.hasNext());
			updatePosition();
		};
		left.addActionListener(previous);
		left.setMnemonic(KeyEvent.VK_LEFT);
		
		ActionListener next = event -> {
			String text = "";
			try {
				text = BMTools.getNext();
			}
			catch(IOException e) {
				showError();
			}
			textArea.setText(text);
			left.setEnabled(BMTools.hasPrevious());
			right.setEnabled(BMTools.hasNext());
			updatePosition();
		};
		right.addActionListener(next);
		right.setMnemonic(KeyEvent.VK_RIGHT);
		
		delete.addActionListener(event -> {
			BMTools.delete();
			if (BMTools.hasPrevious())
				previous.actionPerformed(event);
			else {
				getCurrentBook();
				left.setEnabled(BMTools.hasPrevious());
				right.setEnabled(BMTools.hasNext());
			}
			updatePosition();
			delete.setEnabled(BMTools.amount() != 0);
		});
		SwingUtilities.getRootPane(this).setDefaultButton(delete);
		
		JPanel buttonPanel = new JPanel();
		GridBagLayout panelLayout = new GridBagLayout();
		buttonPanel.setLayout(panelLayout);
		
		buttonPanel.add(left, new GBC(0, 0).setAnchor(GBC.EAST).setInsets(10, 10, 0, 0).setWeight(100, 0));
		buttonPanel.add(delete, new GBC(1, 0).setAnchor(GBC.CENTER).setInsets(10, 20, 0, 0).setWeight(100, 0));
		buttonPanel.add(right, new GBC(2, 0).setAnchor(GBC.WEST).setInsets(10, 20, 0, 10).setWeight(100, 0));
		
		position = new JLabel();
		getCurrentBook();

		add(buttonPanel, new GBC(1, 0).setInsets(0).setAnchor(GBC.CENTER).setWeight(100, 0));
		add(scrollPane, new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setInsets(10, 0, 0, 0).setAnchor(GBC.CENTER).setWeight(0, 100));
		add(position, new GBC(1, 2).setInsets(10, 0, 10, 0).setAnchor(GBC.CENTER).setWeight(0, 0));
	}

	private void getCurrentBook() {
		try {
			textArea.setText(BMTools.getLastPrinted());
		}
		catch(IOException e) {
			showError();
		}
		left.setEnabled(BMTools.hasPrevious());
		right.setEnabled(BMTools.hasNext());
		updatePosition();
	}

	private void showError() {
		JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(ERROR);
	}
	
	private void updatePosition() {
		position.setText(String.valueOf(BMTools.getIter() + 1).concat("/").concat(String.valueOf(BMTools.amount())));
	}
}

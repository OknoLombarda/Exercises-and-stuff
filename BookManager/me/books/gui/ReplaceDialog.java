package me.books.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ReplaceDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int COLUMNS = 30;
	private JLabel l1;
	private JLabel l2;
	private JTextField forr;
	private JTextField repl;
	private JButton replace;
	private JButton cancel;
	private boolean isOk;
	
	public ReplaceDialog(Frame parent) {
		super(parent, "Replace", true);
		setSize(250, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		l1 = new JLabel("Replace:");
		forr = new JTextField(COLUMNS);
		l2 = new JLabel("with:");
		repl = new JTextField(COLUMNS);
		replace = new JButton("Replace");
		cancel = new JButton("Cancel");
		
		replace.addActionListener(event -> {
			isOk = true;
			setVisible(false);
		});
		cancel.addActionListener(event -> setVisible(false));
		SwingUtilities.getRootPane(this).setDefaultButton(replace);
		
		add(l1, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(5).setWeight(0, 0));
		add(forr, new GBC(0, 1, 2, 1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(5).setWeight(0, 0));
		add(l2, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5).setWeight(0, 0));
		add(repl, new GBC(0, 3, 2, 1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(5).setWeight(0, 0));
		add(replace, new GBC(0, 4).setAnchor(GBC.WEST).setInsets(10, 5, 5, 10).setWeight(0, 0));
		add(cancel, new GBC(1, 4).setAnchor(GBC.EAST).setInsets(10, 5, 5, 5).setWeight(0, 0));
	}
	
	public void showDialog() {
		isOk = false;
		setVisible(true);
	}
	
	public boolean hasAccess() {
		return isOk;
	}
	
	public String getForr() {
		return forr.getText();
	}
	
	public String getRepl() {
		return repl.getText();
	}
}

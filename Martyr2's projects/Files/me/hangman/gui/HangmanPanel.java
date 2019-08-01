package me.hangman.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class HangmanPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<Shape> parts = new ArrayList<>();
	private int available = 0;
	
	public HangmanPanel() {
		super();
		parts.add(new Line2D.Double(10, 10, 10, 300));
		parts.add(new Line2D.Double(10, 10, 150, 10));
		parts.add(new Line2D.Double(150, 10, 150, 60));
		parts.add(new Ellipse2D.Double(130, 60, 40, 40));
		parts.add(new Line2D.Double(150, 100, 150, 170));
		parts.add(new Line2D.Double(150, 100, 130, 140));
		parts.add(new Line2D.Double(150, 100, 170, 140));
		parts.add(new Line2D.Double(150, 170, 130, 220));
		parts.add(new Line2D.Double(150, 170, 170, 220));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(4));
		
		for (int i = 0; i < available; i++) {
			g2.draw(parts.get(i));
		}
	}
	
	public void drawNext() {
		available++;
		repaint();
	}
	
	public int getDrawnCount() {
		return available;
	}
	
	public void reset() {
		available = 0;
		repaint();
	}
	
	public Dimension preferredSize() {
		return new Dimension(200, 320);
	}
}

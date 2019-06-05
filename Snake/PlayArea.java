package me.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class PlayArea extends JPanel {
	private static final long serialVersionUID = 367777191638975864L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	private final Rectangle2D background = new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);
	
	private Snake snake;
	private Ellipse2D apple;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.draw(background);
		g2.fill(background);
		
		g2.setColor(Color.GREEN);
		for (Rectangle2D r : snake.get()) {
			g2.fill(r);
		}
		
		g2.setColor(Color.RED);
		g2.draw(apple);
		g2.fill(apple);
	}
	
	public void addSnake(Snake snake) {
		this.snake = snake;
	}
	
	public void addApple(Ellipse2D apple) {
		this.apple = apple;
	}
	
	public Dimension preferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}

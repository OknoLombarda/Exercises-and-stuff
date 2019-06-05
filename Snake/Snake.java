package me.snake;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Snake {
	private static final int DEFAULT_SIZE = 16;
	private static final int delta = 17;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int PAUSE = -1;
	
	private ArrayDeque<Rectangle2D> snake;
	private Dimension bounds;
	private Ellipse2D apple;
	private int score;
	
	private double dx = 0;
	private double dy = 0;
	private int currentDirection;

	public Snake(int x, int y, Dimension bounds) {
		this.bounds = bounds;
		snake = new ArrayDeque<>();
		snake.add(new Rectangle2D.Double(x, y, DEFAULT_SIZE, DEFAULT_SIZE));
		currentDirection = PAUSE;
		score = 0;
	}
	
	public void changeDirection(int direction) {
		if (direction == UP) {
			currentDirection = UP;
			dx = 0;
			dy = -delta;
		}
		
		if (direction == LEFT) {
			currentDirection = LEFT;
			dx = -delta;
			dy = 0;
		}
		
		if (direction == DOWN) {
			currentDirection = DOWN;
			dx = 0;
			dy = delta;
		}
		
		if (direction == RIGHT) {
			currentDirection = RIGHT;
			dx = delta;
			dy = 0;
		}
	}
	
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public Queue<Rectangle2D> get() {
		return snake;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setApple(Ellipse2D apple) {
		this.apple = apple;
	}
	
	public Boolean move() {
		Rectangle2D head = snake.peek();
		Area appleArea = new Area(apple);
		
		double newX = head.getX() + dx;
		double newY = head.getY() + dy;
		
		if (newX > bounds.getWidth() - DEFAULT_SIZE)
			newX = 0;
		else if (newX < 0)
			newX = bounds.getWidth() - DEFAULT_SIZE;
		
		if (newY > bounds.getHeight() - DEFAULT_SIZE)
			newY = 0;
		else if (newY < 0)
			newY = bounds.getHeight() - DEFAULT_SIZE;
		
		head = new Rectangle2D.Double(newX, newY, DEFAULT_SIZE, DEFAULT_SIZE);
		snake.addFirst(head);
		Iterator<Rectangle2D> iter = snake.iterator();
		iter.next();
		while (iter.hasNext()) {
			if (head.intersects(iter.next()))
				return null;
		}
		
		if (!appleArea.intersects(head)) {
			snake.removeLast();
			return false;
		}
		score++;
		return true;
	}
}

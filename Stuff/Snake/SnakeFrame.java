package me.snake;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SnakeFrame extends JFrame {
	private static final long serialVersionUID = -2429704218294522538L;
	private static final int DEFAULT_X = 288;
	private static final int DEFAULT_Y = 280;
	private static final int APPLE_SIZE = 6;
	private static final int NORMAL_DELAY = 100;
	private static final int SHORT_DELAY = 80;
	private static final int LONG_DELAY = 120;
	
	private PlayArea playArea;
	private Snake snake;
	private Ellipse2D apple;
	private int delay;
	
	public SnakeFrame() {
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				int key = event.getKeyCode();
				int currentDirection = snake.getCurrentDirection();
				
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
					if (currentDirection == Snake.UP)
						delay = SHORT_DELAY;
					else if (currentDirection == Snake.DOWN)
						delay = LONG_DELAY;
				}
				
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
					if (currentDirection == Snake.LEFT)
						delay = SHORT_DELAY;
					else if (currentDirection == Snake.RIGHT)
						delay = LONG_DELAY;
				}
				
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
					if (currentDirection == Snake.DOWN)
						delay = SHORT_DELAY;
					else if (currentDirection == Snake.UP)
						delay = LONG_DELAY;
				}
				
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					if (currentDirection == Snake.RIGHT)
						delay = SHORT_DELAY;
					else if (currentDirection == Snake.LEFT)
						delay = LONG_DELAY;
				}
			}
			
			public void keyReleased(KeyEvent event) {
				delay = NORMAL_DELAY;
				int key = event.getKeyCode();
				int currentDirection = snake.getCurrentDirection();
				
				if (currentDirection != Snake.UP
							&& currentDirection != Snake.DOWN
							&& (key == KeyEvent.VK_W || key == KeyEvent.VK_UP))
					snake.changeDirection(Snake.UP);
				
				if (currentDirection != Snake.LEFT
							&& currentDirection != Snake.RIGHT
							&& (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT))
					snake.changeDirection(Snake.LEFT);
				
				if (currentDirection != Snake.DOWN
						&& currentDirection != Snake.UP
							&& (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN))
					snake.changeDirection(Snake.DOWN);
				
				if (currentDirection != Snake.RIGHT
							&& currentDirection != Snake.LEFT
							&& (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT))
					snake.changeDirection(Snake.RIGHT);
			}
		});
		
		playArea = new PlayArea();
		add(playArea, BorderLayout.CENTER);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		
		initializePlayArea();
		
		Thread t = new Thread(() -> {
			while (true) {
				if (snake.getCurrentDirection() != Snake.PAUSE) {
					Boolean flag = snake.move();
					if (flag == null) {
						JOptionPane.showMessageDialog(this, "Game Over\nScore: " + snake.getScore());
						initializePlayArea();
						continue;
					}
					if (flag) {
						getApple();
						playArea.addApple(apple);
						snake.setApple(apple);
					}
					playArea.addSnake(snake);
					//playArea.repaint();
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		Thread rp = new Thread(() -> {
			while (true) {
				playArea.repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		rp.start();
	}
	
	public void initializePlayArea() {
		snake = new Snake(DEFAULT_X, DEFAULT_Y, playArea.preferredSize());
		playArea.addSnake(snake);
		getApple();
		playArea.addApple(apple);
		snake.setApple(apple);
	}
	
	public void getApple() {
		Random rand = new Random();
		Area appleArea;
		boolean flag = true;
		do {
			apple = new Ellipse2D.Double(rand.nextInt(playArea.getWidth() - 12), rand.nextInt(playArea.getHeight() - 16),
									 	 APPLE_SIZE, APPLE_SIZE);
			for (Rectangle2D r : snake.get()) {
				appleArea = new Area(apple);
				flag = !appleArea.intersects(r);
			}
		} while (!flag);
	}
}

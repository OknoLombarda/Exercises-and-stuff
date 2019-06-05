package me.snake;

import java.awt.EventQueue;

public class SnakeGame {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SnakeFrame frame = new SnakeFrame();
			frame.setTitle("Snake");
			frame.setVisible(true);
		});
	}
}

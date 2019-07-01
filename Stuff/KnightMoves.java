import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class KnightMoves {
	public static final int BOARD_SIZE = 8;
	public static final int[] d1 = { 2, -2 };
	public static final int[] d2 = { 1, -1 };
	public static final ArrayList<ArrayList<Point>> solutions = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		boolean[][] board = null;
		ArrayList<Point> moves;

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board = new boolean[BOARD_SIZE][BOARD_SIZE];
				int x = i;
				int y = j;
				board[x][y] = true;
				moves = new ArrayList<Point>();
				moves.add(new Point(x, y));
				moveKnight(board, x, y, moves);
			}
		}

		EventQueue.invokeLater(() -> {
			JFrame f = new JFrame();
			f.setSize(480, 480);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
			JPanel[][] fields = new JPanel[BOARD_SIZE][BOARD_SIZE];
			for (int i = 0; i < BOARD_SIZE; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					fields[i][j] = new JPanel();
					fields[i][j].setBackground(Color.white);
					fields[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					f.add(fields[i][j]);
				}
			}
			f.setVisible(true);

			Thread t = new Thread(() -> {
				Iterator<Point> iter = solutions.get(0).iterator();

				while (iter.hasNext()) {
					Point p = iter.next();
					fields[p.x][p.y].setBackground(Color.DARK_GRAY);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		});
	}

	public static void moveKnight(boolean[][] board, int x, int y, ArrayList<Point> moves) {
		ArrayList<Point> options = new ArrayList<>();

		for (int i : d1) {
			for (int j : d2) {
				options.add(new Point(x + i, y + j));
				options.add(new Point(x + j, y + i));
			}
		}

		if (solutions.size() == 0)
			move(board, moves, options);
	}

	public static void move(boolean[][] board, ArrayList<Point> moves, ArrayList<Point> options) {
		ArrayList<Point> saveMoves = new ArrayList<>(moves);
		boolean[][] saveBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
		transfer(board, saveBoard);

		Iterator<Point> iter = options.iterator();
		Point p;
		int x;
		int y;
		boolean flag = false;
		while (iter.hasNext()) {
			p = iter.next();
			x = (int) p.getX();
			y = (int) p.getY();
			if (isAppropriate(x) && isAppropriate(y) && !board[x][y]) {
				transfer(saveBoard, board);
				moves.removeIf(e -> true);
				moves.addAll(saveMoves);

				moves.add(new Point(x, y));
				board[x][y] = true;
				moveKnight(board, x, y, moves);

				if (!flag)
					flag = true;
			}
		}
		if (!flag && isCleared(board)) {
			solutions.add(moves);
		}
	}

	public static boolean isAppropriate(int i) {
		return i >= 0 && i < BOARD_SIZE;
	}

	public static boolean isCleared(boolean[][] board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (!board[i][j])
					return false;
			}
		}
		return true;
	}

	public static void transfer(boolean[][] from, boolean[][] to) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				to[i][j] = from[i][j];
			}
		}
	}

	public static void printResult(boolean[][] board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(board[i][j] + ((j == BOARD_SIZE - 1) ? "" : ", "));
			}
			System.out.println();
		}
	}
}

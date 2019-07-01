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
 
    public static void main(String[] args) {
        ArrayList<Point> moves;
 
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                moves = new ArrayList<Point>();
                moves.add(new Point(i, j));
                moveKnight(moves, i, j);
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
 
    public static void moveKnight(ArrayList<Point> moves, int x, int y) {
        ArrayList<Point> options = new ArrayList<>();
 
        for (int i : d1) {
            for (int j : d2) {
                options.add(new Point(x + i, y + j));
                options.add(new Point(x + j, y + i));
            }
        }
 
        if (solutions.size() < 2)
            move(moves, options);
    }
 
    public static void move(ArrayList<Point> moves, ArrayList<Point> options) {
        ArrayList<Point> saveMoves = new ArrayList<>(moves);
 
        Iterator<Point> iter = options.iterator();
        Point p;
        int x;
        int y;
        boolean flag = false;
        while (iter.hasNext()) {
            p = iter.next();
            x = (int) p.getX();
            y = (int) p.getY();
            if (isAppropriate(p) && !moves.contains(p)) {
                moves.removeIf(e -> true);
                moves.addAll(saveMoves);
 
                moves.add(new Point(x, y));
                moveKnight(moves, x, y);
 
                if (!flag)
                    flag = true;
            }
        }
        if (!flag && moves.size() == BOARD_SIZE * BOARD_SIZE) {
            solutions.add(moves);
        }
    }
 
    public static boolean isAppropriate(Point p) {
    	int x = (int) p.getX();
        int y = (int) p.getY();
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }
}

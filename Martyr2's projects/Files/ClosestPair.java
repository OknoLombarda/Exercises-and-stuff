import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ClosestPair {
	public static void main(String[] args) {
		int n = 0;
		try(Scanner in = new Scanner(System.in)) {
			System.out.print("Enter amount of points: ");
			n = in.nextInt();
		}
		ArrayList<Point> points = new ArrayList<Point>();
		Random rand = new Random();
		for (int i = 0; i < n; i++)
			points.add(new Point(Math.abs(rand.nextInt()) % 400, Math.abs(rand.nextInt()) % 400));
		
		int minDist = -1;
		int[] indexes = new int[2];
		for (int i = 1; i < n - 1; i++)
			for (int j = i + 1; j < n; j++) {
				int distance = getDistance(points.get(i), points.get(j));
				if (minDist == -1) {
					minDist = distance;
					indexes[0] = i;
					indexes[1] = j;
				}
				else if (distance < minDist) {
					minDist = distance;
					indexes[0] = i;
					indexes[1] = j;
				}
			}
		System.out.println("Minimal distance: " + minDist);
		System.out.println("Points: " + "(" + (int)points.get(indexes[0]).getX() + ";" + (int)points.get(indexes[0]).getY() + "), "
						    + "(" + (int)points.get(indexes[1]).getX() + ";" + (int)points.get(indexes[1]).getY() + ")");
		EventQueue.invokeLater(() -> {
			JFrame frame = new PointFrame(points, indexes);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
	
	public static int getDistance(Point first, Point second) {
		return (int) Math.sqrt((double) (Math.pow((double) (first.getX() - second.getX()), 2) + Math.pow((double) (first.getY() - second.getY()), 2)));
	}
}

class PointFrame extends JFrame {
	public PointFrame(ArrayList<Point> points, int[] indexes) {
		add(new PointComponent(points, indexes));
		pack();
	}
}

class PointComponent extends JComponent {
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 400;
	private static final int RADIUS = 3;
	private ArrayList<Point> points;
	private int[] indexes;
	
	public PointComponent(ArrayList<Point> points, int[] indexes) {
		this.points = points;
		this.indexes = indexes;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int i = 0;
		for (Point2D p : points) {
			Ellipse2D pel = new Ellipse2D.Double(p.getX(), p.getY(), RADIUS, RADIUS);
			if (i == indexes[0] || i == indexes[1])
				g2.setPaint(Color.RED);
			else
				g2.setPaint(Color.BLACK);
			g2.fill(pel);
			g2.draw(pel);
			i++;
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
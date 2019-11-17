package me.graphs.generic.gui;

import me.graphs.generic.DijkstraAlgorithm;
import me.graphs.generic.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

public class MapBuilder extends JPanel {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private Set<Rectangle2D> fields;
    private Set<Rectangle2D> selected;
    private Rectangle2D source;
    private Rectangle2D target;
    private List<Rectangle2D> path;

    private JFrame parent;

    private int size = 10;

    public MapBuilder(JFrame parent) {
        super();
        this.parent = parent;
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        fields = new LinkedHashSet<>();
        selected = new HashSet<>();

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mod = e.getModifiersEx();
                Rectangle2D field = getField(e.getLocationOnScreen());

                if (SwingUtilities.isRightMouseButton(e)) {
                    if (source == field) {
                        source = null;
                    } else if (target == field) {
                        target = null;
                    }
                    repaint();
                }

                if (field != null) {
                    if (mod == MouseEvent.CTRL_DOWN_MASK && SwingUtilities.isLeftMouseButton(e)) {
                        source = field;
                        repaint();
                    } else if (mod == MouseEvent.ALT_DOWN_MASK && SwingUtilities.isLeftMouseButton(e)) {
                        target = field;
                        repaint();
                    } else {
                        handleMouseEvent(e, field);
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseEvent(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    if (size > 10) {
                        size -= 5;
                        recalculate();
                    }
                } else if (size < 50) {
                    size += 5;
                    recalculate();
                }
            }
        };

        addMouseListener(adapter);
        addMouseWheelListener(adapter);
        addMouseMotionListener(adapter);

        InputMap imap = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ctrl F"), "findPath");
        imap.put(KeyStroke.getKeyStroke("ctrl L"), "clear");

        ActionMap amap = getActionMap();
        amap.put("findPath", new PathFinder());
        amap.put("clear", new Sweeper());

        recalculate();
    }

    public void recalculate() {
        Dimension pref = getPreferredSize();

        fields.removeIf(e -> true);
        selected.removeIf(e -> true);
        path = null;
        source = null;
        target = null;

        for (int i = 0; i < pref.width; i += size) {
            for (int j = 0; j < pref.height; j += size) {
                fields.add(new Rectangle2D.Double(i, j, size, size));
            }
        }

        repaint();
    }

    private void handleMouseEvent(MouseEvent e, Rectangle2D field) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            selected.add(field);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            selected.remove(field);
        }
        repaint();
    }

    private void handleMouseEvent(MouseEvent e) {
        Rectangle2D field = getField(e.getLocationOnScreen());
        if (field != null) {
            handleMouseEvent(e, field);
        }
    }

    private Rectangle2D getField(Point p) {
        SwingUtilities.convertPointFromScreen(p, this);
        for (Rectangle2D f : fields) {
            if (f.contains(p.x, p.y)) {
                return f;
            }
        }
        return null;
    }

    public Rectangle2D[][] getFieldsAsArray() {
        Dimension prf = getPreferredSize();
        int y = (int) Math.ceil((double) prf.height / size);
        int x = (int) Math.ceil((double) prf.width / size);
        Rectangle2D[][] arr = new Rectangle2D[y][x];

        Iterator<Rectangle2D> iter = fields.iterator();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = iter.next();
            }
        }

        return arr;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        if (source != null) {
            g2.setPaint(Color.RED);
            g2.fill(source);
        }

        if (target != null) {
            g2.setPaint(Color.GREEN);
            g2.fill(target);
        }

        if (path != null && !path.isEmpty()) {
            g2.setPaint(Color.MAGENTA);
            path.forEach(g2::fill);
        }

        g2.setPaint(Color.BLUE);
        selected.forEach(g2::fill);

        g2.setPaint(Color.BLACK);
        fields.forEach(g2::draw);
    }

    private class PathFinder extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            if (source != null && target != null) {
                Graph<Rectangle2D> gr = DijkstraAlgorithm.constructGraph(getFieldsAsArray());
                path = DijkstraAlgorithm.findPath(gr, source, target, selected);

                if (!path.isEmpty() && path.get(0).equals(source)) {
                    path.remove(0);
                    path.remove(path.size() - 1);
                } else {
                    path = null;
                    JOptionPane.showMessageDialog(parent, "Path to the target does not exist");
                }

                repaint();
            }
        }
    }

    private class Sweeper extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            selected.removeIf(f -> true);
            source = null;
            target = null;
            path = null;
            repaint();
        }
    }
}

package me.graphs.index.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class FileCellRenderer extends JPanel implements ListCellRenderer<File> {
    private File value;
    private Font fileNameFont;
    private Font pathFont;

    public FileCellRenderer() {
        fileNameFont = new Font(Font.SERIF, Font.BOLD, 12);
        pathFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends File> list, File value, int index, boolean isSelected, boolean cellHasFocus) {
        this.value = value;
        setOpaque(true);
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        return this;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(fileNameFont);

        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D bounds = fileNameFont.getStringBounds(value.getName(), frc);

        double x = 5;
        double y = (getHeight() - bounds.getHeight()) / 2.0 - bounds.getY();

        g2.drawString(value.getName(), (int) x, (int) y);

        if (value.isDirectory()) {
            y += 2;
            g2.drawLine((int) x, (int) y, (int) (x + bounds.getWidth()), (int) y);
        }

        File path = value;
        if (!path.isDirectory()) {
            path = path.getParentFile();
        }

        g2.setFont(pathFont);
        bounds = pathFont.getStringBounds(path.getPath(), frc);

        x = getWidth() - 5 - bounds.getWidth();
        y = (getHeight() - bounds.getHeight()) / 2.0 - bounds.getY();

        g2.setPaint(Color.DARK_GRAY);
        g2.drawString(path.getPath(), (int) x, (int) y);
    }

    public Dimension getPreferredSize() {
        double width = 20;
        double height = 5;

        Graphics2D g = (Graphics2D) getGraphics();
        g.setFont(fileNameFont);
        FontRenderContext frc = g.getFontRenderContext();

        Rectangle2D bounds = fileNameFont.getStringBounds(value.getName(), frc);

        width += bounds.getWidth();
        height += bounds.getHeight();

        g.setFont(pathFont);
        frc = g.getFontRenderContext();
        bounds = pathFont.getStringBounds(value.isDirectory() ? value.getPath() : value.getParent(), frc);

        width += bounds.getWidth();

        return new Dimension((int) width, (int) height);
    }
}

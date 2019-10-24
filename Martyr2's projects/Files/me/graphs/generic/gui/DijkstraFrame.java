package me.graphs.generic.gui;

import javax.swing.*;
import java.awt.*;

public class DijkstraFrame extends JFrame{
    public DijkstraFrame() {
        super("Dijkstra Algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MapBuilder mapBuilder = new MapBuilder(this);
        add(mapBuilder, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
}

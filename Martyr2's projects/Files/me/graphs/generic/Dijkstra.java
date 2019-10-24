package me.graphs.generic;

import me.graphs.generic.gui.DijkstraFrame;

import javax.swing.*;
import java.awt.*;

public class Dijkstra {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame f = new DijkstraFrame();
            f.setVisible(true);
        });
    }
}

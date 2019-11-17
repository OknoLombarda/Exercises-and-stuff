package me.graphs.index;

import me.graphs.index.gui.SearchFrame;

import java.awt.*;

public class TextSearch {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        EventQueue.invokeLater(() -> {
            SearchFrame frame = new SearchFrame();
            frame.setVisible(true);
        });
    }
}

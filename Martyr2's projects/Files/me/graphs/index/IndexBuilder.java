package me.graphs.index;

import me.graphs.index.gui.ProgressFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.*;

public class IndexBuilder {
    private Graph index;
    private File root;

    public IndexBuilder() {
        index = new Graph();
    }

    public void setRoot(File root) {
        reset();
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public void reset() {
        root = null;
        index = new Graph();
    }

    public InvertedIndex build(ProgressFrame progressFrame) throws InterruptedException {
        if (root == null) {
            root = new File("/");
        }

        FileScanner fs = new FileScanner(root, progressFrame, index);

        SwingUtilities.invokeLater(() -> {
            progressFrame.setTitle("Build");
            progressFrame.setTaskName("Indexing...");
            progressFrame.updateProgress(" ");
            progressFrame.setProgressIndeterminate(true);
            progressFrame.removeLastOnCancelAction();
            progressFrame.setOnCancelAction(event -> fs.cancel(false), new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    fs.cancel(false);
                }
            });
        });

        fs.execute();
        progressFrame.setVisible(true);

        InvertedIndex invertedIndex;
        try {
            if (fs.isCancelled()) {
                return null;
            }
            invertedIndex = fs.get();
        } catch (ExecutionException e) {
            return null;
        }

        return invertedIndex;
    }
}

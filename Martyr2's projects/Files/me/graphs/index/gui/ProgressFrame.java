package me.graphs.index.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class ProgressFrame extends JDialog {
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 150;

    private JLabel taskName;
    private JLabel currentTask;
    private JProgressBar progressBar;
    private JButton cancel;
    private ActionListener onCancel;
    private WindowListener wListener;

    public ProgressFrame(JFrame parent) {
        super(parent, true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        taskName = new JLabel();
        currentTask = new JLabel();
        progressBar = new JProgressBar(0, 100);
        cancel = new JButton("Cancel");

        add(taskName, new GBC(0, 0).setAnchor(GBC.WEST).setInsets(10)
                .setWeight(100, 0));
        add(currentTask, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(0, 10, 10, 10)
                .setWeight(100, 0));
        add(progressBar, new GBC(0, 2).setFill(GBC.HORIZONTAL)
                .setInsets(0, 10, 10, 10).setWeight(100, 0));
        add(cancel, new GBC(0, 3).setInsets(0, 10, 10, 10)
                .setWeight(0, 0));
    }

    public void setTaskName(String taskName) {
        this.taskName.setText(taskName);
    }

    public void setProgressIndeterminate(boolean flag) {
        progressBar.setIndeterminate(flag);
    }

    public void setProgressMaxValue(int max) {
        progressBar.setMaximum(max);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public void updateProgress(String task, boolean increment) {
        if (increment) {
            progressBar.setValue(progressBar.getValue() + 1);
        }
        currentTask.setText(task);
    }

    public void updateProgress(String task) {
        updateProgress(task, false);
    }

    public void setOnCancelAction(ActionListener listener, WindowListener wListener) {
        onCancel = listener;
        cancel.addActionListener(listener);

        this.wListener = wListener;
        addWindowListener(wListener);
    }

    public void removeLastOnCancelAction() {
        if (onCancel != null) {
            cancel.removeActionListener(onCancel);
            onCancel = null;
        }
        if (wListener != null) {
            removeWindowListener(wListener);
            wListener = null;
        }
    }
}

package me.graphs.index.gui;

import me.graphs.index.FileManager;
import me.graphs.index.IndexBuilder;
import me.graphs.index.InvertedIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class SearchFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int SUCCESS = 11;
    private static final int FAIL = 10;
    private static final int CANCEL = 2;

    private IndexBuilder indexBuilder;

    private InvertedIndex index;
    private boolean saved;
    private File saveFile;

    private JFileChooser fileChooser;
    private ProgressFrame progressFrame;

    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem build;
    private JMenuItem update;
    private JLabel rootLabel;

    private DefaultListModel<File> model;

    public SearchFrame() {
        super("Text Search");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        progressFrame = new ProgressFrame(this);

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic('f');

        JMenuItem newIndex = new JMenuItem("New");
        prepareMenuItem(newIndex, 'n', "ctrl N");
        newIndex.addActionListener(event -> createNewIndex());

        JMenuItem open = new JMenuItem("Open");
        prepareMenuItem(open, 'o', "ctrl O");
        open.addActionListener(event -> open());

        save = new JMenuItem("Save");
        prepareMenuItem(save, 's', "ctrl S");
        save.setEnabled(false);
        save.addActionListener(event -> save());

        saveAs = new JMenuItem("Save as...");
        prepareMenuItem(saveAs, 'a', "ctrl alt S");
        saveAs.setEnabled(false);
        saveAs.addActionListener(event -> saveAs());

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic('e');
        exit.addActionListener(event -> exit());

        file.add(newIndex);
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('e');

        build = new JMenuItem("Build");
        prepareMenuItem(build, 'b', "ctrl B");
        build.setEnabled(false);
        build.addActionListener(event -> build());

        update = new JMenuItem("Update");
        prepareMenuItem(update, 'u', "ctrl U");
        update.setEnabled(false);
        update.addActionListener(event -> update());

        edit.add(build);
        edit.add(update);

        menuBar.add(file);
        menuBar.add(edit);

        setJMenuBar(menuBar);

        JPanel panel = new JPanel(new GridBagLayout());
        JTextField searchBar = new JTextField();
        searchBar.addActionListener(event -> search(searchBar.getText()));

        model = new DefaultListModel<>();
        JList<File> list = new JList<>(model);
        list.setSelectedIndex(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new FileCellRenderer());
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openFile(list.getSelectedValue());
                }
            }
        });
        JScrollPane pane = new JScrollPane(list);

        rootLabel = new JLabel("Root: ");

        panel.add(rootLabel, new GBC(0, 0).setAnchor(GBC.NORTHWEST).setFill(GBC.HORIZONTAL)
                .setInsets(10, 10, 0, 10).setWeight(100, 0));
        panel.add(searchBar, new GBC(0, 1).setAnchor(GBC.NORTHWEST).setFill(GBC.HORIZONTAL)
                .setInsets(10).setWeight(100, 0));
        panel.add(pane, new GBC(0, 2).setFill(GBC.BOTH).setInsets(10).setWeight(100, 100));

        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    private void createNewIndex() {
        if (indexBuilder == null) {
            indexBuilder = new IndexBuilder();
        } else {
            indexBuilder.reset();
        }

        int option;

        if (index != null && !saved && !build.isEnabled()) {
            option = askToSave();
            if (option == CANCEL || option == FAIL) {
                return;
            }
        }

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Choose root directory");
        option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File root = fileChooser.getSelectedFile();
            indexBuilder.setRoot(root);
            saved = false;
            index = null;
            saveFile = null;
            updateRootLabel(indexBuilder.getRoot().toString());
        }

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle(null);

        save.setEnabled(false);
        saveAs.setEnabled(false);
        build.setEnabled(true);
        update.setEnabled(false);
    }

    private void open() {
        if (index != null && !saved && !build.isEnabled()) {
            int option = askToSave();
            if (option == CANCEL) {
                return;
            }
        }

        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                saveFile = fileChooser.getSelectedFile();
                index = FileManager.readData(saveFile);
            } catch (IOException e) {
                showErrorMessage("Unknown error occurred during reading the file");
                index = null;
                saveFile = null;
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (index != null) {
            saved = true;

            updateRootLabel(index.getRoot().getPath());
            saveAs.setEnabled(true);
            build.setEnabled(false);
            update.setEnabled(true);
        }
    }

    private void build() {
        try {
            index = indexBuilder.build(progressFrame);
        } catch (InterruptedException e) {
            index = null;
            showErrorMessage("Unknown error occurred while building the index");
        }

        if (index == null) {
            return;
        }

        JOptionPane.showMessageDialog(this, "Building completed successfully!",
                "Done", JOptionPane.INFORMATION_MESSAGE);

        build.setEnabled(false);
        update.setEnabled(true);

        saved = false;
        save.setEnabled(true);
        saveAs.setEnabled(true);
    }

    private void update() {
        indexBuilder.reset();
        indexBuilder.setRoot(index.getRoot());
        InvertedIndex newIndex;

        try {
            newIndex = indexBuilder.build(progressFrame);
        } catch (InterruptedException e) {
            showErrorMessage("Unknown error occurred while updating the index");
            newIndex = null;
            e.printStackTrace();
        }

        if (newIndex == null) {
            return;
        }

        JOptionPane.showMessageDialog(this, "Updating completed successfully!",
                "Done", JOptionPane.INFORMATION_MESSAGE);

        index = newIndex;

        saved = false;
        save.setEnabled(true);
    }

    private void search(String s) {
        if (s == null || s.isBlank()) return;

        model.removeAllElements();
        model.addAll(index.search(s));
    }

    private int save() {
        if (saveFile == null) {
            return saveAs();
        }

        try {
            FileManager.writeData(saveFile, index);

            saved = true;
            save.setEnabled(false);

            return SUCCESS;
        } catch (IOException e) {
            showErrorMessage("Unknown error occurred when tried to save the file");
            e.printStackTrace();
        }
        return FAIL;
    }

    private int saveAs() {
        int option;
        do {
            option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                saveFile = fileChooser.getSelectedFile();
            } else {
                return CANCEL;
            }

            if (saveFile.exists()) {
                option = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to rewrite this file?",
                        "Rewrite", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return CANCEL;
                }
            }
        } while (option == JOptionPane.NO_OPTION);

        return save();
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int askToSave() {
        int option = JOptionPane.showConfirmDialog(this,
                "Do you wish to save the current index first?", "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            int result;

            if (saveFile != null) {
                result = save();
            } else {
                result = saveAs();
            }

            return result;
        }

        return option;
    }

    private void exit() {
        if (index != null && !saved && !build.isEnabled()) {
            int option = askToSave();
            if (option == CANCEL) {
                return;
            } else if (option == FAIL) {
                option = JOptionPane.showConfirmDialog(this,
                        "Couldn't save the file. Exit anyway?", "Error", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION) {
                    return;
                }
            }
        }
        System.exit(0);
    }

    private void openFile(File file) {
        if (file == null) return;

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                showErrorMessage("Unknown error occurred while trying to open the file");
            }
        }
    }

    private void prepareMenuItem(JMenuItem menuItem, char mnemonic, String keyStroke) {
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
    }

    private void updateRootLabel(String root) {
        rootLabel.setText(rootLabel.getText().substring(0, 6).concat(root));
    }
}

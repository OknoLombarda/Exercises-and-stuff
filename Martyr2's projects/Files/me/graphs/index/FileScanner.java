package me.graphs.index;

import me.graphs.index.gui.ProgressFrame;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class FileScanner extends SwingWorker<InvertedIndex, String> {
    private ProgressFrame progressFrame;
    private File file;
    private Graph index;
    private Set<Callable<Result>> tasks;
    private boolean scanning;

    public FileScanner(File file, ProgressFrame progressFrame, Graph index) {
        this.file = file;
        this.progressFrame = progressFrame;
        this.index = index;
        scanning = true;
        tasks = new HashSet<>();
    }

    @Override
    protected InvertedIndex doInBackground() {
        int fileCount = scanFiles(file, tasks);

        SwingUtilities.invokeLater(() -> {
            progressFrame.setProgressIndeterminate(false);
            progressFrame.setProgressMaxValue(fileCount);
            progressFrame.setProgress(0);
            progressFrame.setTaskName("Processing...");
        });

        scanning = false;

        ExecutorService exec = Executors.newFixedThreadPool(3);
        List<Future<Result>> out;
        try {
            out = exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            return null;
        }

        for (Future<Result> fut : out) {
            if (isCancelled()) {
                exec.shutdown();
            }

            Result result;
            try {
                result = fut.get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }

            if (result != null) {
                result.files.forEach((key, value) -> index.addEdge(result.parent, key, value));
            }
        }

        exec.shutdown();
        return new InvertedIndex(file, index);
    }

    protected void process(List<String> chunks) {
        for (String s : chunks) {
            progressFrame.updateProgress(s, !scanning);
        }
    }

    protected void done() {
        progressFrame.setVisible(false);
    }

    private int scanFiles(File file, Set<Callable<Result>> tasks) {
        if (isCancelled()) {
            return -1;
        }

        int count = 0;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                tasks.add(() -> {
                    Map<String, Integer> ind;
                    Result res = new Result(file);

                    for (File f : files) {
                        if (f.isFile()) {
                            publish(f.getPath());
                            if (f.canRead() && isTextFile(f)) {
                                ind = readFile(f);
                                res.files.put(f, ind);
                            }
                        }
                    }
                    return res;
                });

                for (File f : files) {
                    publish(f.getPath());
                    if (f.isDirectory() && !Files.isSymbolicLink(f.toPath())) {
                        count += scanFiles(f, tasks);
                    } else if (f.isFile()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Map<String, Integer> readFile(File file) {
        Map<String, Integer> ind = new HashMap<>();

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                for (String s : sc.nextLine().replaceAll("\\W+", " ").split("\\s+")) {
                    if (!s.isEmpty()) {
                        s = s.toLowerCase();
                        if (ind.containsKey(s)) {
                            ind.put(s, ind.get(s) + 1);
                        } else {
                            ind.put(s, 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ind;
    }

    private boolean isTextFile(File file) {
        BufferedInputStream bis;
        byte[] input;
        int count = 0;

        try {
            bis = new BufferedInputStream(new FileInputStream(file));

            int size = bis.available();
            input = new byte[Math.min(1024, size)];

            bis.read(input);
        } catch (IOException e) {
            return false;
        }

        for (byte b : input) {
            if (Character.isDigit(b) || Character.isAlphabetic(b) || Character.isIdeographic(b)) {
                count++;
            }
        }

        return input.length != 0 && (double) count / input.length >= 0.6;
    }

    private static class Result {
        File parent;
        Map<File, Map<String, Integer>> files;

        Result(File parent) {
            this.parent = parent;
            files = new HashMap<>();
        }
    }
}

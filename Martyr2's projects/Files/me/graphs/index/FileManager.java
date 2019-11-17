package me.graphs.index;

import java.io.*;

public class FileManager {
    public static InvertedIndex readData(File file) throws IOException, ClassNotFoundException {
        InvertedIndex index;
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            index = (InvertedIndex) is.readObject();
        }
        return index;
    }

    public static void writeData(File file, InvertedIndex index) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            os.writeObject(index);
        }
    }
}

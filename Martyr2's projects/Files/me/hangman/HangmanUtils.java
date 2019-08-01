package me.hangman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HangmanUtils {
	private static List<String> words;
	private static File data = new File("words.dat");
	private static Iterator<String> wordIter;
	
	@SuppressWarnings("unchecked")
	public static void readData() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(data)));
		words = (List<String>) is.readObject();
		is.close();
		Collections.shuffle(words);
		wordIter = words.iterator();
	}
	
	public static String getWord() {
		if (!wordIter.hasNext()) {
			Collections.shuffle(words);
			wordIter = words.iterator();
		}
		
		return wordIter.next();
	}
}

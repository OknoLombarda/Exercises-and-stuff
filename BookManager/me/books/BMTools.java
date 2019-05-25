package me.books;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class BMTools {
	private static File expBooks;
	private static File[] list;
	private static File backup;
	private static File[] bpFiles;
	private static File save;
	private static int iter;

	public static void initializeFiles() throws URISyntaxException {
		String path = new File(BMTools.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		path = path.replace("BookManager.jar", "");
		save = new File(path + "save.txt");
		expBooks = new File(path + "BookExport/");
		list = expBooks.listFiles();
		backup = new File(path + "backup/");
		bpFiles = backup.listFiles();
	}
	
	public static void loadSave() throws IOException {
		if (save.exists()) {
			Scanner sF = new Scanner(save);
			String temp = sF.next();
			iter = Integer.valueOf(temp);
			sF.close();
		}
		else {
			save.createNewFile();
			iter = 0;
			printSave();
		}
	}
	
	public static void printSave() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(save);
		pw.print(iter);
		pw.close();
	}
	
	public static int getIter() {
		return iter;
	}

	public static boolean hasNext() {
		return iter < list.length - 1;
	}
	
	public static String getNext() throws IOException {
		iter++;
		return getText(iter);
	}
	
	public static boolean hasPrevious() {
		return iter != 0;
	}
	
	public static String getPrevious() throws IOException {
		iter--;
		return getText(iter);
	}

	public static void backupFiles() throws IOException {
		if (bpFiles != null)
			for (File f : bpFiles)
				f.delete();
		for (File f : list)
			Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(backup.getAbsolutePath().concat("/")
																						 .concat(f.getName())));
		bpFiles = backup.listFiles();
	}

	public static void loadFromBackup() throws IOException {
		for (File f : list)
			f.delete();
		for (File f : bpFiles)
			Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(expBooks.getAbsolutePath().concat("/")
					                                                                       .concat(f.getName())));
		list = expBooks.listFiles();
		iter = 0;
	}

	public static void replace(String oldString, String newString) throws IOException {
		for (int i = 0; i < list.length; i++) {
			Scanner in = new Scanner(Paths.get(list[i].getAbsolutePath()), "UTF-8");
			ArrayList<String> book = new ArrayList<>();
			String check;
			while (in.hasNextLine()) {
				check = in.nextLine();
				if (check.contains(oldString))
					check = check.replace(oldString, newString);
				book.add(check);
			}
			PrintWriter out = new PrintWriter(new FileWriter(list[i].getAbsolutePath(), false));
			for (String s : book)
				out.println(s);
			in.close();
			out.close();
		}
	}

	public static int amount() {
		return list.length;
	}

	public static String getLastPrinted() throws IOException {
		return getText(iter);
	}
	
	public static String getText(int num) throws IOException {
		Scanner text = new Scanner(Paths.get(list[num].getAbsolutePath()), "UTF-8");
		String line;
		StringBuilder sb = new StringBuilder();
		while (text.hasNextLine()) {
			line = text.nextLine();
			sb.append(line).append("\n");
		}
		text.close();
		return sb.toString();
	}

	public static void delete() {
		list[iter].delete();
		if (iter != 0)
			iter--;
		list = expBooks.listFiles();
	}
}

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

class wordHolder
{
	private static Word[] words = new Word[0];
	static File keeper = new File("words.txt");
	
	static
	{
		try
		{
			readWords();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public boolean isEmpty()
	{
		return words.length == 0;
	}
	
	private static void readWords() throws IOException
	{
		if (keeper.exists())
		{	
			Scanner input = new Scanner(Paths.get(keeper.getAbsolutePath()), "UTF-8");
			String read = input.nextLine();
			if (read != null)
			{
				words = new Word[Integer.parseInt(read.substring(read.indexOf("=") + 1, read.indexOf("}")))];
				
				int i = 0;
				while (input.hasNextLine())
				{
					read = input.nextLine();
					String word = "";
					int[] d = new int[3];
					ArrayList<String> trans = new ArrayList<>();
					for (int k = 0; k < read.length(); k++)
					{
						if (read.substring(0, k).endsWith("word="))
							word = read.substring(k, read.indexOf(","));
						if (read.substring(0, k).endsWith("lastTimePracticed="))
						{
							int o = k;
							int j = k;
							for (int l = 0; l < 3; l++)
							{
								j = read.indexOf(",", j + 1);
								d[l] = Integer.parseInt(read.substring(o, j));
								o = j + 1;
							}
							k = o;
						}
						if (read.substring(0, k).endsWith("translations="))
						{
							int j = k;
							while (k < read.length())
							{
								j = read.indexOf(",", j + 1);
								if (j != -1)
								{
									trans.add(read.substring(k, j));
									k = j + 1;
								}
								else
								{
									trans.add(read.substring(k, read.length() - 1));
									break;
								}
							}
						}
					}
					String[] translations = new String[trans.size()];
					trans.toArray(translations);
					words[i] = new Word(word, d[0], d[1], d[2], translations);
					i++;
				}
			}
			input.close();
		}
	}
	
	public wordHolder() {};
	
	public void addWord(String word, String... translations)
	{
		int newLength = words.length + 1;
		words = Arrays.copyOf(words, newLength);
		words[newLength - 1] = new Word(word, translations);
	}
	
	public void delWord(int index)
	{
		words[index] = words[words.length - 1];
		words = Arrays.copyOf(words, words.length - 1);
	}
	
	public void editWord(int index, String word)
	{
		words[index].editWord(word);
	}
	
	public void editTranslations(int index, String... translations)
	{
		words[index].editTranslations(translations);
	}
	
	public void addTranslations(int index, String... translations)
	{
		words[index].addTranslations(translations);
	}
	
	public int getLength()
	{
		return words.length;
	}
	
	public int[] findWord(String s)
	{
		if (!isEmpty())
		{
			ArrayList<Integer> indexes = new ArrayList<>();
			for (int i = 0; i < words.length; i++)
			{
				if (words[i].getWord().equalsIgnoreCase(s))
					indexes.add(Integer.valueOf(i));
				else
				{
					String[] translations = words[i].getTranslations();
					for (int k = 0; k < translations.length; k++)
					{
						if (s.equalsIgnoreCase(translations[k]))
						{
							indexes.add(i);
							break;
						}
					}
				}
			}
			int[] result = indexes.stream().mapToInt(Integer::intValue).toArray();
			return result;
		}
		return null;
	}
	
	public String getWord(int i)
	{
		return words[i].getWord();
	}
	
	public String[] getTranslations(int i)
	{
		return words[i].getTranslations();
	}
	
	public void printWords() throws IOException
	{
		if (!keeper.exists())
			keeper.createNewFile();
		
		PrintWriter output = new PrintWriter(keeper.getAbsolutePath(), "UTF-8");
		output.println("{length=" + String.valueOf(words.length) + "}");
		for (int i = 0; i < words.length; i++)
			output.println(words[i].toString());
		output.close();
	}
	
	private static class Word implements Comparable<Word>
	{
		private String word;
		private String[] translations;
		private LocalDate lastTimePracticed;
	
		public Word (String word, String... translations)
		{
			this.word = word;
			this.translations = translations;
			lastTimePracticed = LocalDate.now();
		}
	
		public Word(String word, int year, int month, int day, String... translations)
		{
			this (word, translations);
			lastTimePracticed = LocalDate.of(year, month, day);
		}
	
		public void updatePracticed()
		{
			lastTimePracticed = LocalDate.now();
		}
	
		public void addTranslations (String... translations)
		{
			int oldLength = this.translations.length;
			this.translations = Arrays.copyOf(this.translations, oldLength + translations.length);
			
			int k = 0;
			for (int i = oldLength; i < this.translations.length; i++)
			{
				this.translations[i] = translations[k];
				k++;
			}
		}
		
		public void editWord(String word)
		{
			this.word = word;
		}
		
		public void editTranslations(String... translations)
		{
			this.translations = translations;
		}

		public int compareTo(Word word)
		{
			if (this.lastTimePracticed.isAfter(word.lastTimePracticed))
				return 1;
		
			if (this.lastTimePracticed.isBefore(word.lastTimePracticed))
				return -1;
		
			return 0;
		}
	
		public String getWord()
		{
			return word;
		}
	
		public String[] getTranslations()
		{
			return translations;
		}
	
		public String toString()
		{
			String result = "{word=" + word + ",lastTimePracticed=" + lastTimePracticed.getYear()
				+ "," + lastTimePracticed.getMonthValue() + "," + lastTimePracticed.getDayOfMonth() 
				+ ",translations=";
			for (int i = 0; i < translations.length; i++)
			{
				result += translations[i];
				if (i != translations.length - 1)
					result += ",";
			}
			result += "}";
			return result;
		}
	}
}
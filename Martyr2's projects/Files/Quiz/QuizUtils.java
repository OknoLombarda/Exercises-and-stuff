import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
 
public class QuizUtils {
    private static final File questf = new File("questions.txt");
    private static LinkedList<Question> questions = new LinkedList<Question>();
    
    public static void readQuestions() {
        try(Scanner sc = new Scanner(questf)) {
            String line;
            while (sc.hasNext()) {
                line = sc.nextLine();
                questions.add(new Question(line.replaceAll("\\:+.+$", ""), line.replaceAll("^.+\\:+", "")));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void printQuestions() {
        try(PrintWriter pw = new PrintWriter(questf)) {
            Iterator<Question> iter = questions.iterator();
            while (iter.hasNext())
                pw.println(iter.next().forPrinting());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void startTest() {
        Collections.shuffle(questions);
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> answers = new ArrayList<>();
        int i = 0;
        String inp;
        Iterator<Question> iter = questions.iterator();
        while (iter.hasNext() && i < 10) {
            Question quest = iter.next();
            System.out.println(quest.getQuestion());
            inp = in.nextLine();
            if (inp.equalsIgnoreCase(quest.getAnswer()))
                answers.add(1);
            else
                answers.add(0);
        }
        System.out.printf("\n\nResults:\n");
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> ansIter = answers.iterator(); 
        for (int g = 0; g < answers.size(); g++) {
            sb.append(g + 1).append(" - ");
            int temp = ansIter.next();
            if (temp == 1)
                sb.append("Correct");
            else
                sb.append("Wrong");
            System.out.println(sb.toString());
            sb.delete(0, sb.length());
        }
    }
    
    public static void browseQuestions() {
        Scanner in = new Scanner(System.in);
        Iterator<Question> iter = questions.iterator();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(i + 1).append(". ").append(iter.next());
            System.out.println(sb.toString());
            sb.delete(0, sb.length());
            i++;
        }
        System.out.println("Введите через пробел номера вопросов, которые нужно удалить" +
                           ", либо q, чтобы вернуться в меню");
        String inp = in.nextLine();
        if (!inp.equalsIgnoreCase("q")) {
            int[] indexes = Arrays.stream(inp.split("\\s+")).mapToInt(s -> Integer.parseInt(s) - 1).toArray();
            ListIterator<Question> delIter = questions.listIterator();
            i = 0;
            int k = 0;
            while (delIter.hasNext()) {
                delIter.next();
                if (k < indexes.length && i == indexes[k]) {
                    delIter.remove();
                    k++;
                }
                i++;
            }
            printQuestions();
        }
    }
}

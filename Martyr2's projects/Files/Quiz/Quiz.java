import java.util.Scanner;
 
public class Quiz {
    public static void main(String[] args) {
        QuizUtils.readQuestions();
        while (true)
            mainMenu();
    }
    
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        System.out.printf("\nВыберите опцию:\n\n1. Начать тест\n2. Просмотреть список вопросов\n3. Выйти\n\n");
        int ans = in.nextInt();
        
        if (ans == 3)
            System.exit(0);
        else if (ans == 1)
            QuizUtils.startTest();
        else if (ans == 2)
            QuizUtils.browseQuestions();
        else
            System.out.println("Ошибка ввода");
    }
}

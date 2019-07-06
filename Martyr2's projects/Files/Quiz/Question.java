public class Question {
    private String question;
    private String answer;
    
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public String toString() {
        return question + " (" + answer + ")";
    }
    
    public String forPrinting() {
        return question + ":" + answer;
    }
}

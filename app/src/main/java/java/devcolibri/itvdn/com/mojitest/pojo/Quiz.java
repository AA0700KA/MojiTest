package java.devcolibri.itvdn.com.mojitest.pojo;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private int id;
    private String question;
    private List<Answer> answers;
    private Answer currentAnswer;

    public Quiz(String question) {
        this.question = question;
        answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public Answer getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(Answer currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

}

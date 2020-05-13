package java.devcolibri.itvdn.com.mojitest.pojo;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private int id;
    private String name;
    private List<Quiz> quizList;
    private int result;
    private String verdict;

    public Test() {
        quizList = new ArrayList<>();
    }

    public Test(String name) {
        this.name = name;
        quizList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addQuiz(Quiz quiz) {
        quizList.add(quiz);
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void countResult() {
        result = 0;
        for (Quiz quiz : quizList) {
            result += quiz.getCurrentAnswer().getData();
        }
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

}

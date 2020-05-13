package java.devcolibri.itvdn.com.mojitest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.devcolibri.itvdn.com.mojitest.R;
import java.devcolibri.itvdn.com.mojitest.dao.TestDAO;
import java.devcolibri.itvdn.com.mojitest.pojo.Answer;
import java.devcolibri.itvdn.com.mojitest.pojo.Quiz;
import java.devcolibri.itvdn.com.mojitest.pojo.Test;


public class TestActivity extends Activity {

    private Test currentTest;
    private TextView questionText;
    private RadioGroup group;
    private RadioButton answ1;
    private RadioButton answ2;
    private RadioButton answ3;
    private Quiz currentQuiz;
    private int indexQuiz = -1;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        int testId = getIntent().getIntExtra("Test", 0);
        currentTest = getTest(testId);
        questionText = findViewById(R.id.textView);
        group = findViewById(R.id.radioGroup);
        answ1 = findViewById(R.id.asw1);
        answ2 = findViewById(R.id.asw2);
        answ3 = findViewById(R.id.asw3);
        increaseTest();
    }

    public void next(View view) {

        Answer currentAnswer = null;
        switch (group.getCheckedRadioButtonId()) {
            case R.id.asw1:
                currentAnswer = currentQuiz.getAnswers().get(0);
                break;
            case R.id.asw2:
                currentAnswer = currentQuiz.getAnswers().get(1);
                break;
            case R.id.asw3:
                currentAnswer = currentQuiz.getAnswers().get(2);
                break;
        }
        currentQuiz.setCurrentAnswer(currentAnswer);
        increaseTest();
    }

    private void increaseTest() {
        indexQuiz++;
        initInterface();
    }

    private void decreaseTest() {
        indexQuiz--;
        initInterface();
    }

    private void initInterface() {

        if (indexQuiz < currentTest.getQuizList().size()) {
            currentQuiz = currentTest.getQuizList().get(indexQuiz);
            questionText.setText(currentQuiz.getQuestion());
            answ1.setText(currentQuiz.getAnswers().get(0).getAnswer());
            answ2.setText(currentQuiz.getAnswers().get(1).getAnswer());
            answ3.setText(currentQuiz.getAnswers().get(2).getAnswer());
        } else {
            indexQuiz--;
            currentTest.countResult();
            TestDAO testDAO = TestDAO.getInstance(this);
            testDAO.updateScore(currentTest);
            Intent intent = new Intent(this, VerdictActivity.class);
            intent.putExtra("Verdict", currentTest.getVerdict());
            intent.putExtra("score", currentTest.getResult());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (indexQuiz > 0) {
            decreaseTest();
        } else {
            super.onBackPressed();
        }
    }

    private Test getTest(int testId) {
        TestDAO testDAO = TestDAO.getInstance(this);
        Test test = testDAO.getTestById(testId);
        return test;
    }

}

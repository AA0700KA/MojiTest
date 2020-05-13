package java.devcolibri.itvdn.com.mojitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.devcolibri.itvdn.com.mojitest.R;
import java.devcolibri.itvdn.com.mojitest.dao.TestDAO;
import java.devcolibri.itvdn.com.mojitest.pojo.Answer;
import java.devcolibri.itvdn.com.mojitest.pojo.Quiz;
import java.devcolibri.itvdn.com.mojitest.pojo.Test;

public class AddTestActivity extends AppCompatActivity {

    private Test test;
    private EditText testName;
    private EditText questionName;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText data1;
    private EditText data2;
    private EditText data3;
    private EditText verdict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_test_layout);

        test = new Test();
        testName = findViewById(R.id.test_name);
        questionName = findViewById(R.id.question_name);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        verdict = findViewById(R.id.verdict);
    }

    public void addQuiz(View v) {
        try {
            String question = questionName.getText().toString();
            String ans1 = answer1.getText().toString();
            String ans2 = answer2.getText().toString();
            String ans3 = answer3.getText().toString();
            int score1 = Integer.parseInt(data1.getText().toString());
            int score2 = Integer.parseInt(data2.getText().toString());
            int score3 = Integer.parseInt(data3.getText().toString());
            Quiz quiz = new Quiz(question);
            Answer answ1 = new Answer(ans1, score1);
            Answer answ2 = new Answer(ans2, score2);
            Answer answ3 = new Answer(ans3, score3);
            quiz.addAnswer(answ1);
            quiz.addAnswer(answ2);
            quiz.addAnswer(answ3);
            test.addQuiz(quiz);
            questionName.setText("");
            answer1.setText("");
            answer2.setText("");
            answer3.setText("");
            data1.setText("0");
            data2.setText("0");
            data3.setText("0");
        } catch (Exception e) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTest(View v) {
        String name = testName.getText().toString();
        String result = verdict.getText().toString();
        test.setName(name);
        test.setVerdict(result);
        TestDAO testDAO = TestDAO.getInstance(this);
        testDAO.addTest(test);
        finish();
    }

}

package java.devcolibri.itvdn.com.mojitest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.devcolibri.itvdn.com.mojitest.R;

public class VerdictActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        String verdict = getIntent().getStringExtra("Verdict");
        int score = getIntent().getIntExtra("score", 0);
        TextView textView = findViewById(R.id.verdict);
        textView.setText(" Your score is " + score + " and you may result " + verdict);
    }

}

package java.devcolibri.itvdn.com.mojitest.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.devcolibri.itvdn.com.mojitest.R;
import java.devcolibri.itvdn.com.mojitest.adapter.TestAdpater;
import java.devcolibri.itvdn.com.mojitest.dao.TestDAO;
import java.devcolibri.itvdn.com.mojitest.pojo.Test;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Test> tests = initTests();

        TestAdpater adpater = new TestAdpater(this, tests);
        recyclerView.setAdapter(adpater);
    }

    public void exit(View view) {
        finish();
    }

    public void addTest(View view) {
        Intent intent = new Intent(this, AddTestActivity.class);
        startActivity(intent);
    }

    public List<Test> initTests() {
        TestDAO testDAO = TestDAO.getInstance(this);
        List<Test> tests = testDAO.getAll();
        return tests;
    }

}

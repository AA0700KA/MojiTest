package java.devcolibri.itvdn.com.mojitest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.devcolibri.itvdn.com.mojitest.R;
import java.devcolibri.itvdn.com.mojitest.activity.TestActivity;
import java.devcolibri.itvdn.com.mojitest.pojo.Test;
import java.util.List;

public class TestAdpater extends RecyclerView.Adapter<TestAdpater.TestViewHolder> {

    private List<Test> tests;
    private Context context;

    public TestAdpater(Context context, List<Test> tests) {
        this.tests = tests;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        TestViewHolder tvh = new TestViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder testViewHolder, int i) {
        final int index = i;
        testViewHolder.testText.setText(tests.get(i).getName());
        testViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked: " + tests.get(index).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("Test", tests.get(index).getId());
                context.startActivity(intent);
            }
        });
    }


    public static class TestViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView testText;

        public TestViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            testText = itemView.findViewById(R.id.test_name);
        }

    }

}

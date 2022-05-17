package org.mmapp.assignments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>LotteryActivity</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class LotteryActivity extends AppCompatActivity {

    private TextView resultView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = createLayout();

        Button btn = createButton();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeSet<Integer> numberList = generateLotteryNumbers();
                String numberString = prettyFormatNumbers(numberList);
                resultView.setText("Your lucky numbers are:\n" + numberString);
            }
        });
        ll.addView(btn);

        resultView = createTextView();
        ll.addView(resultView);

        setContentView(ll);
        configureActionBar();
    }
    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        String className = getClass().getSimpleName();
        actionBar.setTitle(className.substring(0, className.lastIndexOf("Activity")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(LotteryActivity.this, AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private LinearLayout createLayout() {
        LinearLayout ll = new LinearLayout(LotteryActivity.this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        return ll;
    }
    private Button createButton() {
        Button btn = new Button(LotteryActivity.this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                       ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setGravity(Gravity.CENTER);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTextSize(16.0f);
        btn.setTransformationMethod(null);
        btn.setText("Create my lucky numbers");
        return btn;
    }
    private TextView createTextView() {
        TextView resultView = new TextView(LotteryActivity.this);
        resultView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                              ViewGroup.LayoutParams.WRAP_CONTENT));
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        resultView.setTextSize(16.0f);
        resultView.setText("");
        return resultView;
    }

    private TreeSet<Integer> generateLotteryNumbers() {
        final int EXCLUSIVE_BOUND = 50;
        final int NUMBER_COUNT = 6;
        TreeSet<Integer> numberList = new TreeSet<>();

        Random randomGenerator = new Random();
        while(numberList.size() < NUMBER_COUNT) {
            int num = randomGenerator.nextInt(EXCLUSIVE_BOUND);
            if (!numberList.contains(num)) {
                numberList.add(num);
            }
        }
        return numberList;
    }
    private String prettyFormatNumbers(TreeSet<Integer> numberList) {
        final String DIVIDER = ", ";
        final int DIVIDER_LENGTH = DIVIDER.length();
        String numberString = "";
        for (int number : numberList) {
            numberString += number + DIVIDER;
        }
        return numberString.substring(0, numberString.length() - DIVIDER_LENGTH);
    }
}

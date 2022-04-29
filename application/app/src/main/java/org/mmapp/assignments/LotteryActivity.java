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

import java.util.Random;

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
                int[] numberArray = generateLotteryNumbers();
                numberArray = sortNumbers(numberArray);
                String numberString = prettyFormatNumbers(numberArray);
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

    private int[] generateLotteryNumbers() {
        final int EXCLUSIVE_BOUND = 50;
        int numberCount = 6;
        int[] numberArray = new int[numberCount];
        Random randomGenerator = new Random();
        for (int i = 0; i < numberCount; i++) {
            numberArray[i] = randomGenerator.nextInt(EXCLUSIVE_BOUND);
        }
        return numberArray;
    }

    private int[] sortNumbers(int[] numberArray) {
        quickSort(numberArray, 0, numberArray.length - 1);
        return numberArray;
    }

    private void quickSort (int[] array, int start, int end) {
        int newPartition;

        if (start < end) {
            newPartition = partition(array, start, end);
            quickSort(array, start, newPartition - 1);
            quickSort(array, newPartition + 1, end);
        }
    }
    private int partition(int array[], int start, int end) {

        int pivot = array[end];
        int j = start;

        // Keep placing elements less than the pivot element to the left
        for (int i=start; i<end; i++) {
            if (array[i] < pivot) {
                // Swap array[i] with array[j]
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                j++;
            }
        }
        // Place the pivot i.e array[end] at its final position and return the pivot index
        // for partitioning the array
        int tmp = array[j];
        array[j] = array[end];
        array[end] = tmp;

        return j;
    }

    private String prettyFormatNumbers(int[] numberArray) {
        final String DIVIDER = ", ";
        final int DIVIDER_LENGTH = DIVIDER.length();
        String numberString = "";
        for (int number : numberArray) {
            numberString += number + DIVIDER;
        }
        return numberString.substring(0, numberString.length() - DIVIDER_LENGTH);
    }
}

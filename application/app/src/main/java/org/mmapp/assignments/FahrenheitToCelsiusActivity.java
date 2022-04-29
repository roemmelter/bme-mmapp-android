package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;

import java.text.DecimalFormat;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>FahrenheitToCelsiusActivity</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class FahrenheitToCelsiusActivity extends AppCompatActivity {

    private TextView resultView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = createLayout();

        EditText inputField = createEditText();
        ll.addView(inputField);

        Button btn = createButton();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double degreesFahrenheit = getDegreesFahrenheit(inputField.getText().toString());
                double degreesCelsius = convertFahrenheitToCelsius(degreesFahrenheit);
                resultView.setText(new DecimalFormat("#0.0").format(degreesCelsius) + "°C");
            }
        });
        ll.addView(btn);

        resultView = createTextView();
        ll.addView(resultView);

        setContentView(ll);

        ActionBar actionBar = getSupportActionBar();
        String className = getClass().getSimpleName();
        actionBar.setTitle(className.substring(0, className.lastIndexOf("Activity")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(FahrenheitToCelsiusActivity.this, AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private double getDegreesFahrenheit(String inputString) {
        return Double.parseDouble(inputString);
    }

    private double convertFahrenheitToCelsius(double degreesFahrenheit) {
        double degreesCelsius = (5.0 / 9.0) * (degreesFahrenheit - 32.0);
        return degreesCelsius;
    }

    private LinearLayout createLayout() {
        LinearLayout ll = new LinearLayout(FahrenheitToCelsiusActivity.this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,0,10,0);
        return ll;
    }
    private EditText createEditText() {
        EditText inputField = new EditText(FahrenheitToCelsiusActivity.this);
        inputField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inputField.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        inputField.setHint("Please enter degrees in Fahrenheit...");
        return inputField;
    }
    private Button createButton() {
        Button btn = new Button(FahrenheitToCelsiusActivity.this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTransformationMethod(null);
        btn.setText("Convert");
        return btn;
    }
    private TextView createTextView() {
        TextView resultView = new TextView(FahrenheitToCelsiusActivity.this);
        resultView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                              ViewGroup.LayoutParams.WRAP_CONTENT));
        resultView.setPadding(10,0,10,0);
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        resultView.setTextSize(16.0f);
        resultView.setText("");
        return resultView;
    }
}

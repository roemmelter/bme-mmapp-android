package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.MainActivity;
import org.mmapp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>TerminalActivity</b><br>
 * Desc.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class TerminalActivity extends AppCompatActivity {

    private final String PLACEHOLDER_COMMAND = "ls -al /system/app";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] commandArr= new String[] {
                "ps", "pwd", "cat /proc/cpuinfo",
                "ls /storage/emulated/0",
                "cd /storage/emulated/0", "whoami" };

        LinearLayout ll = createLayout();
        final AutoCompleteTextView inputField = createAutoCompleteTextView();
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this,
                                                                android.R.layout.simple_spinner_item, commandArr);
        inputField.setAdapter(arrAdapter);
        ll.addView(inputField);
        final TextView resultView = createTextView();

        Button executeBtn = createButton();
        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String command = inputField.getText().toString();
                String output = execute(command);
                resultView.setText(output);
            }
        });
        ll.addView(executeBtn);

        ll.addView(resultView);

        setContentView(ll);
        configureActionBar();
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        String    className = getClass().getSimpleName();
        actionBar.setTitle(className.substring(0, className.lastIndexOf("Activity")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(TerminalActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private LinearLayout createLayout() {
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getColor(R.color.LightBlue));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        return ll;
    }
    private AutoCompleteTextView createAutoCompleteTextView() {
        AutoCompleteTextView autoComView = new AutoCompleteTextView(this);
        autoComView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                              ViewGroup.LayoutParams.WRAP_CONTENT));
        autoComView.setBackgroundColor(Color.BLACK);
        autoComView.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
        autoComView.setHint(PLACEHOLDER_COMMAND);
        autoComView.setHintTextColor(getResources().getColor(R.color.Honeydew));
        autoComView.setInputType(InputType.TYPE_CLASS_TEXT);
        autoComView.setPadding(0, 20, 0, 10);
        autoComView.setTextSize(16.0f);
        autoComView.setTextColor(Color.WHITE);
        return autoComView;
    }
    private Button createButton() {
        Button btn = new Button(this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                       ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setGravity(Gravity.CENTER);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTextSize(16.0f);
        btn.setTransformationMethod(null);
        btn.setText("Run");
        return btn;
    }
    private TextView createTextView() {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                              ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setBackgroundColor(Color.BLACK);
        textView.setGravity(Gravity.START);
        textView.setHorizontalScrollBarEnabled(true);
        textView.setVerticalScrollBarEnabled(true);
        textView.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
        textView.setTextSize(16.0f);
        textView.setText("");
        textView.setTextColor(Color.WHITE);
        textView.setMovementMethod(new ScrollingMovementMethod());
        return textView;
    }

    private String execute(String command) {
        StringBuffer outputBuffer = new StringBuffer();
        Process p;
        try {
            if (command.equals("")) {
                command = PLACEHOLDER_COMMAND;
            }
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            do {
                line = reader.readLine();
                outputBuffer.append(line + "\n");
            }
            while (line != null);


        } catch (Exception ex) {
            return ex.toString();
        }

        return outputBuffer.toString();
    }
}

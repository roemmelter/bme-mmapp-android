package org.mmapp.assignments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.MainActivity;
import org.mmapp.R;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>MainActivity</b><br>
 * The activity creates a button for each Activity in ACTIVITY_LIST.
 * The pressed button starts on click the labeled activity.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class AssignmentsOverviewActivity extends AppCompatActivity {

    private final String PACKAGE_BASE = "org.mmapp.";
    private final String PACKAGE_ASSIGNMENTS = "assignments.";
    private final String PACKAGE_LABS = "labs.";
    private final String[] ACTIVITY_LIST = {
            PACKAGE_LABS + "LabsOverview",
            // Assignment 1
            PACKAGE_ASSIGNMENTS + "StateSwitching",
            PACKAGE_ASSIGNMENTS + "RecordVideo",
            PACKAGE_ASSIGNMENTS + "NumpadDemo",
            // Assignment 2
            PACKAGE_ASSIGNMENTS + "GoogleSearchResults",
            PACKAGE_ASSIGNMENTS + "Lottery",
            PACKAGE_ASSIGNMENTS + "FahrenheitToCelsius",
            // Assignment 3
            PACKAGE_ASSIGNMENTS + "RandomSquares",
            PACKAGE_ASSIGNMENTS + "Mondrian",
            PACKAGE_ASSIGNMENTS + "Terminal",
            PACKAGE_ASSIGNMENTS + "Wildbienen",
            PACKAGE_ASSIGNMENTS + "Breakout",
            // Assignment 4
            PACKAGE_ASSIGNMENTS + "SnowFlakes",
            // Assignment 5
            // Assignment 6
    };
    private final String ACTIVITY_STRING = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(AssignmentsOverviewActivity.this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,10,10,10);

        for (String activity : ACTIVITY_LIST) {
            ll.addView(createButtonForActivity(this, activity));
        }

        scrollView.addView(ll);
        setContentView(scrollView);

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
                startActivity(new Intent(AssignmentsOverviewActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    protected Button createButtonForActivity(Context ctx, String activity) {
        String btnLabel = activity.substring(activity.lastIndexOf(".")+1);
        Button btn = new Button(ctx);
        btn.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btn.setTransformationMethod(null);
        btn.setText(btnLabel);
        btn.setPadding(0,0,0,0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                               "Button '" + btnLabel + "' was clicked",
                               Toast.LENGTH_SHORT)
                    .show();
                try {
                    Class<?> classToStart =
                            Class.forName(PACKAGE_BASE + activity + ACTIVITY_STRING);
                    Intent intent = new Intent(ctx, classToStart);
                    startActivity(intent);
                } catch (ClassNotFoundException exception) {
                    Log.e(getResources().getString(R.string.tag), exception.getException().toString());
                    Toast.makeText(v.getContext(),
                                   "Activity '" + btnLabel + "' not found.",
                                   Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });
        return btn;
    }
}

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
import org.mmapp.util.ConfigActionBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
    private final String ACTIVITY_STRING = "Activity";
    private ConfigActionBar configActionBar;

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

        ArrayList<String> activityList = new ArrayList<>();
        activityList = fillActivityList(activityList);
        for (String activity : activityList) {
            ll.addView(createButtonForActivity(this, activity));
        }
        scrollView.addView(ll);

        setContentView(scrollView);
        configActionBar = new ConfigActionBar(MainActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> fillActivityList(ArrayList<String> arrayList) {
        String packageName = PACKAGE_LABS;
        arrayList.add(packageName + "LabsOverview");
        packageName = PACKAGE_ASSIGNMENTS;
        // Assignment 1
        arrayList.add(packageName + "StateSwitching");
        arrayList.add(packageName + "RecordVideo");
        arrayList.add(packageName + "NumpadDemo");
        // Assignment 2
        arrayList.add(packageName + "GoogleSearchResults");
        arrayList.add(packageName + "Lottery");
        arrayList.add(packageName + "FahrenheitToCelsius");
        // Assignment 3
        arrayList.add(packageName + "RandomSquares");
        arrayList.add(packageName + "Mondrian");
        arrayList.add(packageName + "Terminal");
        arrayList.add(packageName + "Wildbienen");
        arrayList.add(packageName + "Breakout");
        // Assignment 4
        arrayList.add(packageName + "Snowflakes");
        // Assignment 5
        // Assignment 6
        return arrayList;
    }

    private Button createButtonForActivity(Context ctx, String activity) {
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

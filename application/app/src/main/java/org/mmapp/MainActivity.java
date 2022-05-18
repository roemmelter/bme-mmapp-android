package org.mmapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
public class MainActivity extends AppCompatActivity {
    private Map<String, Integer> activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,10,10,10);

        initializeActivityList();
        for (Map.Entry<String, Integer> entry : activityList.entrySet()) {
            ll.addView(createButtonForActivity(this, entry.getKey(), entry.getValue()));
        }
        setContentView(ll);
    }

    private void initializeActivityList() {
        final String PACKAGE_BASE = "org.mmapp.";
        activityList = new TreeMap<String, Integer>();
        activityList.put(PACKAGE_BASE + "assignments.AssignmentsOverview",
                         getResources().getColor(R.color.LightSkyBlue));
        activityList.put(PACKAGE_BASE + "labs.LabsOverview",
                         getResources().getColor(R.color.Bisque));
    }

    protected Button createButtonForActivity(Context ctx, String activity, int backgroundColorId) {
        String btnLabel = activity.substring(activity.lastIndexOf(".") + 1);
        Button btn = new Button(ctx);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        btn.setLayoutParams(layoutParams);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTransformationMethod(null);
        btn.setText(btnLabel);
        btn.setTextSize(20.0f);
        btn.setPadding(0,0,0,0);
        btn.setBackgroundColor(backgroundColorId);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<?> classToStart = Class.forName(activity + "Activity");
                    Intent intent = new Intent(ctx, classToStart);
                    startActivity(intent);
                } catch (ClassNotFoundException exception) {
                    Log.e("MainActivity", exception.getException().toString());
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

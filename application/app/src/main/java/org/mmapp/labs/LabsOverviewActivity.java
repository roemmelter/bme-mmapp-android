package org.mmapp.labs;

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
import org.mmapp.assignments.AssignmentsOverviewActivity;
import org.mmapp.util.ConfigActionBar;

import java.util.ArrayList;

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
public class LabsOverviewActivity extends AppCompatActivity {

    private final String ACTIVITY_STRING = "Activity";
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(LabsOverviewActivity.this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,10,10,10);

        ArrayList<String> activityList = initActivityList();
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
    private ArrayList<String> initActivityList() {
        ArrayList<String> arrayList = new ArrayList<>();
        String packageName = getResources().getString(R.string.package_assignments) + ".";
        arrayList.add(packageName + "AssignmentsOverview");
        packageName = getResources().getString(R.string.package_labs) + ".";
        arrayList.add(packageName + "About");
        arrayList.add(packageName + "BorderLayout");
        arrayList.add(packageName + "Button");
        arrayList.add(packageName + "Calculator");
        arrayList.add(packageName + "Calendar");
        arrayList.add(packageName + "Dialog");
        arrayList.add(packageName + "Geo");
        arrayList.add(packageName + "Menu");
        arrayList.add(packageName + "Rotation");
        arrayList.add(packageName + "ScreenDimensions");
        arrayList.add(packageName + "Simple");
        arrayList.add(packageName + "SMS");
        return arrayList;
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
                try {
                    String packageBase = getResources().getString(R.string.package_base);
                    Class<?> classToStart =
                            Class.forName(packageBase + activity + ACTIVITY_STRING);
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

package org.mmapp.labs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.MainActivity;
import org.mmapp.R;
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
    private final String SEPARATOR_STRING = "separator";
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
        int counter = 1;
        for (String activity : activityList) {
            if (activity.equals(SEPARATOR_STRING)) {
                createSeparator(ll,this, counter);
                counter++;
            } else {
                ll.addView(createButtonForActivity(this, activity));
            }
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
        // Lab 1
        arrayList.add(SEPARATOR_STRING);
        arrayList.add(packageName + "Barcode");
        arrayList.add(packageName + "Button");
        arrayList.add(packageName + "Calendar");
        arrayList.add(packageName + "Email");
        arrayList.add(packageName + "Geo");
        arrayList.add(packageName + "Rotation");
        arrayList.add(packageName + "Simple");
        arrayList.add(packageName + "SMS");
        // Lab 2
        arrayList.add(SEPARATOR_STRING);
        arrayList.add(packageName + "About");
        arrayList.add(packageName + "BorderLayout");
        arrayList.add(packageName + "Calculator");
        arrayList.add(packageName + "Dialog");
        arrayList.add(packageName + "Menu");
        arrayList.add(packageName + "ScreenDimensions");
        // Lab 3
        arrayList.add(SEPARATOR_STRING);
        // Lab 4
        arrayList.add(SEPARATOR_STRING);
        // Lab 5
        arrayList.add(SEPARATOR_STRING);
        // Lab 6
        arrayList.add(SEPARATOR_STRING);
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
    private void createSeparator(LinearLayout ll, Context context, int counter) {
        final String TEXT_LABEL = "Lab ";
        TextView     tv         = new TextView(context);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                      ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.START);
        tv.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        tv.setTextSize(16.0f);
        tv.setText(TEXT_LABEL + counter);
        ll.addView(tv);

        View divider = new View(context);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f,
                                                     getResources().getDisplayMetrics());
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        divider.setLayoutParams(params);
        int[]    dividerAttrs    = { android.R.attr.listDivider };
        Drawable dividerDrawable = getTheme().obtainStyledAttributes(dividerAttrs).getDrawable(0);
        divider.setBackground(dividerDrawable);
        ll.addView(divider);
    }
}

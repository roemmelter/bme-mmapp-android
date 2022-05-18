package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>NumpadDemoActivity</b><br>
 * This activity starts the NumpadActivity. After a button has been pressed in the started activity,
 * the transmitted values are displayed as text in the middle of this activity.
 * </p><br>
 * @author Erik Roemmelt
 */
public class NumpadDemoActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    private TextView tv;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int btnId = 42;
        Intent intent = new Intent(getApplicationContext(), NumpadActivity.class);
        intent.putExtra("id", btnId);

        createLinearLayoutWithTextView("Button ID: " + btnId);
        setContentView(ll);

        startActivityForResult(intent, 1);

        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int id = data.getIntExtra("id", -1);
                int num = data.getIntExtra("num", -1);
                Log.i("NumpadDemoActivity", "ButtonId: " + id + ", ButtonNumber: " + num);
                tv.setText(String.format("ButtonId: %d; ButtonNumber: %d", id, num));

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Action canceled.",
                               Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createLinearLayoutWithTextView(String textViewContent) {
        final LinearLayout.LayoutParams paramsMatchParent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        ll = new LinearLayout(this);
        ll.setLayoutParams(paramsMatchParent);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));

        tv = new TextView(this);
        tv.setLayoutParams(paramsMatchParent);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        tv.setText(textViewContent);

        ll.addView(tv);
    }
}

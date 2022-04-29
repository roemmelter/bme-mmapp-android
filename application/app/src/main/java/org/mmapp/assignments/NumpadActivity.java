package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.MainActivity;
import org.mmapp.R;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>NumpadActivity</b><br>
 * This activity is started by the NumpadDemoActivity. Several buttons with numbers are created
 * in the onCreate method. By activating one of these buttons, the ID and the display text
 * are transmitted to the NumpadDemoActivity via intent.
 * </p><br>
 * @author Erik Roemmelt
 */
public class NumpadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int[] NUMBERS = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        final int COLUMNS = 3;

        TableLayout tbl = new TableLayout(this);
        tbl.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tbl.setGravity(Gravity.CENTER_VERTICAL);
        tbl.setBackgroundColor(getResources().getInteger(R.integer.white_bg));


        TableRow tr = null;
        TableLayout.LayoutParams tblParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams trParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        for (int number : NUMBERS) {
            if ((number-1) % COLUMNS == 0) {
                tr = new TableRow(this);
                tr.setLayoutParams(tblParams);
                tr.setGravity(Gravity.CENTER_HORIZONTAL);
                tbl.addView(tr);
            }

            createButton(Integer.toString(number), tr, trParams);
        }

        setContentView(tbl);

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
                startActivity(new Intent(NumpadActivity.this, AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void createButton(String btnLabel, ViewGroup layout, ViewGroup.LayoutParams params) {
        Button btn = new Button(layout.getContext());
        btn.setLayoutParams(params);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setText(btnLabel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Button) v).getId() != -1
                         ? ((Button) v).getId()
                         : Integer.parseInt(btnLabel);
                int num = Integer.parseInt(btnLabel);
                Intent intent = new Intent();
                Log.i("NumpadActivity", "btnId: " + id + ", btnNum: " + num);
                intent.putExtra("id", id);
                intent.putExtra("num", num);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        layout.addView(btn);
    }
}

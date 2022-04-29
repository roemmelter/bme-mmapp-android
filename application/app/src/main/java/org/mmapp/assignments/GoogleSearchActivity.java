package org.mmapp.assignments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.mmapp.MainActivity;
import org.mmapp.R;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>GoogleSearchActivity</b><br>
 * Description
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class GoogleSearchActivity extends AppCompatActivity {

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
            Toast.makeText(GoogleSearchActivity.this,
                           "Button \"Search\" was clicked",
                           Toast.LENGTH_SHORT)
                .show();
            Intent intent = new Intent();
            intent.putExtra("query", inputField.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            }
        });
        ll.addView(btn);

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
                startActivity(new Intent(GoogleSearchActivity.this,
                                         AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private LinearLayout createLayout() {
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,10,10,10);
        return ll;
    }
    private EditText createEditText() {
        EditText inputField = new EditText(this);
        inputField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inputField.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        inputField.setHint("Enter search query...");
        return inputField;
    }
    private Button createButton() {
        Button btn = new Button(this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTransformationMethod(null);
        btn.setText("Search TH Nürnberg");
        btn.setPadding(0,0,0,0);
        return btn;
    }
}

package org.mmapp.labs;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>BorderLayoutActivity</b><br>
 * The activity creates a border-layout example with a north, west,
 * center, east and south labeled text view.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class BorderLayoutActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_border_layout);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
}

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
 * <b>AboutActivity</b><br>
 * Activity use a scroll view layout to display
 * an introduction text about the game TicTacToe.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class AboutActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
}

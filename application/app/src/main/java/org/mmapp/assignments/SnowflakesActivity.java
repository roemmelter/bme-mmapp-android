package org.mmapp.assignments;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>SnowflakesActivity</b><br>
 * Draws vertical falling Koch snowflakes on the display.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class SnowflakesActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SnowflakeView(this));

        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        try {
            return super.onOptionsItemSelected(item);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}

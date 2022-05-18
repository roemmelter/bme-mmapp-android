package org.mmapp.labs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 *
 * RotationActivity
 *
 * @author Erik Roemmelt
 */
public class RotationActivity extends AppCompatActivity {
    private final String TAG = "RotationActivity";
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}

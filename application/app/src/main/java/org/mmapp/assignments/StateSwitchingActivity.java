package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>StateSwitchingActivity</b><br>
 * This activity indicates all its life cycle states in the logcat as soon as they are triggered.
 * </p><br>
 * @author Erik Roemmelt
 */
public class StateSwitchingActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getResources().getString(R.string.tag), "onCreate()");
        super.onCreate(savedInstanceState);
        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Log.i(getResources().getString(R.string.tag), "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(getResources().getString(R.string.tag), "onResume()");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i(getResources().getString(R.string.tag), "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(getResources().getString(R.string.tag), "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(getResources().getString(R.string.tag), "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(getResources().getString(R.string.tag), "onDestroy()");
        super.onDestroy();
    }
}

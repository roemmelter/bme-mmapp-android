package org.mmapp.util;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>ConfigActionBar</b><br>
 *
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class ConfigActionBar {
    private ActionBar         _actionBar;
    private Class<?> _overviewActivity;
    private AppCompatActivity _currentActivity;

    public ConfigActionBar(Class<?> overViewActivity,
                           AppCompatActivity currentActivity,
                           boolean isBackButtonEnabled) {
        _currentActivity = currentActivity;
        if (currentActivity != null) {
            _actionBar = currentActivity.getSupportActionBar();
            if (overViewActivity != null) {
                setOverviewActivity(overViewActivity);
                setBackButtonEnabled(isBackButtonEnabled);
                setTitleToActivityName();
            }
        }
    }
    public void setActionBackButton(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            try {
                _currentActivity.finish();
                _currentActivity.startActivity(new Intent(_currentActivity, _overviewActivity));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void setTitle(String title) {
        _actionBar.setTitle(title);
    }
    public String getTitle() {
        return (String) _actionBar.getTitle();
    }

    private void setOverviewActivity(Class<?> overViewActivity) {
        _overviewActivity = overViewActivity;
    }
    private void setBackButtonEnabled(boolean isBackButtonEnabled) {
        if (_actionBar != null) {
            _actionBar.setDisplayHomeAsUpEnabled(isBackButtonEnabled);
        }
    }
    private void setTitleToActivityName() {
        String className = _currentActivity.getClass().getSimpleName();
        _actionBar.setTitle(className.substring(0, className.lastIndexOf("Activity")));
    }
}
// Example of usage
// private ConfigActionBar configActionBar;
// ...onCreate() {
//     configActionBar = new ConfigActionBar(...OverviewActivity.class, this, true);
// }
// public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//     configActionBar.setActionBackButton(item);
//     return super.onOptionsItemSelected(item);
// }

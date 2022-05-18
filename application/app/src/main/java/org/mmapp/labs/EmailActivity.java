package org.mmapp.labs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 *
 * EmailActivity
 *
 * @author Erik Roemmelt
 */
public class EmailActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto",
                        "ralph@lano.de",
                        null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Book - Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Great book!");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
}

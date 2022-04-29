package org.mmapp.assignments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
public class GoogleSearchResultsActivity extends AppCompatActivity {

    private WebView resultsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultsView = createWebView();
        setContentView(resultsView);

        startActivityForResult(new Intent(getApplicationContext(), GoogleSearchActivity.class), 1);

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
                startActivity(new Intent(GoogleSearchResultsActivity.this,
                                         AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final String URI = "http://www.google.com/search?q=";
        final String SITE_RESTRICTION = "site%3Ath-nuernberg.de+";

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String query = data.getStringExtra("query");
                resultsView.loadUrl(URI + SITE_RESTRICTION + query);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Action canceled.",
                               Toast.LENGTH_SHORT).show();
            }
        }
    }

    private WebView createWebView() {
        WebView resultsView = new WebView(this);
        resultsView.getSettings().setJavaScriptEnabled(true);
        resultsView.setWebViewClient(new WebViewClient());
        return resultsView;
    }
}

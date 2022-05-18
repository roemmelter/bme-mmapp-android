package org.mmapp.labs;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 *
 * ButtonActivity
 *
 * @author Erik Roemmelt
 */
public class ButtonActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(0x200000FF);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn = new Button(this);
        btn.setTypeface(Typeface.create(
            "sans-serif", Typeface.NORMAL));
        btn.setText("Click Me!");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "Button was clicked",
                        Toast.LENGTH_SHORT)
                .show();
            }
        });
        ll.addView(btn);

        Button btnWeb = new Button(this);
        btnWeb.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btnWeb.setText("uri: www.variationenzumthema.de");
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "ButtonWeb was clicked",
                        Toast.LENGTH_SHORT)
                .show();
                Uri uri = Uri.parse("http://www.variationenzumthema.de/");
                Intent gotoUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gotoUri);
            }
        });
        ll.addView(btnWeb);

        Button btnTel = new Button(this);
        btnTel.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btnTel.setText("tel: +49 123 456 7890");
        btnTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "ButtonTel was clicked",
                        Toast.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse("tel:+49 123 456 7890");
                Intent gotoUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gotoUri);
            }
        });
        ll.addView(btnTel);

        Button btnYoutube = new Button(this);
        btnYoutube.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btnYoutube.setText("Youtube: Android vs. iPhone War (2016)");
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "ButtonYoutube was clicked",
                        Toast.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse("http://youtu.be/Cv7CJg8pSIM");
                Intent gotoUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gotoUri);
            }
        });
        ll.addView(btnYoutube);

        Button btnGoogleMaps = new Button(this);
        btnGoogleMaps.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btnGoogleMaps.setText("GoogleMaps: Campus Bib - TH Nürnberg");
        btnGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "ButtonGoogleMaps was clicked",
                        Toast.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse("https://goo.gl/maps/"+
                        "y5HUpYeG1uuPcVM67");
                Intent gotoUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gotoUri);
            }
        });
        ll.addView(btnGoogleMaps);

        Button btnOpenMap = new Button(this);
        btnOpenMap.setTypeface(Typeface.create(
                "sans-serif", Typeface.NORMAL));
        btnOpenMap.setText("OpenStreetMap: Campus Bib - TH Nürnberg");
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "ButtonOpenMap was clicked",
                        Toast.LENGTH_SHORT)
                        .show();
                Uri uri = Uri.parse("http://osm.org/?lat=49.45234&lon=11.09346&zoom=20");
                Intent gotoUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gotoUri);
            }
        });
        ll.addView(btnOpenMap);

        setContentView(ll);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
}

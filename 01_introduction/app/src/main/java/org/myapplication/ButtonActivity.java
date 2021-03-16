package org.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonActivity extends AppCompatActivity {

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

        setContentView(ll);
    }
}
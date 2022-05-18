package org.mmapp.labs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 *
 * BarcodeActivity
 *
 * @author Erik Roemmelt
 */
public class BarcodeActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(Color.parseColor("#20FFFFFF"));
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn = new Button(this);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTransformationMethod(null);
        btn.setText("Scan & Get Barcode Information");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBarcodeScanner = new Intent("con.google.zxing.client.android.SCAN");
                gotoBarcodeScanner.setPackage("com.google.zxing.client.android");
                gotoBarcodeScanner.putExtra("SCAN_MODE", "QR_CODE_MODE");
                if (gotoBarcodeScanner.resolveActivityInfo(getPackageManager(), 0) != null) {
                    startActivityForResult(gotoBarcodeScanner, 0);
                } else {
                    Toast.makeText(v.getContext(), "Package/App not installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll.addView(btn);

        tv = new TextView(this);
        tv.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        tv.setText("");
        ll.addView(tv);

        setContentView(ll);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                tv.setText("Contents: " + contents + ", Format: " + format);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Scan was canceled.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

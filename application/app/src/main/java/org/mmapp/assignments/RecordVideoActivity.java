package org.mmapp.assignments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>RecordVideoActivity</b><br>
 * This activity passes the user to standard video recording activity and
 * upon return shows the path to the video file.
 * </p><br>
 * @author Erik Roemmelt
 */
public class RecordVideoActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    private TextView tvPathToVideo;
    private final String labelTVPathToVideo = "Path to Video: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Typeface regularSansSerif = Typeface.create("sans-serif", Typeface.NORMAL);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btnRecordVideo = createButton(this, regularSansSerif, "Record Video",
                 new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gotoVideoCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        if (gotoVideoCapture.resolveActivityInfo(getPackageManager(), 0) != null) {
                            startActivityForResult(gotoVideoCapture, 1);
                        } else {
                            Toast.makeText(v.getContext(), "Package/App not installed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        ll.addView(btnRecordVideo);

        tvPathToVideo = createTextView(this, regularSansSerif, String.format("%s-", labelTVPathToVideo));
        ll.addView(tvPathToVideo);

        setContentView(ll);
        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this,
                                              true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                Uri pathToVideo = data.getData();
                tvPathToVideo.setText(String.format("%s%s", labelTVPathToVideo, pathToVideo));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,
                        "Recording was canceled.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Button createButton(Context context, Typeface typeface, String label, View.OnClickListener onClickListenerMethod) {
        Button btn = new Button(context);
        btn.setTypeface(typeface);
        btn.setTransformationMethod(null); // disables capitalization of all letters
        btn.setText(label);
        btn.setOnClickListener(onClickListenerMethod);
        return btn;
    }

    private TextView createTextView(Context context, Typeface typeface, String label) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setTypeface(typeface);
        tv.setText(label);
        return tv;
    }
}

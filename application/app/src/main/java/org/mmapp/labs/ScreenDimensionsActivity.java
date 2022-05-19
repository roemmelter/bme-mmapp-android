package org.mmapp.labs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.assignments.LotteryActivity;
import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>ScreenDimensionsActivity</b><br>
 * Display real screen dimension that is available.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class ScreenDimensionsActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = createLayoutContainer(this);
        TextView tv = createTextView(this);
        ll.addView(tv);

        tv.setText(calculateScreenDimensions());
        setContentView(ll);

        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
    private LinearLayout createLayoutContainer(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(Color.WHITE);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        return ll;
    }
    private TextView createTextView(Context context) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                      ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.START);
        tv.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        tv.setTextSize(16.0f);
        tv.setTextColor(Color.BLACK);
        tv.setText("");
        return tv;
    }

    private String calculateScreenDimensions() {
        String msg = "";
        // Display size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        msg += "Display size: w=" + size.x + ", h=" + size.y;
        // status bar
        int statusBarHeight = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight =
                    getResources().getDimensionPixelSize(resourceId);
        }
        msg += "\nStatus Bar: h=" + statusBarHeight;
        // action bar
        int actionBarHeight = 0;
        final TypedArray styledAttributes =
                this.getTheme().obtainStyledAttributes(
                        new int[] { android.R.attr.actionBarSize });
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        msg += "\nAction Bar: h=" + actionBarHeight;
        // navigation bar
        int navigationBarHeight = 0;
        resourceId = getResources()
                .getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight =
                    getResources().getDimensionPixelSize(resourceId);
        }
        msg += "\nNavigation Bar: h=" + navigationBarHeight;
        // real size
        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            msg += "\n\nReal display size: w=" + size.x + ", h= " + (size.y - statusBarHeight - actionBarHeight - navigationBarHeight);
        } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            msg += "\n\nReal display size: w=" + (size.x - navigationBarHeight) + ", h= " + (size.y - statusBarHeight - actionBarHeight);
        }
        return msg;
    }
}

package org.mmapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.util.TreeMap;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>ScreenUtility</b><br>
 * Utility class to calculate various screen dimensions.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class ScreenUtility {
    private final TreeMap<String, Integer> _DIMEN_MAP = new TreeMap<String, Integer>();
    private Context   _context;
    private Resources _resources;

    public ScreenUtility(Context context) {
        this._context = context;
        init();
    }


    /**
     * Get the width of the screen.
     * @return int screenWidth in pixels
     */
    public int getScreenWidth() {
        return _DIMEN_MAP.get("screenWidth");
    }
    /**
     * Get the height of the screen.
     * @return int screenHeight in pixels
     */
    public int getScreenHeight() {
        return _DIMEN_MAP.get("screenHeight");
    }
    /**
     * Get the size of the screen dimension.
     * @return Point x= screenWidth in pixels, y= screenHeight in pixels
     */
    public Point getScreenSize() {
        return new Point(_DIMEN_MAP.get("screenWidth"), _DIMEN_MAP.get("screenHeight"));
    }
    /**
     * Get the height of the status bar.
     * @return int statusBarHeight in pixels
     */
    public int getStatusBarHeight() {
        return _DIMEN_MAP.get("statusBarHeight");
    }
    /**
     * Get the height of the action bar.
     * @return int actionBarHeight in pixels
     */
    public int getActionBarHeight() {
        return _DIMEN_MAP.get("actionBarHeight");
    }
    /**
     * Get the height of the navigation bar.
     * @return int navigationBarHeight in pixels
     */
    public int getNavigationBarHeight() {
        return _DIMEN_MAP.get("navigationBarHeight");
    }
    /**
     * Get the width of the real screen dimension (usable screen size).
     * @return int realScreenWidth in pixels
     */
    public int getRealScreenWidth() {
        return _DIMEN_MAP.get("realScreenWidth");
    }
    /**
     * Get the height of the real screen dimension (usable screen size).
     * @return int realScreenHeight in pixels
     */
    public int getRealScreenHeight() {
        return _DIMEN_MAP.get("realScreenHeight");
    }
    /**
     * Get the size of the real screen dimension (usable screen size).
     * @return Point x= realScreenWidth in pixels, y= realScreenHeight in pixels
     */
    public Point getRealScreenSize() {
        return new Point(_DIMEN_MAP.get("realScreenWidth"), _DIMEN_MAP.get("realScreenHeight"));
    }

    public String toString() {
        return _DIMEN_MAP.toString();
    }

    // Private methods
    private void init() {
        initContextResources();
        identifyScreenRotation();
        calculateDisplaySize();
        calculateStatusBarHeight();
        calculateActionBarHeight();
        calculateNavigationBarHeight();
        calculateRealScreenSize();
        //calculateScreenDensity();
    }

    private void initContextResources() {
        _resources = _context.getResources();
    }
    private void identifyScreenRotation() {
        _DIMEN_MAP.put("screenOrientation", _resources.getConfiguration().orientation);
    }
    private void calculateDisplaySize() {
        Point   size    = new Point();
        Display display = ((Activity) _context).getWindowManager().getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        display.getMetrics(displayMetrics);
//        Log.d("M_", "Display Metrics: "+displayMetrics.widthPixels +", "+displayMetrics.heightPixels);
//        display.getRealMetrics(displayMetrics);
//        Log.d("M_",
//              "Display Real Metrics: "+displayMetrics.widthPixels +", "+displayMetrics.heightPixels);
        display.getRealSize(size);
//        Log.d("M_", "Display Size: "+size.x+", "+size.y);
//        int w = _context.getResources().getDisplayMetrics().widthPixels;
//        int h = _context.getResources().getDisplayMetrics().heightPixels;
//        Log.d("M_", "Display Resource Metrics: "+w+", "+h);
        _DIMEN_MAP.put("screenWidth", size.x);
        _DIMEN_MAP.put("screenHeight", size.y);
    }
    private void calculateStatusBarHeight() {
        int resourceId = _resources
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            _DIMEN_MAP.put("statusBarHeight", _resources.getDimensionPixelSize(resourceId));
//            Log.d("M_", "StatusBar Height: "+_resources.getDimensionPixelSize(resourceId));
        }
    }
    private void calculateActionBarHeight() {
        final TypedArray styledAttributes = _context.getTheme().obtainStyledAttributes(
                        new int[] { android.R.attr.actionBarSize });
        int actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        _DIMEN_MAP.put("actionBarHeight", actionBarHeight);
//        Log.d("M_", "ActionBar Height: "+actionBarHeight);
    }
    private void calculateNavigationBarHeight() {
        int resourceId = _resources
                .getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            _DIMEN_MAP.put("navigationBarHeight", _resources.getDimensionPixelSize(resourceId));
//            Log.d("M_", "NavigationBar Height: "+_resources.getDimensionPixelSize(resourceId));
        }
    }
    private void calculateRealScreenSize() {
        int screenOrientation = _DIMEN_MAP.get("screenOrientation");
        int width = 0;
        int height = 0;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            width = _DIMEN_MAP.get("screenWidth");
            height = _DIMEN_MAP.get("screenHeight") - _DIMEN_MAP.get("statusBarHeight")
                    - _DIMEN_MAP.get("actionBarHeight") - _DIMEN_MAP.get("navigationBarHeight");
        } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = _DIMEN_MAP.get("screenWidth") - _DIMEN_MAP.get("navigationBarHeight");
            height = _DIMEN_MAP.get("screenHeight") - _DIMEN_MAP.get("statusBarHeight")
                     - _DIMEN_MAP.get("actionBarHeight");
        }
//        Log.d("M_", "Display RealSize: "+width+", "+height);
        _DIMEN_MAP.put("realScreenWidth", width);
        _DIMEN_MAP.put("realScreenHeight", height);
    }
    private void calculateScreenDensity() {
        Display display = ((Activity) _context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        _DIMEN_MAP.put("screenDensity", (int) (displayMetrics.density * 100));
        Log.d("M_", "Screen Density: " + (int) (displayMetrics.density * 100) );
    }
}

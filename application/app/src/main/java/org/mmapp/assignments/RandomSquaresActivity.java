package org.mmapp.assignments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

import java.util.Random;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>RandomSquares</b><br>
 * Draw random sized, positioned and colored rectangles.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class RandomSquaresActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(RandomSquaresActivity.this));
        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    class GraphicsView extends View {

        private final int FPS       = 24;
        private final int BASE_SIZE = 40;

        private Random rgen = new Random();
        private Paint  paintStroke;
        private Paint  paintFill;
        private Bitmap bitmap;
        private Canvas bitmapCanvas;

        public GraphicsView(Context context) {
            super(context);
            paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintStroke.setColor(Color.BLACK);
            paintStroke.setStyle(Paint.Style.STROKE);
            paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintFill.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            if (bitmap != null) {
                bitmap.recycle();
            }
            bitmap       = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            setBackgroundColor(getResources().getInteger(R.integer.white_bg));

            createRandomRect(bitmapCanvas);
            canvas.drawBitmap(bitmap, 0, 0, null);

            pause(1000 / FPS, this::invalidate);
        }

        private void createRandomRect(Canvas canvas) {
            // Draw randomly sized rectangles
            int sizeX = BASE_SIZE + rgen.nextInt(BASE_SIZE) * rgen.nextInt(BASE_SIZE);
            int sizeY = BASE_SIZE + rgen.nextInt(BASE_SIZE) * rgen.nextInt(BASE_SIZE);
            int x     = -BASE_SIZE + rgen.nextInt(getWidth());
            int y     = -BASE_SIZE + rgen.nextInt(getHeight());

            paintFill.setColor(rgen.nextInt());
            canvas.drawRect(x, y, x + sizeX, y + sizeY, paintFill);
            canvas.drawRect(x, y, x + sizeX, y + sizeY, paintStroke);
        }

        private void pause(int millis, Runnable function) {
            Handler handler = new Handler();
            handler.postDelayed(function, millis);
        }
    }
}

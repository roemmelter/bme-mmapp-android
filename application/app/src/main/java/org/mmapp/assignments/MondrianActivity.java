package org.mmapp.assignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.util.ConfigActionBar;

import java.util.Random;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>MondrianActivity</b><br>
 * Create a mondrian art generator.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class MondrianActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MondrianView(MondrianActivity.this));
        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    class MondrianView extends View {
        private final int BASE_SIZE = 80;
        private final int STROKE_SIZE = 0;

        private Random rgen = new Random();
        private Paint  paintStroke;
        private Paint  paintFill;
        private Bitmap bitmap;
        private Canvas bitmapCanvas;

        public MondrianView(Context context) {
            super(context);
            paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintStroke.setColor(Color.BLACK);
            paintStroke.setStyle(Paint.Style.STROKE);
            paintStroke.setStrokeWidth(STROKE_SIZE);

            paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintFill.setStyle(Paint.Style.FILL);

            this.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        resetBackground(bitmap.getWidth(), bitmap.getHeight());
                        invalidate();
                    }
                    return true;
                }
            });
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            resetBackground(w,h);
        }

        private void resetBackground(int width, int height) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            setBackgroundColor(Color.WHITE);
            drawMondrian(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        private void drawMondrian(int i, int j, int width, int height) {
            // base case
            if ((width < BASE_SIZE) || (height < BASE_SIZE)) {
                return;
            }
            // recursive case
            int choice = rgen.nextInt(3);
            switch (choice) {
                case 0: // divide canvas horizontally
                    drawMondrian(i, j, width / 2, height);
                    drawMondrian(i + width / 2, j, width / 2, height);
                    break;
                case 1: // divide canvas vertically
                    drawMondrian(i, j, width, height / 2);
                    drawMondrian(i, j + height / 2, width, height / 2);
                    break;
                default: // do nothing
                    drawRectangle(i, j, width, height);
                    break;
            }
        }

        private void drawRectangle(int left, int top, int width, int height) {
            int color = getRandomColor();

            paintFill.setColor(color);
            bitmapCanvas.drawRect(left + STROKE_SIZE, top + STROKE_SIZE,
                                  left + width - STROKE_SIZE *2,
                                  top + height - STROKE_SIZE *2,
                                  paintFill);
            bitmapCanvas.drawRect(left, top, width, height, paintStroke);
        }
        private int getRandomColor() {
            int choice = rgen.nextInt(4);
            switch (choice) {
                case 0:
                    return Color.BLUE;
                case 1:
                    return Color.RED;
                case 2:
                    return Color.YELLOW;
                default:
                    return Color.WHITE;
            }
        }

    }
}

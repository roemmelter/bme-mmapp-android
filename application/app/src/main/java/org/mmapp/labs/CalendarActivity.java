package org.mmapp.labs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MenuItem;

import org.mmapp.util.ConfigActionBar;

import java.util.Calendar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 *
 * CalendarActivity
 *
 * @author Erik Roemmelt
 */
public class CalendarActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar beginTime = Calendar.getInstance();
        // year, month, day, hour, minute
        beginTime.set(2021, 2, 16, 8, 00);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2021,2,16,9,30);
        long endMillis = endTime.getTimeInMillis();

        Intent intent = new Intent(Intent.ACTION_EDIT);

        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, "MMApp/A: Lano Vorlesung");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Online via Discord mit Skript 'Variationen zum Thema: Android'");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis);
        // FREQ: Frequency, INTERVAL: how often by frequency, BYDAY=what days in week, UNTIL= repeat until what date 'yearmonthday'+'T'+'hoursminutesseconds'+'Z'
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=1;BYDAY=TU;UNTIL=20210702T000000");
        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        intent.putExtra(CalendarContract.Events.ALLOWED_AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }
}

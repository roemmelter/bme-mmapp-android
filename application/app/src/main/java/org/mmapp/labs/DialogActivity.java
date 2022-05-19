package org.mmapp.labs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.util.ConfigActionBar;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>DialogActivity</b><br>
 * Activity creates a dialog with a string array as
 * option list. Shows selected option in a toast.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class DialogActivity extends AppCompatActivity {
    private ConfigActionBar configActionBar;
    private String[] who_starts = {
            "Computer", "Human" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openNewGameDialog();
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    private void openNewGameDialog() {
        new AlertDialog.Builder(this).setTitle("Who should start?")
                .setItems(who_starts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(((Dialog) dialog).getContext(),
                                       "Let's start the game, " + who_starts[which] + " will " +
                            "start.",
                                       Toast.LENGTH_LONG).show();
                    }
                }).show();
    }
}

package org.mmapp.assignments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;
import org.mmapp.util.PermissionUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>EditorActivity</b><br>
 * Open / create a file in text view, edit and save it as a new file or override the existing file.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class EditorActivity extends AppCompatActivity {
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_DOCUMENTS,
    };
    private PermissionUtility permissionUtility;

    private final int FILE_OPEN_REQUEST_CODE = 1;
    private ConfigActionBar configActionBar;
    private EditText et;
    private File workFile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = createLayout(this);
        et = createEditText(this);
        ll.addView(et);
        setContentView(ll);
        configActionBar = new ConfigActionBar(AssignmentsOverviewActivity.class, this, true);

        permissionUtility = new PermissionUtility(this, PERMISSIONS);
        if (permissionUtility.arePermissionsEnabled()) {
            Log.d("M_", "Permissions granted onCreate()");
        } else {
            permissionUtility.requestMultiplePermissions();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem itemOpen = menu.add("Open");
        itemOpen.setIcon(R.drawable.ic_baseline_folder_open_24);
        itemOpen.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItem itemSave = menu.add("Save");
        itemSave.setIcon(R.drawable.ic_baseline_save_24);
        itemSave.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        super.onOptionsItemSelected(item);
        if (item.getTitle() != null) {
            try {
                switch (item.getTitle().toString().toLowerCase()) {
                    case "open":
                        showFileChooser();
                        break;
                    case "save":
                        openSaveDialog();
                        break;
                }
                return true;
            } catch (NullPointerException ex) {
                et.setText(ex.toString());
            }
        }
        return false;
    }

    private LinearLayout createLayout(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(Color.WHITE);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        return ll;
    }
    private EditText createEditText(Context context) {
        EditText inputField = new EditText(context);
        inputField.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                              ViewGroup.LayoutParams.MATCH_PARENT));
        inputField.setGravity(Gravity.TOP);
        inputField.setSingleLine(false);
        inputField.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        inputField.setHorizontallyScrolling(false);
        inputField.setVerticalScrollBarEnabled(true);
        inputField.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        inputField.setHint("Enter here your text...");
        inputField.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
        return inputField;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        String path   = Environment.getExternalStorageDirectory() + "/";
        Uri    uri    = Uri.parse(path);
        intent.setDataAndType(uri, "text/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to open"),
                                       FILE_OPEN_REQUEST_CODE);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                           Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                assert data != null;
                Uri uri = data.getData();
                if (requestCode == FILE_OPEN_REQUEST_CODE) {
                    workFile = getFileFromUri(uri);
                    String fileContent = readFromFile(workFile);
                    et.setText(fileContent);
                }
            } catch (NullPointerException | IOException ex) {
                et.setText(ex.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String readFromFile(File sourceFile) throws IOException {
        BufferedReader  bufferedReader  =
                new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
        String content = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            content += line + "\n";
        }
        bufferedReader.close();
        return content;
    }
    private File getFileFromUri(Uri uri) {
        String uriPath = uri.getPath();
        String dirPath = uriPath.substring(uriPath.indexOf(":")+1);
        Log.d("M_", "dirPath: " + dirPath);
        return new File(dirPath);
    }

    private void openSaveDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Save");

        final EditText inputFilePath = new EditText(this);
        inputFilePath.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        inputFilePath.setTextColor(Color.WHITE);

        final float PADDING = 5.0f;
        float density = getResources().getDisplayMetrics().density;
        int paddingInDp = (int) Math.round(PADDING * density);
        inputFilePath.setPadding(4 * paddingInDp, paddingInDp,
                                4 * paddingInDp, paddingInDp);

        if (workFile == null) {
            // new file
            inputFilePath.setText(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            // opened file
            inputFilePath.setText(workFile.getAbsolutePath());
        }
        dialog.setView(inputFilePath);

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = inputFilePath.getText().toString();
                try {
                    saveToFile(new File(fileName));
                } catch (IOException ex) {
                    et.setText(ex.toString());
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
    private void saveToFile(File destinationFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        String content = et.getText().toString();
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionUtility.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            Log.d("M_", "Permission granted onRequestPermissionsResult()");
        }
    }
}

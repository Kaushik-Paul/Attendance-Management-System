package com.example.milestone2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView informationTextView;

    ListView listView;

    public void saveOption(View view) {

        listView = (ListView) findViewById(R.id.listView);

        String save = informationTextView.getText().toString() + "\n\r";

        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                save += "1";
            } else {
                save += "0";
            }
        }
        save += "\n\r";

        Log.i("Saved Item: ", save);

        // Store the information in txt file
        String fileName = LocalDateTime.now().toString() + ".txt";

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(save.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create file
        // File for storing in internal storage
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

//        File file = commonDocumentDirPath(fileName);
//
//        // Write to the file
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(save.getBytes());
//            fos.flush();
//            fos.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Toast.makeText(this, "Attendance saved in " + fileName, Toast.LENGTH_LONG).show();

        // Uncheck all the items after saving
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Ask for permission to write in the external storage

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);

        }


        // Values to Use
        String stream = getIntent().getExtras().getString("stream");
        String teacherName = getIntent().getExtras().getString("teacherName");
        String subjectCode = getIntent().getExtras().getString("subjectCode");
        int noOfStudents = getIntent().getExtras().getInt("noOfStudents");

        informationTextView = (TextView) findViewById(R.id.informationTextView);

        String information = "Teacher Name: " + teacherName + "\n\r" + "Stream: " + stream + "\n\r" + "Subject Code: " + subjectCode + "\n\r"
                + "No of Students: " + noOfStudents;
        informationTextView.setText(information);

        List<String> listOfStudents = new ArrayList<>();
        for (int i = 1; i <= noOfStudents; i++) {
            listOfStudents.add(Integer.toString(i));
        }

        listView = (ListView) findViewById(R.id.listView);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listOfStudents);
        listView.setAdapter(arrayAdapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public File commonDocumentDirPath(String FolderName) {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + FolderName);
        } else {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + FolderName);
        }

        // Make sure the path directory exists.
        if (!dir.exists()) {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success) {
                dir = null;
            }
        }
        return dir;
    }

}
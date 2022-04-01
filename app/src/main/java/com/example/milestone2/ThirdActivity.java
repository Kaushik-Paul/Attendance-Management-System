package com.example.milestone2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    TextView informationTextView;
    ListView listView;

    // Values to Use
    static String stream;
    static String teacherName;
    static String subjectCode;
    static int noOfStudents;
    // Get formatted date and time
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormattedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter myFormattedTimeWithSeconds = DateTimeFormatter.ofPattern("HH.mm.ss");
    DateTimeFormatter myFormattedTimeWithoutSeconds = DateTimeFormatter.ofPattern("HH:mm");
    String formattedDate = myDateObj.format(myFormattedDate);
    String formattedTimeWithSeconds = myDateObj.format(myFormattedTimeWithSeconds);
    String formattedTimeWithoutSeconds = myDateObj.format(myFormattedTimeWithoutSeconds);

    public void saveOption(View view) {

        listView = (ListView) findViewById(R.id.listView);

        StringBuilder save = new StringBuilder();

        // Values to be written in txt file
        save.append(teacherName).append(",");
        save.append(stream).append(",");
        save.append(subjectCode).append(",");
        save.append(noOfStudents).append(",");

        // get the attendance
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                save.append("1");
            } else {
                save.append("0");
            }
        }
        save.append(",").append(formattedDate).append(",").append(formattedTimeWithSeconds);

        // Log the info for debugging
        Log.i("Saved Item: ", save.toString());

        // Name of the saved file
        String fileName = teacherName + " " + formattedDate + formattedTimeWithSeconds + ".txt";

        // Check for permission to excess external storage
        if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
        ) {
            File file = null;
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Attendance Management");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                File gpxfile = new File(file, fileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(save.toString());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Pop a toast message
        Toast.makeText(
                        this,
                        "Attendance saved in Documents/Attendance Management/" + fileName,
                        Toast.LENGTH_LONG)
                .show();

        // Uncheck all the items after saving
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, false);
        }

        // Alert Dialog for next path
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Attendance Saved!!!")
                .setMessage("Continue??")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Info:", "Yes Button is pressed");

                        Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Ask for permission to write in the external storage
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        informationTextView = (TextView) findViewById(R.id.informationTextView);

        // Values to Use
        stream = getIntent().getExtras().getString("stream");
        teacherName = getIntent().getExtras().getString("teacherName");
        subjectCode = getIntent().getExtras().getString("subjectCode");
        noOfStudents = getIntent().getExtras().getInt("noOfStudents");


        String information = "Teacher Name: " + teacherName + "\n\r" + "Stream: " + stream + "\n\r" + "Subject Code: " +
                subjectCode + "\n\r" + "No of Students: " + noOfStudents + "\n\r" + "Date: " + formattedDate + "\n\r"
                + "Time: " + formattedTimeWithoutSeconds;
        informationTextView.setText(information);

        List<String> listOfStudents = new ArrayList<>();
        for (int i = 1; i <= noOfStudents; i++) {
            listOfStudents.add("Roll No: " + Integer.toString(i));
        }

        listView = (ListView) findViewById(R.id.listView);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                listOfStudents
        );
        listView.setAdapter(arrayAdapter);

    }

}
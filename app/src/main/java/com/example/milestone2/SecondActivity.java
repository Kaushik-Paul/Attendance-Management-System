package com.example.milestone2;

import android.Manifest;
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

public class SecondActivity extends AppCompatActivity {

    TextView informationTextView;

    ListView listView;

    public void saveOption(View view) {

        listView = (ListView) findViewById(R.id.listView);

        StringBuilder save = new StringBuilder(informationTextView.getText().toString() + "\n\r");
        String teacherName = getIntent().getExtras().getString("teacherName");

        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                save.append("1");
            } else {
                save.append("0");
            }
        }
        save.append("\n\r");

        Log.i("Saved Item: ", save.toString());

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");

        // Name of the saved file
        String fileName = teacherName + " " + myDateObj.format(myFormatObj) + ".txt";

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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

        Toast.makeText(this, "Attendance saved in Documents/Attendance Management/" + fileName, Toast.LENGTH_LONG).show();

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

}
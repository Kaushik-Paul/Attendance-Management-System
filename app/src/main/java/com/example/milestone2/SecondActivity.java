package com.example.milestone2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    static String stream;
    static String semester;
    EditText teacherName;
    EditText subjectCode;
    EditText noOfStudents;

    public void formSubmit(View view) {

        teacherName = (EditText) findViewById(R.id.teacherNameEditText);
        subjectCode = (EditText) findViewById(R.id.subjectCodeEditText);
        noOfStudents = (EditText) findViewById(R.id.noOfStudentsEditText);

        try {
            Integer numberOfStudentsInInt = Integer.parseInt(noOfStudents.getText().toString());
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirm")
                    .setMessage("Are You Sure??")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i("Info:", "Yes Button is pressed");

                            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                            intent.putExtra("stream", stream);
                            intent.putExtra("teacherName", teacherName.getText().toString());
                            intent.putExtra("subjectCode", subjectCode.getText().toString().trim());
                            intent.putExtra("noOfStudents", Integer.parseInt(noOfStudents.getText().toString()));
                            intent.putExtra("semester", semester);

                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } catch (NumberFormatException e) {
            noOfStudents.setError("Enter a Valid Number");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Spinner spinnerStream = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterStream = ArrayAdapter.createFromResource(this, R.array.stream, android.R.layout.simple_spinner_item);
        adapterStream.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStream.setAdapter(adapterStream);

        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stream = adapterView.getItemAtPosition(i).toString();
                Log.i("Stream Selected: ", stream);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerSemester = findViewById(R.id.spinnerSem);
        ArrayAdapter<CharSequence> adapterSem = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapterSem);

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semester = adapterView.getItemAtPosition(i).toString();
                Log.i("Semester Selected: ", semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
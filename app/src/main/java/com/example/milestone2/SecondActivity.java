package com.example.milestone2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    static String stream;

    EditText teacherName;
    EditText subjectCode;
    EditText noOfStudents;

    public void formSubmit(View view) {

        teacherName = (EditText) findViewById(R.id.teacherNameEditText);
        subjectCode = (EditText) findViewById(R.id.subjectCodeEditText);
        noOfStudents = (EditText) findViewById(R.id.noOfStudentsEditText);

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

                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stream, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stream = adapterView.getItemAtPosition(i).toString();
                Log.i("Item Selected: ", stream);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
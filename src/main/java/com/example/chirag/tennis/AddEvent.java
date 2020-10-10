package com.example.chirag.tennis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class AddEvent extends AppCompatActivity {

    EditText etStartTime, etEndTime;
    Button bSubmitE;
    Spinner eventSpinner;
    String fileName = "file35.txt";
    String date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        date2 = getIntent().getStringExtra(EXTRA_MESSAGE);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        eventSpinner = findViewById(R.id.eventSpinner);
        bSubmitE = findViewById(R.id.bSubmitE);

        String[] arraySpinner2 = new String[] {"Meet", "Practice"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(adapter2);

        if (readFile(fileName).contains(date2)){
            Toast.makeText(AddEvent.this, "Event already added", Toast.LENGTH_LONG).show();
        }else {
            submitEvent();
        }

        String firstName = "Chi";
        String lastName = "Kikk";
        String ID = "3";
        String bigString = firstName+"-"+lastName+"-"+ID + "-_";

    }

    public void submitEvent(){
        bSubmitE.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String eventee = date2 + "_" + eventSpinner.getSelectedItem().toString() + "_"+etStartTime.getText().toString() +"_"+
                                etEndTime.getText().toString() +"_-";
                        saveFile (fileName, readFile(fileName) + eventee);
                        Intent intent = new Intent(AddEvent.this, Calendar.class);
                        startActivity(intent);
                    }
                }
        );

    }

    public void saveFile (String file,String text){
        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    public String readFile (String file){
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte [] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String (buffer);


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error reading", Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    public void btEvents(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }
}

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
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class UpdateEvent extends AppCompatActivity {

    EditText etStartTime1, etEndTime1;
    Button bSubmitE1, bRemovee;
    Spinner eventSpinner1;
    ArrayList<Evento> alEvents1 = new ArrayList<>();
    String filename = "file35.txt";
    String date3;
    Evento tevent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        date3 = getIntent().getStringExtra(EXTRA_MESSAGE);

        etStartTime1 = findViewById(R.id.etStartTime1);
        etEndTime1 = findViewById(R.id.etEndTime1);
        eventSpinner1 = findViewById(R.id.eventSpinner1);
        bSubmitE1 = findViewById(R.id.bSubmitE1);
        //bRemovee = findViewById(R.id.bRemovee);

        String[] arraySpinner2 = new String[] {"Meet", "Practice"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner1.setAdapter(adapter2);

        makeEventO();
        submitEvent();
       // deleteEvent();
    }

    public void submitEvent(){
        bSubmitE1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String saveString = "";
                        Evento e2 = new Evento (date3, eventSpinner1.getSelectedItem().toString(),
                        etStartTime1.getText().toString(), etEndTime1.getText().toString());
                        for (int j = 0; j < alEvents1.size(); j++){
                            if (date3.equals(alEvents1.get(j).getDate1())){
                                alEvents1.set(j, e2);
                            }
                        }
                        for (int k = 0; k < alEvents1.size(); k++){
                            saveString += alEvents1.get(k).getDate1() +"_";
                            saveString += alEvents1.get(k).getType() +"_";
                            saveString += alEvents1.get(k).getBtime() +"_";
                            saveString += alEvents1.get(k).getEtime() +"_-";
                        }
                        saveFile(filename, saveString);
                        Toast.makeText(UpdateEvent.this, "Updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateEvent.this, Calendar.class);
                        startActivity(intent);
                    }
                }
        );

    }

    public void deleteEvent(){
        bRemovee.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String saveString = "";
                        for (int j = 0; j < alEvents1.size(); j++){
                            if (date3.equals(alEvents1.get(j).getDate1())){
                                alEvents1.remove(j);
                            }
                        }
                        for (int k = 0; k < alEvents1.size(); k++){
                            saveString += alEvents1.get(k).getDate1() +"_";
                            saveString += alEvents1.get(k).getType() +"_";
                            saveString += alEvents1.get(k).getBtime() +"_";
                            saveString += alEvents1.get(k).getEtime() +"_-";
                        }
                        saveFile(filename, saveString);
                        Toast.makeText(UpdateEvent.this, "Updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateEvent.this, Calendar.class);
                        startActivity(intent);
                    }

                }
        );

    }

    public void makeEventO(){
        String edata = readFile(filename);
        String [] eventu = edata.split("-");
        for (int i = 0; i < eventu.length; i++){
            String [] oevent = eventu [i].split("_");
            Evento e1 = new Evento(oevent[0], oevent[1], oevent[2], oevent[3]);
            alEvents1.add(e1);
        }
        for (int j = 0; j < alEvents1.size(); j++){
            if (date3.equals(alEvents1.get(j).getDate1())){
                tevent1 = new Evento(alEvents1.get(j).getDate1(),alEvents1.get(j).getType(), alEvents1.get(j).getBtime()
                        , alEvents1.get(j).getEtime());
            }
        }

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

    public void btEvents1(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }
}

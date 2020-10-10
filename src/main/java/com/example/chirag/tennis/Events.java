package com.example.chirag.tennis;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Events extends AppCompatActivity {

    databaseHelper myDB;
    ArrayList<Player> alPlayer4 = new ArrayList<>();
    ArrayList <Evento> alEvents = new ArrayList<>();
    Button bSend, bRemove1;
    String filename = "file35.txt";
    String date1;
    Evento tevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        date1 = getIntent().getStringExtra(EXTRA_MESSAGE);
        TextView tveDate = findViewById(R.id.eventsDate);
        TextView tveType = findViewById(R.id.tveType);
        TextView tveTime = findViewById(R.id.tveTime);

        bSend = findViewById(R.id.bSend);
        bRemove1 = findViewById(R.id.bDeleteEvent);
        myDB = new databaseHelper( this);
        tevent = new Evento ("","","","");

        VData();
        sendMail();
        makeEventO();
        deleteEvent();

        tveDate.setText(date1);
        tveType.setText(tevent.getType());
        tveTime.setText(tevent.getBtime() + "-" + tevent.getEtime());
        String noEvents = tevent.getBtime() + "-" + tevent.getEtime();
        if (noEvents.equals("-")){
            tveTime.setText("No events scheduled");
        }
    }

    public void makeEventO(){
        String edata = readFile(filename);
        String [] eventu = edata.split("-");
        for (int i = 0; i < eventu.length; i++){
            String [] oevent = eventu [i].split("_");
            Evento e1 = new Evento(oevent[0], oevent[1], oevent[2], oevent[3]);
            alEvents.add(e1);
        }
        for (int j = 0; j < alEvents.size(); j++){
            if (date1.equals(alEvents.get(j).getDate1())){
                tevent = new Evento(alEvents.get(j).getDate1(),alEvents.get(j).getType(), alEvents.get(j).getBtime()
                , alEvents.get(j).getEtime());
            }
        }

    }

    public void deleteEvent(){
        bRemove1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String saveString = "";
                        for (int j = 0; j < alEvents.size(); j++){
                            if (date1.equals(alEvents.get(j).getDate1())){
                                alEvents.remove(j);
                            }
                        }
                        for (int k = 0; k < alEvents.size(); k++){
                            saveString += alEvents.get(k).getDate1() +"_";
                            saveString += alEvents.get(k).getType() +"_";
                            saveString += alEvents.get(k).getBtime() +"_";
                            saveString += alEvents.get(k).getEtime() +"_-";
                        }
                        saveFile(filename, saveString);
                        Toast.makeText(Events.this, "Updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Events.this, Calendar.class);
                        startActivity(intent);
                    }

                }
        );

    }

    public void sendMail(){
        bSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String [] recepients = new String[alPlayer4.size()];
                        for (int i = 0; i< alPlayer4.size(); i++){
                            recepients[i] = (alPlayer4.get(i).getEmail());
                        }
                        String subject1 = "Tennis Reminder: " + date1;
                        String message1 = "There is a " + tevent.getType() + " from " + tevent.getBtime() + "-" + tevent.getEtime() + ".";
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL, recepients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject1);
                        intent.putExtra(Intent.EXTRA_TEXT, message1);

                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Choose an application"));
                    }
                }
        );

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

    public void btCal(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void gtAddEvent(View view) {
        if (readFile(filename).contains(date1)){
            Toast.makeText(Events.this, "Event already added", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(Events.this, AddEvent.class);
            intent.putExtra(EXTRA_MESSAGE, date1);
            startActivity(intent);
        }

    }

    public void gtUpdateEvent(View view) {
        if (!readFile(filename).contains(date1)){
            Toast.makeText(Events.this, "No event to update", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, UpdateEvent.class);
            intent.putExtra(EXTRA_MESSAGE, date1);
            startActivity(intent);
        }
    }

    public void VData (){

        Cursor curse = myDB.getAllData();
        if (curse.getCount() == 0){
            return;
        }
        while (curse.moveToNext()){
            Player p1 = new Player(Integer.parseInt(curse.getString(0)),
                    curse.getString(1), curse.getString(2), curse.getString(3),
                    curse.getString (4), Integer.parseInt(curse.getString(5)));
            alPlayer4.add(p1);
        }
        Collections.sort(alPlayer4, new SortByRank());

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



}



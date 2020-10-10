package com.example.chirag.tennis;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Players extends AppCompatActivity {

    databaseHelper myDB;
    ListView lvPlayers;
    ArrayList names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        lvPlayers = (ListView) findViewById(R.id.lvLadder);
        myDB = new databaseHelper( this);


    }

    public void ViewAll (){

        Cursor curse = myDB.getAllData();
        StringBuffer sb1 = new StringBuffer();
        while (curse.moveToNext()){
            sb1.append(curse.getString(0) + "\n");
            names.add(curse.getString(0));  

            }
    }
}




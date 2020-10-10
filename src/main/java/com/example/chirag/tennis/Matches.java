package com.example.chirag.tennis;

import android.content.Intent;
import android.database.Cursor;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Matches extends AppCompatActivity {

    ArrayList<Player> alPlayer = new ArrayList <Player>();
    Button bsMatch;
    Spinner wpSpinner, lpSpinner;
    databaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        myDB = new databaseHelper( this);
        bsMatch = findViewById(R.id.bsMatch);
        wpSpinner = findViewById(R.id.wpSpinner);
        lpSpinner = findViewById(R.id.lpSpinner);
        VData();
        doMatches();


        String [] aNames = new String [alPlayer.size()];
        for (int i = 0; i < alPlayer.size(); i++){
            aNames[i] = alPlayer.get(i).getRank() + ".  " + alPlayer.get(i).getFirstN();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wpSpinner.setAdapter(adapter);
        lpSpinner.setAdapter(adapter);

    }

    public void VData (){

        Cursor curse = myDB.getAllData();
        if (curse.getCount() == 0){
            return;
        }
        while (curse.moveToNext()){
            Player p1 = new Player(Integer.parseInt(curse.getString(0)),
                    curse.getString(1), curse.getString(2), curse.getString(3),
                    curse.getString(4), Integer.parseInt(curse.getString(5)));
            alPlayer.add(p1);
        }
        Collections.sort(alPlayer, new SortByRank());
    }

    public void doMatches (){
        bsMatch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int wrank = 0;
                        int lrank = 0;
                        String wpS = wpSpinner.getSelectedItem().toString();
                        String tempw = wpS.substring(wpS.indexOf('.') + 1, wpS.length());
                        String firstw = tempw.substring(2);
                        String lpS = lpSpinner.getSelectedItem().toString();
                        String templ = lpS.substring(lpS.indexOf('.') + 1, lpS.length());
                        String firstl = templ.substring(2);

                        for (int i = 0; i<alPlayer.size(); i++){
                            if (firstw.equals(alPlayer.get(i).getFirstN())){
                                wrank = alPlayer.get(i).getRank();
                            }
                            if (firstl.equals(alPlayer.get(i).getFirstN())){
                                lrank = alPlayer.get(i).getRank();
                            }
                        }
                        if (wrank < lrank){
                            Intent update = new Intent(Matches.this, ViewKids.class);
                            Toast.makeText(Matches.this, "Recorded", Toast.LENGTH_LONG).show();
                            startActivity(update);
                        }else if (wrank > lrank){
                            Player temp2 = alPlayer.remove(wrank - 1);
                            alPlayer.add(lrank - 1, temp2);
                            for (int i = 0; i < alPlayer.size(); i++){
                                myDB.updateData(i+1 +"", alPlayer.get(i).getFirstN(), alPlayer.get(i).getLastN(),
                                        alPlayer.get(i).getYear(),alPlayer.get(i).getEmail(), alPlayer.get(i).getId() + "");
                            }
                            Intent update = new Intent(Matches.this, ViewKids.class);
                            Toast.makeText(Matches.this, "Recorded", Toast.LENGTH_LONG).show();
                            startActivity(update);
                        }else{
                            Toast.makeText(Matches.this, "Must be different players", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void btVK(View view) {
        Intent intent = new Intent(this, ViewKids.class);
        startActivity(intent);
    }
}

 class SortByRank implements Comparator <Player>{
        public int compare(Player a, Player b){
            return a.getRank() - b.getRank();
        }
}

class SortByName implements Comparator <Player>{
    public int compare(Player a, Player b){
        return a.getFirstN().toLowerCase().compareTo(b.getFirstN().toLowerCase());
    }
}



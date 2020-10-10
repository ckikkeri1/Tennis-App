package com.example.chirag.tennis;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ViewKids extends AppCompatActivity {

    ListView lvLadder;
    ArrayList<String> listItems = new ArrayList <String>();
    ArrayList<Player> alPlayer1 = new ArrayList <Player>();
    public static final String DATABASE_NAME = "Name.db";
    Spinner sortSpinner;
    ArrayAdapter<String> adapt;
    databaseHelper myDB;
    Button bMatch, bSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_kids);

        lvLadder = (ListView) findViewById(R.id.lvLadder);
        myDB = new databaseHelper( this);

        adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
        bMatch = (Button) findViewById(R.id.bMatch);
        bSort = (Button) findViewById(R.id.bSort);
        sortSpinner = findViewById(R.id.sortSpinner);
        VData();
        populateLV();
        doSort();

    }

    public void populateLV (){
        listItems.clear();
        for (int i = 0; i< alPlayer1.size(); i++){
            listItems.add(alPlayer1.get(i).getRank() + ".  " + alPlayer1.get(i).getFirstN());
        }
        lvLadder.setAdapter(adapt);

        String[] arraySpinner1 = new String[] {
                "Rank", "First Name"
        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter2);


        lvLadder.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nameS = (String) adapterView.getItemAtPosition(i);
                Intent playerStats = new Intent(ViewKids.this, InfoPlayer.class);
                playerStats.putExtra("name", nameS);
                startActivity(playerStats);
            }
        });
        }
    public void gtMatches(View view) {
        Intent intent = new Intent(this, Matches.class);
        startActivity(intent);
    }

    public void gtLadder(View view) {
        Intent intent = new Intent(this, Ladder.class);
        intent.putExtra("nump", listItems.size() + 1 + "");
        startActivity(intent);
    }

    public void btHP(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
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
            alPlayer1.add(p1);
        }
        Collections.sort(alPlayer1, new SortByRank());
    }

    public void doSort(){
        bSort.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (sortSpinner.getSelectedItem().toString().equals("Rank")){
                            Collections.sort(alPlayer1, new SortByRank());
                            populateLV();
                        }if (sortSpinner.getSelectedItem().toString().equals("First Name")){
                            Collections.sort(alPlayer1, new SortByName());
                            populateLV();
                        }
                    }
                }
        );
    }
}


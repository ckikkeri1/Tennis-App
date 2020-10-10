package com.example.chirag.tennis;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoPlayer extends AppCompatActivity {

    Button bUpdate, bDelete;
    String message;
    databaseHelper myDB;
    String rank2, firstn3;
    int pID3;
    ArrayList<Player> alPlayer3 = new ArrayList<>();
    ArrayList<Player> alp = new ArrayList<>();
    Player p1;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_player);

        Intent intent = getIntent();
        message = intent.getStringExtra("name");
        rank2 = message.substring(0,message.indexOf('.'));
        String temp234 = message.substring(message.indexOf('.') + 1, message.length());
        firstn3 = temp234.substring(2);

        bUpdate = (Button) findViewById(R.id.bUpdate);
        bDelete = (Button) findViewById(R.id.bDelete);
        myDB = new databaseHelper(this);

        VData();
        UpdateData();
        DeleteData();

        TextView fn = (TextView) findViewById(R.id.test1);
        fn.setText(message);
        String test9 = p1.getFirstN();
        TextView fn1 = (TextView) findViewById(R.id.tvFirstN);
        fn1.setText("First Name: " + p1.getFirstN());
        TextView ln = (TextView) findViewById(R.id.tvLastN);
        ln.setText("Last Name: " +p1.getLastN());
        TextView yn = (TextView) findViewById(R.id.tvYear);
        yn.setText("Year: " +p1.getYear());
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail123);
        tvEmail.setText("Email: " +p1.getEmail());
    }

    public void UpdateData (){
        bUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(InfoPlayer.this, UpdateData.class);
                        update.putExtra("message1", message);
                        startActivity(update);
                    }
                }
        );
    }

    public void DeleteData (){
        bDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dRank = 0;
                        for (int i = 0; i < alPlayer3.size(); i++){
                            if (alPlayer3.get(i).getId() == pID3){
                                dRank = alPlayer3.get(i).getRank();
                                alPlayer3.remove(i);
                            }
                        }
                        Integer deletedRows = myDB.deleteData(pID3 +"");
                        for (int i = 0; i < alPlayer3.size(); i++){
                            if (dRank > alPlayer3.get(i).getRank()) {
                                myDB.updateData(alPlayer3.get(i).getRank() + "", alPlayer3.get(i).getFirstN(),
                                        alPlayer3.get(i).getLastN(), alPlayer3.get(i).getYear(), alPlayer3.get(i).getEmail(),
                                        alPlayer3.get(i).getId() + "");
                            }else {
                                myDB.updateData(alPlayer3.get(i).getRank()-1 + "", alPlayer3.get(i).getFirstN(),
                                        alPlayer3.get(i).getLastN(), alPlayer3.get(i).getYear(), alPlayer3.get(i).getEmail(),
                                        alPlayer3.get(i).getId() + "");
                            }
                        }
                        Intent update2 = new Intent(InfoPlayer.this, ViewKids.class);
                        startActivity(update2);
                    }
                }
        );
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
            alPlayer3.add(p1);
        }
        pID3 = 0;

        for (int i = 0; i<alPlayer3.size(); i++){
            if (firstn3.equals(alPlayer3.get(i).getFirstN())){
                pID3 = alPlayer3.get(i).getId();
                p1 = new Player(alPlayer3.get(i).getRank(), alPlayer3.get(i).getFirstN(), alPlayer3.get(i).getLastN(),
                alPlayer3.get(i).getYear(),alPlayer3.get(i).getEmail(), alPlayer3.get(i).getId());
            }

        }

    }
    public void btVK(View view) {
        Intent intent = new Intent(this, ViewKids.class);
        startActivity(intent);
    }
}



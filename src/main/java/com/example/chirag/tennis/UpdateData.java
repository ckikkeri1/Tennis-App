package com.example.chirag.tennis;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class UpdateData extends AppCompatActivity {

    EditText etFirstN2, etLastN2, etEmail2;
    Spinner yearSpinner2;
    Button bUpdate2;
    String rank, firstn;
    int pID;
    databaseHelper myDB;
    ArrayList<Player> alPlayer2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message1");
        rank = message.substring(0,message.indexOf('.'));
        String temp234 = message.substring(message.indexOf('.') + 1, message.length());
        firstn = temp234.substring(2);


        myDB = new databaseHelper( this);
        bUpdate2 = findViewById(R.id.bUpdate2);
        etFirstN2 = findViewById(R.id.etFirstN2);
        etLastN2 = findViewById(R.id.etLastN2);
        etEmail2 = findViewById(R.id.etEmail2);

        String[] arraySpinner2 = new String[] {
                "Freshman", "Sophomore", "Junior", "Senior"
        };
        yearSpinner2 = (Spinner) findViewById(R.id.yearSpinner3);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner2.setAdapter(adapter2);

        VData();
        UpdateData();
    }

    public void UpdateData (){
        bUpdate2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etFirstN2.getText().toString().trim().equals("")){
                            Toast.makeText(UpdateData.this, "Enter First Name", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (etLastN2.getText().toString().trim().equals("")){
                            Toast.makeText(UpdateData.this, "Enter Last Name", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (etEmail2.getText().toString().trim().equals("")){
                            Toast.makeText(UpdateData.this, "Enter Email", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String mop = etEmail2.getText().toString();
                        if (isValid(mop) == false){
                            Toast.makeText(UpdateData.this, "Enter Valid Email", Toast.LENGTH_LONG).show();
                            return;
                        }

                         boolean isUpdated = myDB.updateData(rank, etFirstN2.getText().toString(),
                                 etLastN2.getText().toString(), yearSpinner2.getSelectedItem().toString(),etEmail2.getText().toString(), pID+"");
                         if (isUpdated){
                             Toast.makeText(UpdateData.this, "Updated", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(UpdateData.this, ViewKids.class);
                             startActivity(intent);
                         }
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
                    curse.getString(4),Integer.parseInt(curse.getString(5)));
            alPlayer2.add(p1);
        }
        pID = 0;

        for (int i = 0; i<alPlayer2.size(); i++){
            if (firstn.equals(alPlayer2.get(i).getFirstN())){
                pID = alPlayer2.get(i).getId();
            }

        }

    }
    public void btIP(View view) {
        Intent intent = new Intent(this, ViewKids.class);
        startActivity(intent);
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}

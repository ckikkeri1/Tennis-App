package com.example.chirag.tennis;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Ladder extends AppCompatActivity {

    databaseHelper myDB;
    EditText etFirstN, etLastN, etEmaila;
    Button bAddData, bViewData;
    Spinner yearSpinner;
    String nextRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);

        Intent intent = getIntent();
        nextRank = intent.getStringExtra("nump");

        String[] arraySpinner = new String[] {
                "Freshman", "Sophomore", "Junior", "Senior"
        };
        yearSpinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

        myDB = new databaseHelper( this);

        etFirstN = (EditText) findViewById(R.id.etFirstN);
        etLastN = (EditText) findViewById(R.id.etLastN);
        etEmaila = (EditText) findViewById(R.id.etEmaila);
        bAddData = (Button) findViewById(R.id.bAddData);
     //   bViewData = (Button) findViewById(R.id.bViewData);


        AddData();
      //  ViewAll();
    }

    public void AddData (){
        bAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etFirstN.getText().toString().trim().equals("")){
                            Toast.makeText(Ladder.this, "Enter First Name", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (etLastN.getText().toString().trim().equals("")){
                            Toast.makeText(Ladder.this, "Enter Last Name", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (etEmaila.getText().toString().trim().equals("")){
                            Toast.makeText(Ladder.this, "Enter Email", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String mop = etEmaila.getText().toString();
                        if (isValid(mop) == false){
                            Toast.makeText(Ladder.this, "Enter Valid Email", Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean isInserted = myDB.insertData(nextRank, etFirstN.getText().toString(),
                                etLastN.getText().toString(), yearSpinner.getSelectedItem().toString(), etEmaila.getText().toString());
                        if (isInserted){
                            Intent update = new Intent(Ladder.this, ViewKids.class);
                            Toast.makeText(Ladder.this, "Recorded", Toast.LENGTH_LONG).show();
                            startActivity(update);
                        }else{
                            Toast.makeText(Ladder.this, "Fails", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void ViewAll (){
        bViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor  curse = myDB.getAllData();
                        if (curse.getCount() == 0){
                            ShowMessage ("Error", "no Values");
                            return;
                        }
                        StringBuffer sb1 = new StringBuffer();
                        while (curse.moveToNext()){
                            sb1.append("Rank :" + curse.getString(0) + "\n");
                            sb1.append("FirstN :" + curse.getString(1) + "\n");
                            sb1.append("LastN :" + curse.getString(2) + "\n");
                            sb1.append("Year :" + curse.getString(3) + "\n");
                            sb1.append("Email :" + curse.getString(4) + "\n");
                            sb1.append("ID :" + curse.getString(5) + "\n");

                        }
                        ShowMessage("data", sb1.toString());
                    }
                }
        );
    }

    public void btVK(View view) {
        Intent intent = new Intent(this, ViewKids.class);
        startActivity(intent);
    }

    public void ShowMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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

package com.example.chirag.tennis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Pattern;


public class Emails extends AppCompatActivity {

    EditText etEmail;
    Button bSubmite;
    String fileName = "file123.txt";
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);

        etEmail = findViewById(R.id.etEmail);
        bSubmite = findViewById(R.id.bSubmite);

        SubmitEmail();
        tvTest = findViewById(R.id.tvTest);
        tvTest.setText(readFile(fileName));
    }

    public void SubmitEmail(){
        bSubmite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sf = etEmail.getText().toString();
                        if(isValid(sf)){
                            //Toast.makeText(Emails.this, "Valid", Toast.LENGTH_LONG).show();
                            saveFile(fileName, readFile(fileName) + "|" + sf);
                        }else{
                            Toast.makeText(Emails.this, "Invalid", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }



    public void btEvents(View view) {
        Intent intent = new Intent(this, Events.class);
        startActivity(intent);
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

package com.example.chirag.tennis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class homePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView tvTennistitle = findViewById(R.id.tvTennistitle);
        tvTennistitle.setTextColor(Color.parseColor("#0000ff"));
    }

    public void goToViewKids(View view) {
        Intent intent = new Intent(this, ViewKids.class);
        startActivity(intent);
    }

    public void goToCalendar(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }
}

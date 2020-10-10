package com.example.chirag.tennis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Calendar extends AppCompatActivity {


    public String date1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView view = findViewById(R.id.calendarView2);

        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                date1 = month+1+ "/"+date+"/"+year;

            }
        });
    }


    public void goToEvents(View view) {
        Intent intent = new Intent(this, Events.class);
        intent.putExtra(EXTRA_MESSAGE, date1);
        startActivity(intent);
    }

    public void btHP7(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }
}

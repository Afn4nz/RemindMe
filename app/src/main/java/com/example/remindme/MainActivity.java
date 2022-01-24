package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
TextView Time1, Date1;
int t1Hour , t1Minute, y, m,d ;
public static String mypreff = "perf";
     int mode = Activity.MODE_PRIVATE;

    private int notification = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Time1 = findViewById(R.id.time1);
        Date1= findViewById(R.id.date1);
        Button set =findViewById(R.id.set);
        Button cancel =findViewById(R.id.Cancel);
        EditText editText = findViewById(R.id.Task);
        Switch s=  findViewById(R.id.switch1);
        //---------------

        SharedPreferences mypref = getSharedPreferences("perf", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mypref.edit();



        //-------------select Time Button---------------------
        Time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //time picker dialog
                TimePickerDialog t1dialog = new TimePickerDialog(
                        MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                t1Hour =hourOfDay;
                                t1Minute=minute;
                                Calendar calendar =  Calendar.getInstance();
                                calendar.set(0,0,0,t1Hour,t1Minute);
                                Time1.setText(DateFormat.format("hh:mm aa", calendar));

                            }
                        },12,0,false
                );
            t1dialog.updateTime(t1Hour,t1Minute);
                t1dialog.show();
            }
        });

//-------------end select Time Button---------------------

//-----------------------------------------------------------------------------------------------------


//-------------Start of select Date Button---------------------
    Date1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar c= Calendar.getInstance();
            int year =c.get(Calendar.YEAR) ;
             int month =c.get(Calendar.MONTH) ;
             int date= c.get(Calendar.DATE) ;

            DatePickerDialog d1dialog = new DatePickerDialog(MainActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker , int year1, int month1, int dayOfMonth1) {
                             y=year1;
                             m = month1;
                             d= dayOfMonth1;
                             Date1.setText(dayOfMonth1+"/ "+ (month1+1)+"/ "+year1);



                            return;
                        }
                    },
                    year,
                    month,
                    date);
            d1dialog.updateDate(year,month, date);
            d1dialog.show();
            return;
        }
    });
        //-------------end of select Date Button---------------------

//-----------------------------------------------------------------------------------------------------

        //-------------Start of set  Button---------------------

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //if the feilds are empty
                if (Time1.getText().toString().isEmpty()|| Date1.getText().toString().isEmpty() || editText.getText().toString().isEmpty() ){
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setMessage("Please make sure to select Date and Time, and fill the massage feild")
                            .setTitle("wrong")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                AlertDialog alert = b.create();
                alert.show();


                } else {
                    // else


                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(System.currentTimeMillis());

                    //startTime.set(y,m,d,9,53,0);
                    startTime.set(Calendar.YEAR, y);
                    startTime.set(Calendar.MONTH, m);
                    startTime.set(Calendar.DAY_OF_MONTH, d);
                    startTime.set(Calendar.HOUR_OF_DAY, t1Hour);
                    startTime.set(Calendar.MINUTE, t1Minute);
                    startTime.set(Calendar.SECOND, 0);

                    long alarmStartTime = startTime.getTimeInMillis();
                    Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                    //intent.putExtra("notificationId", notification);
                    // intent.putExtra("Task", editText.getText().toString());
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
if(s.isChecked()) {
    editor.putString("TD", editText.getText().toString());
    editor.putString("Time", DateFormat.format("hh:mm aa", startTime).toString());
    editor.putString("Date", d + "/ " + (m + 1) + "/ " + y);
    editor.putBoolean("level", s.isChecked());
    editor.putBoolean("level1", s.isChecked());
}
    else{
    editor.putString("TD1", editText.getText().toString());
    editor.putString("Time1", DateFormat.format("hh:mm aa", startTime).toString());
    editor.putString("Date1", d + "/ " + (m + 1) + "/ " + y);
    editor.putBoolean("level", s.isChecked());



}
    editor.apply();

                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

                }}
        });

        //-------------End of set  Button---------------------

//-----------------------------------------------------------------------------------------------------

        //-------------Start of Cancel  Button---------------------

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time1.setText("Select Time");
                Date1.setText("Select Date");
                editText.setText("");

                Toast.makeText(MainActivity.this, "Canceld", Toast.LENGTH_SHORT).show();
            }
        });
        //-------------End of Cancel  Button---------------------



    }



}

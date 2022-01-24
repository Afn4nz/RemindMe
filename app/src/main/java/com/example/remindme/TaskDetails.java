package com.example.remindme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.example.remindme.MainActivity.mypreff;

public class TaskDetails  extends Activity {
    TextView task, time, date, impor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);

        task = findViewById(R.id.textView5);
        time =findViewById(R.id.textView6);
        date=findViewById(R.id.textView7);
        impor=findViewById(R.id.textView4);
        SharedPreferences mypref = getSharedPreferences("perf",Context.MODE_PRIVATE);
        if(mypref.getBoolean("level",false)){
        task.setText(mypref.getString("TD","notfound"));
        time.setText(mypref.getString("Time","notfound"));
        date.setText(mypref.getString("Date","notfound"));
            impor.setText("YES");}
        else{
            task.setText(mypref.getString("TD1","notfound"));
            time.setText(mypref.getString("Time1","notfound"));
            date.setText(mypref.getString("Date1","notfound"));
            impor.setText("NO");}
        return;

    }

    @Override
 public void onBackPressed() {

        Intent intent =new Intent(TaskDetails.this, MainActivity.class );
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);}
}

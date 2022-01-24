package com.example.remindme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.EditText;
import android.widget.Switch;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import static com.example.remindme.MainActivity.mypreff;


public class ReminderBroadcast extends BroadcastReceiver {

    private static final String CHANNEL_ID="SAMPLE_CHANNEL";
    private static final String CHANNEL_ID2 ="SAMPLE_CHANNEL2";
    int notificationId = 1;
    int notificationId2 = 222;
    Switch s;
    @Override
    public void onReceive(Context context, Intent intent) {

       // int notificationId = intent.getIntExtra("NotificationId", 0);

        SharedPreferences mypref = context.getSharedPreferences ("perf",Context.MODE_PRIVATE);


       //String message = intent.getStringExtra("message");

        Intent mainIntent = new Intent(context, TaskDetails.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context,1, mainIntent , PendingIntent.FLAG_UPDATE_CURRENT );

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(TaskDetails.class);

        stackBuilder.addNextIntent(mainIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);



            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                NotificationChannel channel = new NotificationChannel(CHANNEL_ID ,"N1", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
                NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID2 ,"N12", NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(channel2);

            }
if(mypref.getBoolean("level", false)){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Reminder")
                .setContentText(mypref.getString("TD","notfound"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        notificationManager.notify(notificationId, builder.build());}
else{

    NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, CHANNEL_ID2)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Reminder")
            .setContentText(mypref.getString("TD1","notfound"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(contentIntent);

    notificationManager.notify(notificationId2, builder2.build());
}

    }
}

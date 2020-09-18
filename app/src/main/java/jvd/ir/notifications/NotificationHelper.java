package jvd.ir.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    private static final String ChannelId = "simplified_coding";
    private static final String ChannelName = "Simplified Coding";
    private static final String ChannelDescription = "all four types of notifications";

    public static void displayNotification(Context context, String title, String body,int icon){

        Intent intent = new Intent(context,ProfileActivity.class);
        Intent intent1 = new Intent(context,ProfileActivity.class);
        intent1.putExtra("name","FUCKING SHIT");

        PendingIntent pendingIntent= PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        PendingIntent pendingIntent1 = PendingIntent.getActivity(
                context,
                200,
                intent1,
                PendingIntent.FLAG_CANCEL_CURRENT);

        /*
        RemoteViews remoteViews= new RemoteViews(context.getPackageName(),R.layout.custom_push);
        remoteViews.setImageViewResource(R.id.notif_img,R.drawable.background);
        remoteViews.setTextViewText(R.id.notif_title,title);
        remoteViews.setTextViewText(R.id.notif_Body,body);
         */

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,ChannelId)
                        .setSmallIcon(R.drawable.ic_baseline_accessibility_24,1)
                        .setContentTitle(title)
                        .setLargeIcon(bm)
                        //.setCustomContentView(remoteViews)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(new NotificationCompat.Action(R.drawable.ic_launcher_background,"MYTITLE",pendingIntent1));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());
    }
}

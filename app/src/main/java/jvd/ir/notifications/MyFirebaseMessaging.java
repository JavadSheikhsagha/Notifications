package jvd.ir.notifications;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();
            int icon=R.drawable.ic_launcher_foreground;
            if (remoteMessage.getNotification().getIcon() == null) {
                 icon = R.drawable.ic_baseline_accessibility_24;
            }

            NotificationHelper.displayNotification(getApplicationContext(), title, text, icon);
        }
    }
}

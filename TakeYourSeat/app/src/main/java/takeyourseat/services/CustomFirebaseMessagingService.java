package takeyourseat.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Nenad on 6/16/2017.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.i(TAG, "Random info");
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
}
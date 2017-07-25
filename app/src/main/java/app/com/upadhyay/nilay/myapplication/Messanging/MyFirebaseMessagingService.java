package app.com.upadhyay.nilay.myapplication.Messanging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


import app.com.upadhyay.nilay.myapplication.R;
import app.com.upadhyay.nilay.myapplication.UserInterface.ResultActivity;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by nilay on 2/5/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    JSONArray parentarr;
    JSONArray auctionarr;
    JSONObject jobj = new JSONObject();
    JSONObject auctiondata = new JSONObject();

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        //JSONObject data = (JSONObject) remoteMessage.getData();

        Map<String, String> hashmap = remoteMessage.getData();

        Log.e(TAG, "data: " + hashmap.get("details").toString());
//        try {
//            parentarr = new JSONArray(hashmap.get("details").toString());
//            auctionarr= new JSONArray();
//            //jobj.put("auction",parentarr);
//            //jstring = jobj.toString();
//            for(int i=0;i<parentarr.length();i++) {
//                JSONObject pjobj = new JSONObject();
//                JSONObject jsonObject = parentarr.getJSONObject(i);
//                long startime =jsonObject.getLong("startTime");
//                long currTime = System.currentTimeMillis();
//                long elapsed = currTime - startime;
//                Date date = new Date(currTime);
//                Log.e("date", date.toString());
//                Log.e("starttime", String.valueOf(elapsed));
//
//                int seconds = (int) (elapsed / 1000) % 60 ;
//                int minutes = (int) ((elapsed / (1000*60)) % 60);
//                String time = minutes+"minutes and "+seconds+"seconds ago";
//                pjobj.put("startTime",startime);
//                pjobj.put("elapsed",time);
//                auctionarr.put(pjobj);
//                Log.e("seconds",seconds+"");
//                Log.e("minutes",minutes+"");
//                Log.e("auctionr",auctionarr+"");
//                // int hours   = (int) ((currTime / (1000*60*60)) % 24);
//                //System.out.print("jsonobj",jsonObject.toString());
//                Log.e("jsonobj", jsonObject.toString());
//            }
//            Log.e("o",parentarr.toString());
//            Log.e("N",auctionarr.toString());
//            jobj.put("auction",auctionarr);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            //final String jstring = "{ name: \"Aarhus\", votes: 99 }";
//            @Override
//            public void execute(Realm realm) {
//                realm.createObjectFromJson(AuctionMain.class, jobj);
//            }
//        });
        //Log.e(TAG, "remote message: " + hashmap.get("details"));
        //Log.d(TAG, "Notification Message Body: " + data);
        sendNotification(hashmap.get("details").toString());
    }
    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("msg",message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_plus)
                .setContentTitle("Auction Finished")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}

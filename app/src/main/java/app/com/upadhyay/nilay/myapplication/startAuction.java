package app.com.upadhyay.nilay.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nilay on 2/7/2017.
 */

public class startAuction extends AsyncTask<Void, Void, Void> {
    private String Tag = "startAuction";
    public  startAuction(){

    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.e(Tag,"doInBackground");

        try {
            URL url = new URL("https://coral-core-175.appspot.com/hello");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String returnString ="";

            while((returnString =in.readLine())!= null){
                Log.e("Server Response",returnString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startProgress(){
       // new Thread(new Task()).start();
    }
    /*class Task implements Runnable {
        private String Tag = "startAuction";
        @Override
        public void run() {
            Log.e(Tag,"Running");
        }
    }*/

}


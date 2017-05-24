package app.com.upadhyay.nilay.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nilay.myapplication.backend.auctionApi.AuctionApi;
import com.example.nilay.myapplication.backend.registrationRecordApi.RegistrationRecordApi;
import com.example.nilay.myapplication.backend.registrationRecordApi.model.RegistrationRecord;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.MissingFormatArgumentException;
import java.util.logging.Logger;

/**
 * Created by nilay on 2/5/2017.
 */
public class RegistrationEndpointsAsyncTask extends AsyncTask<String, Void, Void> {
    private static RegistrationRecordApi registrationRecordApi = null;
    private MainActivity.AsyncRegistraion registrationDelegate;
    //private MyFirebaseInstanceIDService.registration registration;
    private static Logger myLogger = Logger.getLogger(RegistrationEndpointsAsyncTask.class.getName());
    RegistrationRecord registrationRecord;
    public RegistrationEndpointsAsyncTask(MainActivity.AsyncRegistraion registrationDelegate){
           this.registrationDelegate = registrationDelegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("RE","PreExecute");
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("RE","PostExecute");
        registrationDelegate.onSuccess();
    }
    @Override
    protected Void doInBackground(String... regInfo) {
        Log.e("inback","regInfo");
        if(registrationRecordApi == null){
            RegistrationRecordApi.Builder builder = new
                    RegistrationRecordApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
// options for running against local devappserver
// — 10.0.2.2 is localhost’s IP address in Android emulator
// — turn off compression when running against local devappserver
                    .setRootUrl("https://coral-core-175.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?>    abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            registrationRecordApi = builder.build();
        }
        try {
            registrationRecord = new RegistrationRecord();
            registrationRecord.setRegId(regInfo[1]);
            registrationRecord.setUserID(regInfo[0]);
            registrationRecord.setType(regInfo[2]);
            registrationRecordApi.register(registrationRecord).execute();
            //myLogger.warning("Device Registration Id"+regId);
        }
        catch (Exception e){
        }
        return null;
    }
}

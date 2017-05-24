package app.com.upadhyay.nilay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.nilay.myapplication.backend.auctionApi.AuctionApi;
import com.example.nilay.myapplication.backend.auctionApi.model.Auction;
import java.io.IOException;

/**
 * Created by nilay on 11/8/2016.
 */

class EndpointsAsyncTask extends AsyncTask<Integer, Void, Auction> {

    private static AuctionApi myApiService = null;
    private ProgressDialog pd;
    private PlaceBid.FragmentCallback mfragmentCallback;
   // private static QuoteEndpoint myApiService = null;
    private Context context;
    EndpointsAsyncTask(PlaceBid.FragmentCallback fragmentCallback) {
        mfragmentCallback = fragmentCallback;
        Log.e("constructor","endpoint");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //pd = new ProgressDialog(context);
        //spd.setMessage("Adding Quote");
    }
    @Override
    protected Auction doInBackground(Integer... bid) {
        if(myApiService == null) { // Only do this once
            Log.e("service ","null");
            AuctionApi.Builder builder = new
                    AuctionApi.Builder(AndroidHttp.newCompatibleTransport(),
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
// end options for devappserver
            myApiService = builder.build();
        }
        try {
            Auction auction = new Auction();
            //myApiService.insert(auction.set).execute();
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    protected void onPostExecute(Auction auction) {
        //pd.dismiss();
        Log.e("onPost","EndPoint");
        mfragmentCallback.onTaskDone();
    }
}

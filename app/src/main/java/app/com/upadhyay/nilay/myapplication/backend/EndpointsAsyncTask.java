package app.com.upadhyay.nilay.myapplication.backend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.nilay.myapplication.backend.auctionApi.AuctionApi;
import com.example.nilay.myapplication.backend.auctionApi.model.Auction;
import java.io.IOException;


import app.com.upadhyay.nilay.myapplication.UserInterface.PlaceBid;
import app.com.upadhyay.nilay.myapplication.UserInterface.MainActivity;

/**
 * Created by nilay on 11/8/2016.
 */

public class EndpointsAsyncTask extends AsyncTask<String, Void, Auction> {

    private static AuctionApi myApiService = null;
    private ProgressDialog pd;
    private PlaceBid.FragmentCallback mfragmentCallback;
   // private static QuoteEndpoint myApiService = null;
    private Context context;
    public EndpointsAsyncTask(PlaceBid.FragmentCallback fragmentCallback) {
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
    protected Auction doInBackground(String... auctioninfo) {
        if(myApiService == null) { // Only do this once
            Log.e("service ","null");
            AuctionApi.Builder builder = new
                    AuctionApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
// options for running against local devappserver
// — 10.0.2.2 is localhost’s IP address in Android emulator
// — turn off compression when running against local devappserver
                    .setRootUrl(MainActivity.appurl)
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
            auction.setAuctionID(auctioninfo[0]);
            auction.setAuctionName(auctioninfo[1]);
            auction.setUserID(auctioninfo[2]);
            auction.setBid(Integer.valueOf(auctioninfo[3]));
            auction.setType(auctioninfo[4]);
            auction.setRegisID(auctioninfo[5]);
            auction.setPlocation(auctioninfo[6]);
            auction.setPdestination(auctioninfo[7]);
            auction.setRegisID(auctioninfo[5]);
            myApiService.insert(auction).execute();
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    protected void onPostExecute(Auction auction) {
        //pd.dismiss();
        //Log.e("onPost","EndPoint");
        mfragmentCallback.onTaskDone();
    }
}

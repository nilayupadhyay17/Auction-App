package app.com.upadhyay.nilay.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.nilay.myapplication.backend.auctionTimeApi.AuctionTimeApi;
import com.example.nilay.myapplication.backend.auctionTimeApi.model.AuctionTime;
import com.example.nilay.myapplication.backend.auctionTimeApi.model.AuctionTimeCollection;
import com.example.nilay.myapplication.backend.gCMMessagesApi.GCMMessagesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;

/**
 * Created by nilay on 3/29/2017.
 */

public class AuctionDetailsEndpoint extends AsyncTask<String,Void,List<AuctionTime>>{
    private static AuctionTimeApi auctionTimeApi= null;
    private Context mContext;
    JSONArray auctiondataarr;
    JSONObject auctiondata;
    public MainActivity.AsyncRespose delegate;
    public  AuctionDetailsEndpoint(MainActivity.AsyncRespose delegate){
        this.delegate = delegate;
    }
    @Override
    protected List<AuctionTime> doInBackground(String... params) {
        if(auctionTimeApi == null){
            AuctionTimeApi.Builder builder = new
                    AuctionTimeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
// options for running against local devappserver
// — 10.0.2.2 is localhost’s IP address in Android emulator
// — turn off compression when running against local devappserver
                    .setRootUrl("https://coral-core-175.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            auctionTimeApi = builder.build();
        }
            int k =0;
        AuctionTimeCollection list= null;
        try {
            list = auctionTimeApi.list().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.getItems();
    }

    @Override
    protected void onPreExecute() {
//        Toast.makeText(mContext, "Fetching list", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(List<AuctionTime> auctionTime) {
        delegate.progressFinish(auctionTime);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

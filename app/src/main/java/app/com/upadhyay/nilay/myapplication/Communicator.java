package app.com.upadhyay.nilay.myapplication;

import android.view.View;

import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;
import io.realm.RealmResults;

/**
 * Created by nilay on 4/7/2017.
 */

public interface Communicator {
    public void respond();
    public void auctionClicked(View view, int position, String[] auctionDetail);
}

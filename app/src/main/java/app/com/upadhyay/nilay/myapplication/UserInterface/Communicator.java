package app.com.upadhyay.nilay.myapplication.UserInterface;

import android.view.View;

import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;
import io.realm.RealmResults;

/**
 * Created by nilay on 4/7/2017.
 */

public interface Communicator {
    public void respond(String s, String toString);
    public void auctionClicked(View view, int position, String[] auctionDetail);
    public void loginResponse(String username, String type);
}

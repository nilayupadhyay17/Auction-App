package app.com.upadhyay.nilay.myapplication.beans;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by nilay on 3/13/2017.
 */

public class AuctionMain extends RealmObject {

private RealmList<AuctionDetail> auction;

    public RealmList<AuctionDetail> getAuction() {
        return auction;
    }

    public void setAuction(RealmList<AuctionDetail> auction) {
        this.auction = auction;
    }

}

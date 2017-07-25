package com.example.nilay.myapplication.backend;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
/**
 * Created by nilay on 11/8/2016.
 */

public class ofyService {

    static {
        ObjectifyService.register(Auction.class);
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(AuctionWinner.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
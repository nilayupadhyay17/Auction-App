package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.ObjectifyService;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.example.nilay.myapplication.backend.ofyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "auctionWinnerApi",
        version = "v1",
        resource = "auctionWinner",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class AuctionWinnerEndpoint {

    private static final Logger logger = Logger.getLogger(AuctionWinnerEndpoint.class.getName());
    List<AuctionWinner> auctionWinner;
    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(AuctionWinner.class);
    }

    @ApiMethod(name = "getAuctionWinner")
    public List<AuctionWinner> getAuctionWinner() {
        // TODO: Implement this function
        logger.info("Calling getAuctionWinner method");
        auctionWinner = ofy().load().type(AuctionWinner.class).list();
        return auctionWinner;
    }

    /**
     * This inserts a new <code>AuctionWinner</code> object.
     *
     * @param auctionWinner The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertAuctionWinner")
    public AuctionWinner insertAuctionWinner(AuctionWinner auctionWinner) {
        // TODO: Implement this function
        logger.info("Calling insertAuctionWinner method");
        ofy().save().entity(auctionWinner).now();
        return auctionWinner;
    }
}
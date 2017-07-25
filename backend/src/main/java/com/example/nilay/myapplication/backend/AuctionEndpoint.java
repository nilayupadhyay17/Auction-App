package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import com.google.appengine.api.users.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import static com.example.nilay.myapplication.backend.ofyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "auctionApi",
        version = "v1",
        resource = "auction",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class AuctionEndpoint {

    private static final Logger logger = Logger.getLogger(AuctionEndpoint.class.getName());

    String userId = null;
    String mainEmail = null;
    String displayName = "Your name will go here";
    private static final int DEFAULT_LIST_LIMIT = 20;
    List<Auction> auction;

    public AuctionEndpoint() {
    }

    private static String extractDefaultDisplayNameFromEmail(String email) {
        return email == null ? null : email.substring(0, email.indexOf("@"));
    }
    /**
     * Returns the {@link Auction} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Auction} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "auction/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Auction get(User user ,@Named("id") Long id) throws NotFoundException,UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        //logger.info("Getting Auction with ID: " + id);
        /*Auction auction = ofy().load().type(Auction.class).id(id).now();
        if (auction == null) {
            throw new NotFoundException("Could not find Auction with ID: " + id);
        }*/
        String userId = ""; // TODO
        Key key = null; // TODO
        Auction auction = null; // TODO load the Profile entity
        userId = user.getUserId();
        key = key.create(Auction.class,userId);
        auction = (Auction) ofy().load().key(key).now();
        return auction;
    }

    /**
     *  This  method will be used by IOS
     */
   @ApiMethod(
            name = "insertAuction",
            httpMethod = ApiMethod.HttpMethod.POST,
            path = "auctionInsert")
    public void insertAuction(@Named("auctionID") String auctionID,@Named("auctionName") String auctionName,@Named("userID") String userID,
       @Named("type") String type, @Named("bid") Integer bid,@Named("regisID") String regisID, @Named("dlocation") String dlocation,
                       @Named("plocation") String plocation, @Named("pdestination") String pdestination

    ) {
       Auction nauction = new Auction(userID, bid, auctionName, type, regisID, dlocation, plocation, pdestination);
       nauction.setUserID(userID);
       nauction.setType(type);
       nauction.setBid(bid);
       nauction.setRegisID(regisID);
       nauction.setDlocation(dlocation);
       nauction.setPlocation(plocation);
       nauction.setPdestination(pdestination);
       nauction.setAuctionID(auctionID);
       ofy().save().entity(nauction).now();
   }

    @ApiMethod(
            name = "insert",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(Auction auction) {
        auction.setBid(auction.getBid());
        auction.setUserID(auction.getUserID());
        auction.setAuctionID(auction.getAuctionID());
        auction.setType(auction.getType());
        auction.setAuctionName(auction.getAuctionName());
        auction.setRegisID(auction.getRegisID());
        ofy().save().entity(auction).now();
    }
    @ApiMethod(
            name = "getTraderList",
            path = "getTraderList",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public List<Auction> getTradersList(final User user) throws UnauthorizedException{
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }
        // TODO
        // load the Profile Entity
        String userId = user.getUserId();
        Key key = Key.create(Auction.class, userId);
        List<Auction> auction = ofy().load().type(Auction.class).list();
        return auction;

    }
    /**
     * Updates an existing {@code Auction}.
     *
     * @param id      the ID of the entity to be updated
     * @param auction the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Auction}
     */
    @ApiMethod(
            name = "update",
            path = "auction/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Auction update(@Named("id") Long id, Auction auction) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(auction).now();
        logger.info("Updated Auction: " + auction);
        return ofy().load().entity(auction).now();
    }

    /**
     * Deletes the specified {@code Auction}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Auction}
     */
    @ApiMethod(
            name = "remove",
            path = "auction/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Auction.class).id(id).now();
        logger.info("Deleted Auction with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "auction",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Auction> list() {

        //Auction au = ofy().load().type(Auction.class).id("1").now();
        auction = ofyService.ofy().load().type(Auction.class).list();
       // Iterable<Auction> query = ofy().load().type(Auction.class).filter("auctionID =", id);
        Iterable<Auction> query = ofy().load().type(Auction.class);
        List<Auction> list = new ArrayList<Auction>();
        for (Auction auc: query) {
            list.add(auc);
        }
        return list;
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Auction.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Auction with ID: " + id);
        }
    }
}
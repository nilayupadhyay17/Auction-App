package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.Key;
import com.google.appengine.api.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "biddingApi",
        version = "v1",
        resource = "bidding",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class BiddingEndpoint {

    private static final Logger logger = Logger.getLogger(BiddingEndpoint.class.getName());
    String userId = null;
    String mainEmail = null;
    String displayName = "Your name will go here";

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Bidding.class);
    }

    /**
     * Returns the {@link Bidding} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Bidding} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bidding/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Bidding get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting Bidding with ID: " + id);
        Bidding bidding = ofy().load().type(Bidding.class).id(id).now();
        if (bidding == null) {
            throw new NotFoundException("Could not find Bidding with ID: " + id);
        }
        return bidding;
    }
    private static String extractDefaultDisplayNameFromEmail(String email) {
        return email == null ? null : email.substring(0, email.indexOf("@"));
    }
    /**
     * Inserts a new {@code Bidding}.
     */
    @ApiMethod(
            name = "insert",
            path = "bidding",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Bidding insert(User user, Bidding bidding)throws UnauthorizedException {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bidding.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        userId =user.getUserId();
        mainEmail=user.getEmail();
        Integer bid = bidding.getBid();
        Key key =null;
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }
        Bidding biddingobj = (Bidding) ofy().load().key(key.create(Bidding.class,userId)).now();
        if(biddingobj == null){
            if(displayName == null){
                displayName = extractDefaultDisplayNameFromEmail(user.getEmail());
            }
            biddingobj = new Bidding(userId,bid);
        }
        else{
            //profile.update(displayName,teeShirtSize);
        }
        ofy().save().entity(biddingobj).now();
        //logger.info("Created Auction with ID: " + auction.getId());
        return ofy().load().entity(biddingobj).now();
    }

    /**
     * Updates an existing {@code Bidding}.
     *
     * @param id      the ID of the entity to be updated
     * @param bidding the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bidding}
     */
    @ApiMethod(
            name = "update",
            path = "bidding/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Bidding update(@Named("id") String id, Bidding bidding) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(bidding).now();
        logger.info("Updated Bidding: " + bidding);
        return ofy().load().entity(bidding).now();
    }

    /**
     * Deletes the specified {@code Bidding}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bidding}
     */
    @ApiMethod(
            name = "remove",
            path = "bidding/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Bidding.class).id(id).now();
        logger.info("Deleted Bidding with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "bidding",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bidding> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bidding> query = ofy().load().type(Bidding.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bidding> queryIterator = query.iterator();
        List<Bidding> biddingList = new ArrayList<Bidding>(limit);
        while (queryIterator.hasNext()) {
            biddingList.add(queryIterator.next());
        }
        return CollectionResponse.<Bidding>builder().setItems(biddingList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(Bidding.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bidding with ID: " + id);
        }
    }
}
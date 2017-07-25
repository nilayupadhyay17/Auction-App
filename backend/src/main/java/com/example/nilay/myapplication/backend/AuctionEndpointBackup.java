//package com.example.nilay.myapplication.backend;
//
//import com.google.api.server.spi.response.UnauthorizedException;
//import com.google.appengine.api.taskqueue.Queue;
//import com.google.appengine.api.taskqueue.QueueFactory;
//import com.google.appengine.api.taskqueue.TaskOptions;
//import com.googlecode.objectify.Key;
//import com.google.appengine.api.users.User;
//import com.google.api.server.spi.config.Api;
//import com.google.api.server.spi.config.ApiMethod;
//import com.google.api.server.spi.config.ApiNamespace;
//import com.google.api.server.spi.response.CollectionResponse;
//import com.google.api.server.spi.response.NotFoundException;
//import com.google.appengine.api.datastore.Cursor;
//import com.google.appengine.api.datastore.QueryResultIterator;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.Work;
//import com.googlecode.objectify.cmd.Query;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Logger;
//
//import javax.annotation.Nullable;
//import javax.inject.Named;
//import static com.example.nilay.myapplication.backend.ofyService.ofy;
//
///**
// * WARNING: This generated code is intended as a sample or starting point for using a
// * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
// * restrictions and no data validation.
// * <p>
// * DO NOT deploy this code unchanged as part of a real application to real users.
// */
//@Api(
//        name = "auctionApi",
//        version = "v1",
//        resource = "auction",
//        namespace = @ApiNamespace(
//                ownerDomain = "backend.myapplication.nilay.example.com",
//                ownerName = "backend.myapplication.nilay.example.com",
//                packagePath = ""
//        )
//)
//public class AuctionEndpointBackup {
//
//    private static final Logger logger = Logger.getLogger(AuctionEndpoint.class.getName());
//
//    String userId = null;
//    String mainEmail = null;
//    String displayName = "Your name will go here";
//    private static final int DEFAULT_LIST_LIMIT = 20;
//    private static String extractDefaultDisplayNameFromEmail(String email) {
//        return email == null ? null : email.substring(0, email.indexOf("@"));
//    }
//    /**
//     * Returns the {@link Auction} with the corresponding ID.
//     *
//     * @param id the ID of the entity to be retrieved
//     * @return the entity with the corresponding ID
//     * @throws NotFoundException if there is no {@code Auction} with the provided ID.
//     */
//    @ApiMethod(
//            name = "get",
//            path = "auction/{id}",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public Auction get(User user ,@Named("id") Long id) throws NotFoundException,UnauthorizedException {
//        if (user == null) {
//            throw new UnauthorizedException("Authorization required");
//        }
//        //logger.info("Getting Auction with ID: " + id);
//        /*Auction auction = ofy().load().type(Auction.class).id(id).now();
//        if (auction == null) {
//            throw new NotFoundException("Could not find Auction with ID: " + id);
//        }*/
//        String userId = ""; // TODO
//        Key key = null; // TODO
//        Auction auction = null; // TODO load the Profile entity
//        userId = user.getUserId();
//        key = key.create(Auction.class,userId);
//        auction = (Auction) ofy().load().key(key).now();
//        return auction;
//    }
//
//    /**
//     * Inserts a new {@code Auction}.
//     */
//    @ApiMethod(
//            name = "insert",
//            path = "auction",
//            httpMethod = ApiMethod.HttpMethod.POST)
//    public Auction insert(User user, final Auction auction)throws UnauthorizedException {
//        userId =user.getUserId();
//        mainEmail=user.getEmail();
//        final Integer bid = auction.getBid();
//        Key key =null;
//        logger.info("userId");
//        final String emailID = user.getEmail();
//        final Date date = new Date();
//        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
//        // You should validate that auction.id has not been set. If the ID type is not supported by the
//        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
//        //
//        // If your client provides the ID then you should probably use PUT instead.
//        if (user == null) {
//            throw new UnauthorizedException("Authorization required");
//        }
//        final Key<Auction> auctionKey = key.create(Auction.class,userId);
//        final Queue queue = QueueFactory.getDefaultQueue();
//
//        Auction auctionobj = ofy().transact(new Work<Auction>() {
//
//            @Override
//            public Auction run() {
//
//                Auction auctionobj = (Auction) ofy().load().key(auctionKey).now();
//                if(auctionobj == null){
//                    if(displayName == null){
//                        //displayName = extractDefaultDisplayNameFromEmail(emailID);
//                    }
//                    auctionobj = new Auction(userId,bid,auction.getType(),date);
//                }
//                else{
//                    auctionobj = new Auction(userId,bid,auction.getType(),date);
//                }
//                ofy().save().entity(auctionobj).now();
//                queue.add(ofy().getTransaction(),
//                        TaskOptions.Builder.withUrl("/tasks/startAuction")
//                                .param("Bid", auction.getBid().toString())
//                                .param("TraderType",auction.getType().toString()));
//                return auctionobj;
//            }
//        });
//        logger.warning("Created Auction with ID: " + auction.getId());
//        return auctionobj;
//    }
//    @ApiMethod(
//            name = "getTraderList",
//            path = "getTraderList",
//            httpMethod = ApiMethod.HttpMethod.PUT)
//    public List<Auction> getTradersList(final User user) throws UnauthorizedException{
//        if (user == null) {
//            throw new UnauthorizedException("Authorization required");
//        }
//        // TODO
//        // load the Profile Entity
//        String userId = user.getUserId();
//        Key key = Key.create(Auction.class, userId);
//        List<Auction> auction = ofy().load().type(Auction.class).list();
//        return auction;
//    }
//    /**
//     * Updates an existing {@code Auction}.
//     *
//     * @param id      the ID of the entity to be updated
//     * @param auction the desired state of the entity
//     * @return the updated version of the entity
//     * @throws NotFoundException if the {@code id} does not correspond to an existing
//     *                           {@code Auction}
//     */
//    @ApiMethod(
//            name = "update",
//            path = "auction/{id}",
//            httpMethod = ApiMethod.HttpMethod.PUT)
//    public Auction update(@Named("id") Long id, Auction auction) throws NotFoundException {
//        // TODO: You should validate your ID parameter against your resource's ID here.
//        checkExists(id);
//        ofy().save().entity(auction).now();
//        logger.info("Updated Auction: " + auction);
//        return ofy().load().entity(auction).now();
//    }
//
//    /**
//     * Deletes the specified {@code Auction}.
//     *
//     * @param id the ID of the entity to delete
//     * @throws NotFoundException if the {@code id} does not correspond to an existing
//     *                           {@code Auction}
//     */
//    @ApiMethod(
//            name = "remove",
//            path = "auction/{id}",
//            httpMethod = ApiMethod.HttpMethod.DELETE)
//    public void remove(@Named("id") Long id) throws NotFoundException {
//        checkExists(id);
//        ofy().delete().type(Auction.class).id(id).now();
//        logger.info("Deleted Auction with ID: " + id);
//    }
//
//    /**
//     * List all entities.
//     *
//     * @param cursor used for pagination to determine which page to return
//     * @param limit  the maximum number of entries to return
//     * @return a response that encapsulates the result list and the next page token/cursor
//     */
//    @ApiMethod(
//            name = "list",
//            path = "auction",
//            httpMethod = ApiMethod.HttpMethod.GET)
//    public CollectionResponse<Auction> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
//        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
//        Query<Auction> query = ofy().load().type(Auction.class).limit(limit);
//        if (cursor != null) {
//            query = query.startAt(Cursor.fromWebSafeString(cursor));
//        }
//        QueryResultIterator<Auction> queryIterator = query.iterator();
//        List<Auction> auctionList = new ArrayList<Auction>(limit);
//        while (queryIterator.hasNext()) {
//            auctionList.add(queryIterator.next());
//        }
//        return CollectionResponse.<Auction>builder().setItems(auctionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
//    }
//
//    private void checkExists(Long id) throws NotFoundException {
//        try {
//            ofy().load().type(Auction.class).id(id).safe();
//        } catch (com.googlecode.objectify.NotFoundException e) {
//            throw new NotFoundException("Could not find Auction with ID: " + id);
//        }
//    }
//}
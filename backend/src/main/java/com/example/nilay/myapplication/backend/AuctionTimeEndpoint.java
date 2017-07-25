package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.repackaged.com.google.api.client.util.DateTime;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        name = "auctionTimeApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class AuctionTimeEndpoint {

    private static final Logger logger = Logger.getLogger(AuctionTimeEndpoint.class.getName());
    private static final int DEFAULT_LIST_LIMIT = 20;
    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(AuctionTime.class);
        ObjectifyService.register(Auction.class);
    }
    List<AuctionTime> auctionTime = null;
    JSONArray auctiondataarr;
    JSONObject auctiondata;
    /**
     * Returns the {@link AuctionTime} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code AuctionTime} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "auctionTime/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public AuctionTime get(@Named("Id") Long Id) throws NotFoundException {
        logger.info("Getting AuctionTime with ID: " + Id);
        AuctionTime auctionTime = ofy().load().type(AuctionTime.class).id(Id).now();
        if (auctionTime == null) {
            throw new NotFoundException("Could not find AuctionTime with ID: " + Id);
        }
        return auctionTime;
    }
    /**
     * Inserts a new {@code AuctionTime}.
     */
    @ApiMethod(
            name = "insert",
            httpMethod = ApiMethod.HttpMethod.POST,
            path = "auctionTime")
    public void insert(@Named("totalTime") Long totalTime,@Named("name") String name) {
        long currTime = System.currentTimeMillis();
        long time = totalTime *60000;
        long endTime = currTime+time;
        Date date = new Date(currTime);
        AuctionTime auctionTime = new AuctionTime();
        auctionTime.setTotalTime(totalTime);
        auctionTime.setStartTime(currTime);
        auctionTime.setDateTime(date);
        auctionTime.setEndTime(endTime);
        auctionTime.setAuctionName(name);
        auctionTime.setStatus(0);
        Auction au = new Auction();
        au.setAuctionName(name);
        au.setEndtime(endTime);
        ofy().save().entity(au).now();
        ofy().save().entity(auctionTime).now();
    }
    @ApiMethod(
            name = "list")
    public List<AuctionTime> getAuctionList() throws IOException{
        auctionTime = ofyService.ofy().load().type(AuctionTime.class).list();
       return auctionTime;
    }
    private JSONObject getAuctionData() {
        auctiondataarr = new JSONArray();
        auctiondata = new JSONObject();
        /*try {
            for (int i = 0; i < auctionTime.size(); i++) {
                JSONObject auction = new JSONObject();
               *//* auction.put("id", auctionTime.get(i).Id);
                auction.put("startTime", auctionTime.get(i).getStartTime());
                auction.put("endTime", auctionTime.get(i).getEndTime());*//*
                auctiondataarr.put(auction);
            }
            auctiondata.put("details", auctiondataarr);
            String str = auctiondata.toString();
            System.out.println("String "+str);
        }
        catch(JSONException e){
        }*/
        return auctiondata;
    }


}
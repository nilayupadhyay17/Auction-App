package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
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
        name = "registrationRecordApi",
        version = "v1",
        resource = "registrationRecord",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class RegistrationRecordEndpoint {

    private static final Logger logger = Logger.getLogger(RegistrationRecordEndpoint.class.getName());
    private static final int DEFAULT_LIST_LIMIT = 20;

    @ApiMethod(name = "register")
    public void registerDevice(RegistrationRecord registrationRecord) {
        if (findRecord(registrationRecord.getRegId()) != null) {
            logger.info("Device " + registrationRecord.getRegId() + " already registered, skipping register");
            return;
        }
        //RegistrationRecord registrationRecord = new RegistrationRecord();
        registrationRecord.setUserID(registrationRecord.getUserID());
        registrationRecord.setRegId(registrationRecord.getRegId());
        ofy().save().entity(registrationRecord).now();
    }
    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId",regId).first().now();
    }
    @ApiMethod(name = "listDevices")
    public CollectionResponse<RegistrationRecord> listDevices(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<RegistrationRecord> query =ofy().load().type(RegistrationRecord.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<RegistrationRecord> queryIterator = query.iterator();
        List<RegistrationRecord> testList = new ArrayList<RegistrationRecord>(limit);
        while (queryIterator.hasNext()) {
            testList.add(queryIterator.next());
        }
        return CollectionResponse.<RegistrationRecord>builder().setItems(testList).build();
    }
}
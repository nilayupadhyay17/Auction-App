package com.example.nilay.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
//import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import static com.google.appengine.repackaged.com.google.common.base.StringUtil.JsEscapingMode.JSON;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "gCMMessagesApi",
        version = "v1",
        resource = "gCMMessages",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.nilay.example.com",
                ownerName = "backend.myapplication.nilay.example.com",
                packagePath = ""
        )
)
public class GCMMessagesEndpoint {

    private static final Logger logger = Logger.getLogger(GCMMessagesEndpoint.class.getName());
    private static final String GCM_SEND_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String UTF8 = "UTF-8";
    private static final String PARAM_TO = "registration_ids";
    private static final String PARAM_JSON_PAYLOAD = "data";
    private static final String PARAM_TIME_TO_LIVE = "time_to_live";
    private static final int DEFAULT_LIST_LIMIT = 20;
    List<RegistrationRecord> registrationRecord = null;
    List<AuctionTime> auctionTime = null;
    String[] tokenarray ;
    Long[] auctionarr;
    String[] strings;
    JSONArray auctiondataarr;
    JSONObject auctiondata;
    JSONObject winner;
    JSONObject jsonBody;
    private static String CLIENTID = "";

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GCMMessages.class);
    }
 private void getRegistrationId(){
      registrationRecord = ofyService.ofy().load().type(RegistrationRecord.class).limit(10).list();
      CollectionResponse<RegistrationRecord> cr= CollectionResponse.<RegistrationRecord>builder().setItems(registrationRecord).build();
      tokenarray = new String[registrationRecord.size()];
      for(int i=0;i<registrationRecord.size();i++){

          tokenarray[i] = registrationRecord.get(i).getRegId().toString();
      }
  }
    private void getAuctionList() {

        auctiondataarr= new JSONArray();
        auctiondata = new JSONObject();

            JSONObject auction = new JSONObject();
           // auctionarr[i] = auctionTime.get(i).getStartTime();
            auction.put("message","Auction Finished");
            auctiondataarr.put(auction);
        //System.out.print("auction data size !!"+auctiondata);
        //System.out.println("fetched auction of size"+auctionarr.toString());
        auctiondata.put("details",auctiondataarr);

    }
/*Map<Integer, String> treeMap = new TreeMap<Integer, String>(
        new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }

        });*/
    @ApiMethod(name = "calculatewinners", path="auction")
    public void calculatewinners(@Named("auctionID") String auctionid) {

        auctiondataarr= new JSONArray();
        auctiondata = new JSONObject();
        tokenarray = new String[2];
        Map<String,Integer> mapDriver = new HashMap<String, Integer>();
        Map<String,Integer> mapPass = new HashMap<String, Integer>();
        Iterable<Auction> driver = ofyService.ofy().load().type(Auction.class).filter("type =", "Driver").filter("auctionID =",auctionid);
        Iterable<Auction> passenger = ofyService.ofy().load().type(Auction.class).filter("type =", "Passenger").filter("auctionID =",auctionid);

        List<Auction> driverList = new ArrayList<Auction>();
        List<Auction> passengerList = new ArrayList<Auction>();


        // System.out.print("key "+wd.getKey());
        //System.out.print("key1 "+wd1.getKey());



        // Iterable<Auction> regisDriver = ofyService.ofy().load().type(Auction.class).filter("userID =",wd.getKey());
        // Iterable<Auction> regisPassenger = ofyService.ofy().load().type(Auction.class).filter("userID =",wd1.getKey());
        HashMap<String,ArrayList<String>> hmap= new HashMap<String,ArrayList<String>>();
        winner  = new JSONObject();

        for (Auction ad : driver) {
            // JSONObject alldata = new JSONObject();
            ArrayList<String> arrList =new ArrayList<String>();
            arrList.add(ad.getAuctionID());
            arrList.add(ad.getAuctionName());
            arrList.add(String.valueOf(ad.getBid()));
            arrList.add(ad.getDlocation());
            arrList.add(ad.getRegisID());
            hmap.put(ad.getUserID(),arrList);
            mapDriver.put(ad.getUserID(), ad.getBid());
            //jarr.put(alldata);
        }
        System.out.println("map d size" + mapDriver.size());
        for (Auction ap : passenger) {

            ArrayList<String> parrList =new ArrayList<String>();
            parrList.add(ap.getAuctionID());
            parrList.add(ap.getAuctionName());
            parrList.add(ap.getPlocation());
            parrList.add(ap.getPdestination());
            parrList.add(String.valueOf(ap.getBid()));
            parrList.add(ap.getRegisID());
            hmap.put(ap.getUserID(),parrList);
            mapPass.put(ap.getUserID(), ap.getBid());
        }
        // System.out.println("map p "+mapPass.size());
        Map<String, Integer> sortedDMap = sortByDriver(mapDriver);
        Map<String, Integer> sortedPMap = sortByValue(mapPass);
        for(Map.Entry entry : sortedDMap.entrySet()){
            System.out.println("driver "+entry.getValue());
        }
        for(Map.Entry entry : sortedPMap.entrySet()){
            System.out.println("ass  "+entry.getValue());
        }
        //System.out.println("sorted d "+sortedDMap.size());
        //System.out.println("sorted P "+sortedPMap.size());


        Iterator<Map.Entry<String, Integer>> wd = sortedDMap.entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> wd1 = sortedPMap.entrySet().iterator();

        Map<String,Integer> winnerDriver = new LinkedHashMap<String, Integer>();
        Map<String,Integer> winnerPass = new LinkedHashMap<String, Integer>();
        float price =0;
        int tempdval =0;
        int temppval =0;
        while(wd.hasNext()){
            try {
                Map.Entry dpairs = (Map.Entry) wd.next();
                Map.Entry ppairs = (Map.Entry) wd1.next();
                int dval = (int) dpairs.getValue();
                int pval = (int) ppairs.getValue();
                System.out.println("dval "+dval);
                System.out.println("pval "+pval);
                if(pval > dval){
                    winnerDriver.put((String) dpairs.getKey(),dval);
                    winnerPass.put((String)ppairs.getKey(),pval);
                }else {

                    System.out.println("dval 1 "+dval);
                    System.out.println("pval 1 "+pval);
                    //if (!wd.hasNext() || !wd1.hasNext()) {
                    //Map.Entry ldpairs = (Map.Entry) wd.next();
                    //Map.Entry lppairs = (Map.Entry) wd1.next();

                    int low = Math.min(temppval, dval);
                    int high = Math.max(tempdval, pval);

                    System.out.println("low"+low);
                    System.out.println("high"+high);
                    System.out.println("tempdval"+tempdval);
                    System.out.println("temppval"+temppval);

                    price = (float)(low + high) / 2;
                    System.out.print(price);
                    break;
                    // }

                }
                tempdval = dval;
                temppval = pval;
            }

            catch (NoSuchElementException nosuchElement){

                price = 0;
            }
        }
        if(price ==0){

            int npvl = tempdval -1;
            int ndvl = temppval +1;
            int low = Math.min(temppval, npvl);
            int high = Math.max(tempdval, ndvl);
            price = (float)(low+high)/2;
            System.out.print("Price zero");
            System.out.print(price);
        }
        System.out.print("price"+price);

       /* for(Map.Entry<String,Integer> entry : winnerDriver.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key);
            System.out.println(value);
        }
        for(Map.Entry<String,Integer> entry : winnerPass.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key);
            System.out.println(value);
        }*/
        Iterator<Map.Entry<String, Integer>> wd2 = winnerDriver.entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> wd3 = winnerPass.entrySet().iterator();
        while(wd2.hasNext()){
            System.out.println("inside ");
            JSONObject jn = new JSONObject();
            Map.Entry dpairs1 = (Map.Entry) wd2.next();
            Map.Entry ppairs1 = (Map.Entry) wd3.next();
            ArrayList<String> as= hmap.get(dpairs1.getKey());
            ArrayList<String> ap= hmap.get(ppairs1.getKey());

            //String jon = new Gson().toJson(as);
            // String jon1 = new Gson().toJson(ap);
            // System.out.println(jon.toString()+""+jon1.toString());
            // System.out.println(jon1);
            // System.out.println(jon);
            //for(String str: as){
            AuctionWinner auctionWinner = new AuctionWinner();
            //   System.out.println(str);
            auctionWinner.setAuctionID(as.get(0));
            auctionWinner.setAuctionName(as.get(1));
            auctionWinner.setUserD(dpairs1.getKey().toString());
            auctionWinner.setBidD(Integer.parseInt(as.get(2)));
            auctionWinner.setDlocation(as.get(3));
            auctionWinner.setPlocation(ap.get(2));
            auctionWinner.setPdestination(ap.get(3));
            auctionWinner.setUserP(ppairs1.getKey().toString());
            auctionWinner.setBidP(Integer.parseInt(ap.get(4)));
            auctionWinner.setPrice(Float.parseFloat(String.valueOf(price)));
            auctionWinner.setUserP(ppairs1.getKey().toString());
            ObjectifyService.ofy().save().entity(auctionWinner);
            tokenarray[0] = as.get(4);
            tokenarray[1] = ap.get(5);
            System.out.println(tokenarray[0]);
            System.out.println(tokenarray[1]);
            winner.put("name",as.get(1));
            winner.put("id",as.get(0));
            winner.put("dl",as.get(3));
            winner.put("pl",ap.get(2));
            winner.put("pd",ap.get(3));
            winner.put("price",String.valueOf(price));
            //String dname = dpairs.getKey().toString();
            winner.put("dbid",dpairs1.getKey().toString());
            winner.put("pbid",ppairs1.getKey().toString());
            auctiondataarr.put(winner);
            auctiondata.put("details", auctiondataarr);
            // }

            sendmessage(tokenarray);
            System.out.println("done");
        }   }

        //ofy().save().entity(auctionWinner);
        //System.out.println("token array "+Arrays.toString(tokenarray));
        //winner.put("message","Auction Finished");
       // auctiondataarr.put(winner);
       // System.out.println(winner.toString());
       // auctiondata.put("details",auctiondataarr);
       // System.out.println(auctiondata.toString());
           /* try {
               // createJson();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
//        for (Map.Entry<String, Integer> entry : sortedDMap.entrySet()) {
//        System.out.println("key===> "+entry.getKey()+"value ===> "+entry.getValue());
//       }
//
//        for (Map.Entry<String, Integer> entry : sortedPMap.entrySet()) {
//        System.out.println("key===> "+entry.getKey()+"value ===> "+entry.getValue());
//        }
    //Iterable<RegistrationRecord> regisID = ofyService.ofy().load().type(RegistrationRecord.class).filter("userID =", entry1.getKey());

    private Map<String, Integer> sortByDriver(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
   // return auctiondata;

   // @ApiMethod(name = "sm")
    private void sendmessage(String[] tr) {
        System.out.println("send message");
       // System.out.println(tr[0]);
        //System.out.println(tr[1]);
        OutputStream out = null;
        try {
            jsonBody = new JSONObject();
            jsonBody.put(PARAM_TO, tr);
            jsonBody.put("data", auctiondata);
            //jsonBody.put("registration_ids", tokenarray);
            // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
            // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
        } catch (JSONException e) {

        }
        try {
            HttpURLConnection conn= (HttpURLConnection) new URL(GCM_SEND_ENDPOINT).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty(HEADER_AUTHORIZATION, "key=" + "AIzaSyBx4J3ZSO66uGfkEmXHuwnRNPJTo7t_YKM");
            conn.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            conn.setUseCaches(false);
            //conn.setFixedLengthStreamingMode(requestBody.getBytes().length);
            conn.setRequestMethod("POST");
            out = conn.getOutputStream();
            out.write(jsonBody.toString().getBytes());
            int responseCode = conn.getResponseCode();
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.print(responseCode);
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
            //  Log.i(LoggingService.LOG_TAG, "HTTP response. body: " + responseBody);


        System.out.println(" send messageend");
    }
    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    private  <K, V> void printMap(Map<K, V> map) {

    }

    @ApiMethod(name = "stopauction",clientIds = "9160635882-n7olfcc6h8m1f96780er2l8h5orkn0da.apps.googleusercontent.com")
    public void stopauction(@Named("auctionID") String auctionid) {
        try {
            calculatewinners(auctionid);
            System.out.print(auctiondata.toString());
            //createJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(GCM_SEND_ENDPOINT).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty(HEADER_AUTHORIZATION,"key="+"AIzaSyBx4J3ZSO66uGfkEmXHuwnRNPJTo7t_YKM");
            conn.setRequestProperty(HEADER_CONTENT_TYPE,CONTENT_TYPE_JSON);
            conn.setUseCaches(false);
            //conn.setFixedLengthStreamingMode(requestBody.getBytes().length);
            conn.setRequestMethod("POST");
            OutputStream out = null;
            jsonBody = new JSONObject();
            try {

                jsonBody.put(PARAM_TO,tokenarray);
                jsonBody.put("data",auctiondata);
                //jsonBody.put("registration_ids", tokenarray);
                // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
                // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
            }
            catch (JSONException e) {
                throw new IOException("Failed to build JSON body");
            }
            try {
                out = conn.getOutputStream();
                out.write(jsonBody.toString().getBytes());
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
            int  responseCode = conn.getResponseCode();
             System.out.print(responseCode);
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            //  Log.i(LoggingService.LOG_TAG, "HTTP response. body: " + responseBody);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterable<Auction> all = ofyService.ofy().load().type(Auction.class).filter("auctionID =", auctionid);
        for (Auction au : all){


        }
        /*ArrayList<Auction> all_list = new ArrayList<Auction>();
        Gson gson = new Gson();
        JSONObject jobj1 = new JSONObject();
        for(Auction au : all){
            //jobj1.put(au);
            all_list.add(au);
        }
        String jsonau = gson.toJson(all_list);
        Gson gsonNew  = new Gson();
        Type collectionType = new TypeToken<ArrayList<Auction>>(){}.getType();
        List<Auction> enums = gson.fromJson(jsonau, collectionType);*/

        return ;
    }

       private JSONObject createJson()throws IOException {
       // getRegistrationId();
       // getAuctionList();
       jsonBody = new JSONObject();
        try {

            jsonBody.put(PARAM_TO,tokenarray);
            jsonBody.put("data",auctiondata);
            //jsonBody.put("registration_ids", tokenarray);
            // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
            // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
        }
        catch (JSONException e) {
            throw new IOException("Failed to build JSON body");
        }
        return jsonBody;
    }
}


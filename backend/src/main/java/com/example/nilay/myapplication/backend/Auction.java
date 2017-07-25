package com.example.nilay.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by nilay on 12/28/2016.
 */
@Entity
public class Auction {
    @Id
    Long ID;
    @Index
    String auctionID;
    String auctionName;
    @Index
    String userID;
    @Index
    String type;
    @Index
    Integer bid;
    String  regisID;
    String dlocation;
    String plocation;
    String pdestination;
    long endtime;

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public Auction(String userID, Integer bid, String auctionName, String type, String regisID, String dlocation, String plocation, String pdestination) {
        this.auctionID = auctionID;
        this.bid = bid;
        this.userID = userID;
        this.auctionName = auctionName;
        this.type = type;
        this.regisID = regisID;
        this.dlocation = dlocation;
        this.plocation = plocation;
        this.pdestination = pdestination;
    }
    public Auction(){}
    public String getAuctionName() {
        return auctionName;
    }
    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getAuctionID() {
        return auctionID;
    }
    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getBid() {
        return bid;
    }
    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getRegisID() {return regisID;}

    public String getDlocation() {
        return dlocation;
    }

    public void setDlocation(String dlocation) {
        this.dlocation = dlocation;
    }

    public String getPlocation() {
        return plocation;
    }

    public void setPlocation(String plocation) {
        this.plocation = plocation;
    }

    public String getPdestination() {
        return pdestination;
    }

    public void setPdestination(String pdestination) {
        this.pdestination = pdestination;
    }

    public void setRegisID(String regisID) {this.regisID = regisID;}
}

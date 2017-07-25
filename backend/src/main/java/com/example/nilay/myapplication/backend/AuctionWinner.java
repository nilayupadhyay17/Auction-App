package com.example.nilay.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by nilay on 6/20/2017.
 */
@Entity
public class AuctionWinner {
    @Id
    Long ID;
    @Index
    String auctionID;
    String auctionName;
    @Index
    String userD;
    String userP;
    Integer bidD;
    Integer bidP;
    String dlocation;
    String plocation;
    String pdestination;
    float price;

    public String getDlocation() {
        return dlocation;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getUserD() {
        return userD;
    }

    public void setUserD(String userD) {
        this.userD = userD;
    }

    public String getUserP() {
        return userP;
    }

    public void setUserP(String userP) {
        this.userP = userP;
    }

    public Integer getBidD() {
        return bidD;
    }

    public void setBidD(Integer bidD) {
        this.bidD = bidD;
    }

    public Integer getBidP() {
        return bidP;
    }

    public void setBidP(Integer bidP) {
        this.bidP = bidP;
    }
}

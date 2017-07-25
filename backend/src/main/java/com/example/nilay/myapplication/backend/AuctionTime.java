package com.example.nilay.myapplication.backend;

import com.google.appengine.repackaged.com.google.api.client.util.DateTime;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by nilay on 2/10/2017.
 */
@Entity
public class AuctionTime {
    @Id
     Long Id;
    @Index
    long startTime;
    @Index
    long endTime;
    Date dateTime;
    Long totalTime;
    String auctionName;
    @Index
    int status ;

    public AuctionTime(){}

    public AuctionTime(Long Id, long startTime, long endTime, String auctionName, int status){
        this.Id = Id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.auctionName = auctionName;
        this.status = status;
    }
    public void update(int status){
     this.status = status;
    }
    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public long getEndTime() {return endTime;}

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuctionName() {return auctionName;}

    public void setAuctionName(String auctionName) {this.auctionName = auctionName;}
}

package com.example.nilay.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by nilay on 1/14/2017.
 */
@Entity
public class Bidding {
    @Id
    String id;
    Integer bid;
    public Bidding(){

    }
    public Bidding(String id, Integer bid) {
        this.bid = bid;
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }
}

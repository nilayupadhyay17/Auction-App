package app.com.upadhyay.nilay.myapplication.beans;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nilay on 3/11/2017.
 */

public class AuctionDetail extends RealmObject {
    String ID;
    Long startTime;
    Long endTime;
    String name;
    String elapsed;
    //Date date;
    public AuctionDetail(String id, Long startTime,Long endTime,String name){
        this.ID = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }
    public AuctionDetail(){}
    public String getId() {
        return ID;
    }
    public void setId(String id) {
        this.ID = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
    /*    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/
}

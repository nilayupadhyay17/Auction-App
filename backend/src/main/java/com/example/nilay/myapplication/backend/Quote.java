package com.example.nilay.myapplication.backend;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Created by nilay on 11/6/2016.
 */
@Entity
public class Quote {
    @Id
    Long id;
    String who;
    String whom;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWho() {
        return who;
    }
    public void setWho(String who) {
        this.who = who;
    }
    public String getWhom() {
        return whom;
    }
    public void setWhom(String whom) {
        this.whom = whom;
    }
}

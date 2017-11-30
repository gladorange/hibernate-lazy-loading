package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Grape {

    @GeneratedValue
    @Id
    Long id;

    @ManyToOne
    Grapevine grapevine;


    @Column
    String st;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grapevine getGrapevine() {
        return grapevine;
    }

    public void setGrapevine(Grapevine grapevine) {
        this.grapevine = grapevine;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}

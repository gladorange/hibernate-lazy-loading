package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Grapevine {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    private User user;

    @OneToMany(cascade = { CascadeType.MERGE,CascadeType.PERSIST})
    @Fetch(FetchMode.SUBSELECT)
    private List<Grape> grapes = new ArrayList<>();

    @Column
    private String st;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public List<Grape> getGrapes() {
        return grapes;
    }

    public void setGrapes(List<Grape> grapes) {
        this.grapes = grapes;
    }
}

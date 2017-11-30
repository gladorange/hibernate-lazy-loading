package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String st;

    @OneToMany(mappedBy = "user",cascade = { CascadeType.PERSIST,CascadeType.MERGE})
    Set<Orange> oranges = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    Set<Apple> apples = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    Set<Peach> peaches = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    Set<Grapevine> grapevines = new HashSet<>();

    public Set<Apple> getApples() {
        return apples;
    }

    public void setApples(Set<Apple> apples) {
        this.apples = apples;
    }

    public Set<Peach> getPeaches() {
        return peaches;
    }

    public void setPeaches(Set<Peach> peaches) {
        this.peaches = peaches;
    }

    public Set<Grapevine> getGrapevines() {
        return grapevines;
    }

    public void setGrapevines(Set<Grapevine> grapevines) {
        this.grapevines = grapevines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Orange> getOranges() {
        return oranges;
    }

    public void setOranges(Set<Orange> oranges) {
        this.oranges = oranges;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}

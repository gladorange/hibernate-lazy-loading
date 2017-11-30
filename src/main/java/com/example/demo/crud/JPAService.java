package com.example.demo.crud;

import static com.example.demo.DemoApplication.m;
import static com.example.demo.DemoApplication.readAllEntities;

import java.util.HashMap;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.User;

@Transactional
@Service
public class JPAService {


    @PersistenceContext
    EntityManager em;

    public void tryManualLoading() {
        System.out.println("TRYING TO FETCH USER WITH Manual loading");
        long before = m();
        User user = em.find(User.class, 1L);
        em.createQuery("SELECT a from Apple a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Orange a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Grapevine a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Peach a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        readAllEntities(user);
        System.out.println("Manual loading took" + (m() - before));
    }

    public void tryLazyLoading() {
        System.out.println("TRYING TO FETCH USER WITH lazy loading");
        long before = m();
        User user = em.find(User.class, 1L);

        readAllEntities(user);
        System.out.println("Fully initialized with lazy loading queries took" + (before - m()));
    }

    public void tryFetchGraph() {
        System.out.println("TRYING TO FETCH USER WITH entity graph");
        long before = m();
        EntityGraph<User> entityGraph = em.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("oranges", "apples", "peaches", "grapevines");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        User user = em.find(User.class, 1L, properties);
        readAllEntities(user);
        System.out.println("Fully initialized with lazy loading queries took" + (before - m()));
    }

    public void tryJoinFetches() {
        System.out.println("TRYING TO FETCH USER WITH FETCH JOINS");
        long before = m();
        User fullyInitialized = (User) em.createQuery("SELECT u from User u LEFT JOIN fetch u.apples LEFT JOIN fetch u"
                + ".oranges LEFT JOIN fetch u.grapevines LEFT JOIN fetch u.oranges " + " where u.id=1").getSingleResult();
        readAllEntities(fullyInitialized);
        System.out.println("Fully initialized with join fetches took" + (before - m()));

    }

    public void merge(User u) {
        em.merge(u);
    }
}

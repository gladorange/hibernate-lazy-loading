package com.example.demo.crud;

import static com.example.demo.DemoApplication.m;
import static com.example.demo.DemoApplication.readAllEntities;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.User;

@Transactional
@Service
public class ManualLoading {


    @PersistenceContext
    EntityManager em;

    public void tryManualLoading() {
        System.out.println("TRYING TO FETCH USER WITH Manual loading");
        long before = m();
        User user = em.find(User.class, 1L);
        em.createQuery("SELECT a from Apple a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Orange a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Grape a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        em.createQuery("SELECT a from Peach a JOIN FETCH a.user where a.user.id=1 ").getResultList();
        readAllEntities(user);
        System.out.println("Manual loading took" + (m() - before));

    }

}

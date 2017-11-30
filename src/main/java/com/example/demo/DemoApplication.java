package com.example.demo;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.crud.ManualLoading;
import com.example.demo.crud.UserCrud;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	UserCrud parentDao;
	@Autowired
	ManualLoading manualLoading;

	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void doSomething() {

		LogManager.getLogger("org.hibernate.SQL").setLevel(Level.DEBUG);

		User u = new User();

		for (int i = 0; i < 30; i++) {
			Apple e = new Apple();
			e.setSt("Apple #"+i);
			u.getApples().add(e);
			e.setUser(u);

			Peach p = new Peach();
			p.setSt("add #"+i);
			u.getPeaches().add(p);
			p.setUser(u);

			Grape g = new Grape();
			g.setSt("Grape #"+i);
			u.getGrapes().add(g);
			g.setUser(u);

			Orange o = new Orange();
			o.setSt("Orange #"+i);
			u.getOranges().add(o);
			o.setUser(u);

		}

		parentDao.save(u);

		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger("org.hibernate.SQL");
		logger.setLevel(ch.qos.logback.classic.Level.DEBUG);

		tryJoinFetches();
		tryLazyLoading();
		tryFetchGraph();
		manualLoading.tryManualLoading();


	}



	private void tryFetchGraph() {
		System.out.println("TRYING TO FETCH USER WITH entity graph");
		long before = m();
		EntityGraph<User> entityGraph = em.createEntityGraph(User.class);
		entityGraph.addAttributeNodes("oranges", "apples", "peaches", "grapes");
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		User user = em.find(User.class, 1L, properties);
		readAllEntities(user);
		System.out.println("Fully initialized with lazy loading queries took" + (before - m()));
	}

	private void tryLazyLoading() {
		System.out.println("TRYING TO FETCH USER WITH lazy loading");
		long before = m();
		User user = em.find(User.class, 1L);

		readAllEntities(user);
		System.out.println("Fully initialized with lazy loading queries took" + (before - m()));
	}

	private void tryJoinFetches() {
		System.out.println("TRYING TO FETCH USER WITH FETCH JOINS");
		long before = m();
		User fullyInitialized = (User) em.createQuery("SELECT u from User u LEFT JOIN fetch u.apples LEFT JOIN fetch u"
				+ ".oranges LEFT JOIN fetch u.grapes LEFT JOIN fetch u.oranges " + " where u.id=1").getSingleResult();
		readAllEntities(fullyInitialized);
		System.out.println("Fully initialized with join fetches took" + (before - m()));

	}

	public static void readAllEntities(User user) {
		for (com.example.demo.Orange Orange : user.getOranges()) {
			Orange.getSt();
			Orange.getUser().getSt();
		}

		for (Apple apple : user.getApples()) {
			apple.getSt();
			apple.getUser().getSt();

		}

		for (com.example.demo.Grape Grape : user.getGrapes()) {
			Grape.getSt();
			Grape.getUser().getSt();

		}

		for (com.example.demo.Peach Peach : user.getPeaches()) {
			Peach.getSt();
			Peach.getUser().getSt();

		}
	}

	public static long m() {
		return System.currentTimeMillis();
	}


	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

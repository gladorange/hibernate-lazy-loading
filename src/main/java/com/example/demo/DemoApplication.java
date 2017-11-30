package com.example.demo;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.crud.JPAService;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	JPAService jpaService;

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

			Grapevine g = new Grapevine();

			for (int j = 0; j < 10; j++) {
				Grape grape = new Grape();
				grape.setSt("Grape in grapevine" + i + " #" + j);
				grape.setGrapevine(g);
				g.getGrapes().add(grape);
			}

			g.setSt("Grapevine #"+i);
			u.getGrapevines().add(g);
			g.setUser(u);

			Orange o = new Orange();
			o.setSt("Orange #"+i);
			u.getOranges().add(o);
			o.setUser(u);

		}

		jpaService.merge(u);

		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger("org.hibernate.SQL");
		logger.setLevel(ch.qos.logback.classic.Level.DEBUG);

		jpaService.tryJoinFetches();
		jpaService.tryLazyLoading();
		jpaService.tryFetchGraph();
		jpaService.tryManualLoading();


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

		for (Grapevine Grapevine : user.getGrapevines()) {
			Grapevine.getSt();
			Grapevine.getUser().getSt();

			for (Grape grape : Grapevine.getGrapes()) {
				grape.getSt();
			}
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

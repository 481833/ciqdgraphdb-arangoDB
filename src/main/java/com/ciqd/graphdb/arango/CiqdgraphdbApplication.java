package com.ciqd.graphdb.arango;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CiqdgraphdbApplication {
	public static void main(final String... args) {
		final Class<?>[] runner = new Class<?>[] { CrudRunner.class};
		System.exit(SpringApplication.exit(SpringApplication.run(runner,args)));
	}
}

package com.ciqd.graphdb.arango;


import com.arangodb.springframework.annotation.EnableArangoRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CiqdgraphdbApplication {
	public static void main(final String... args) {
		final Class<?>[] runner = new Class<?>[] { CrudRunner.class};
		//System.exit(SpringApplication.exit(SpringApplication.run(runner,args)));
		//SpringApplication.run(CiqdgraphdbApplication.class, args);
		SpringApplication.run(runner,args);
	}
}

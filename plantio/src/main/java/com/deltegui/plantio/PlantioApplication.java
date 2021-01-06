package com.deltegui.plantio;

import com.deltegui.plantio.store.implementation.StoreInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlantioApplication {
	public static void main(String[] args) {
		var ctx = SpringApplication.run(PlantioApplication.class, args);
		StoreInitializer.fromContext(ctx).ensureThereAreItemsInStore();
	}
}

package com.tricol.supplier_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplierOrderApplication {

	public static void main(String[] args) {
		String postgresUri = "jdbc:postgresql://ep-fancy-bonus-ahln33hw-pooler.c-3.us-east-1.aws.neon.tech:5432/tricol?sslmode=require&channel_binding=require";
		System.setProperty("spring.datasource.url", postgresUri);
		System.setProperty("spring.datasource.username", "neondb_owner");
		System.setProperty("spring.datasource.password", "npg_v9BVOLc3dKnR");

		SpringApplication.run(SupplierOrderApplication.class, args);
	}

}

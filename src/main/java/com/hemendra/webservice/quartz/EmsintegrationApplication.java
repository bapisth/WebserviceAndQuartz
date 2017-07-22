package com.hemendra.webservice.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class EmsintegrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmsintegrationApplication.class);
	}
}

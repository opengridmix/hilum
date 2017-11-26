package com.hilum.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.hilum"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

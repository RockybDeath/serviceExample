package main;

import beans.DataConfiguration;
import controllers.Demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = Demo.class)
public class Service {
    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }
}

package pl.edu.pg.eti.kask.rpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class. Spring Boot will scan all classes starting from the package containing this
 * class. It is important that all other classes will be in this or one of the nested packages.
 */
@SpringBootApplication
public class SimpleRpgAuthApplication {

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SimpleRpgAuthApplication.class, args);
    }

}

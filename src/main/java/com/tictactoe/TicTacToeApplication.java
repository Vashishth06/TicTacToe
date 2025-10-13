package com.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TicTacToeApplication - Main Spring Boot application entry point
 *
 * @SpringBootApplication enables:
 * - Component scanning for @Service, @Controller, etc.
 * - Auto-configuration of Spring Boot features
 * - Configuration properties support
 *
 * Starts embedded Tomcat server and initializes Spring context.
 * All components under com.tictactoe package are automatically discovered.
 */
@SpringBootApplication
public class TicTacToeApplication {

    /**
     * Main method - Application entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }
}
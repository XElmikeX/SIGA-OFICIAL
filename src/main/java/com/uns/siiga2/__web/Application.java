package com.uns.siiga2.__web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) { 
        try {
            SpringApplication.run(Application.class, args);
            System.out.println("---CORRIENDO CORRECTAMENTE---");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}



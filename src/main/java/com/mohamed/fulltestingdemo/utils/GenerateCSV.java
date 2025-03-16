package com.mohamed.fulltestingdemo.utils;


import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class GenerateCSV {
    // ! the command for generating the fake data => mvn compile exec:java -Dexec.mainClass="com.yourapp.until the GenerateCSV"
    public static void main(String[] args){
        Faker faker = new Faker();
        int numberOfUsers = 1000;

        try (FileWriter writer = new FileWriter("users.csv")) {
            writer.append("name,email\n");
            for (int i = 1; i <= numberOfUsers; i++) {
                String name = faker.name().fullName();
                String email = faker.internet().emailAddress();
                writer.append(name).append(",").append(email).append("\n");
            }
            log.info("✅ users.csv generated successfully with {} users!", numberOfUsers);
        } catch (IOException e) {
            log.info("❌ Error generating CSV: {}", e.getMessage());
        }
    }
}

package ru.clevertec.tests;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class.getName());

    private static final String FILENAME = "Log.log";

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.printA();
        System.out.println(testClass.getInt());



//        logger.info("Just a log message.");
//        logger.debug("af98das90f7dsa97f");
//        logger.error("asdf98w4urioafah");

        try {
            Files.readAllBytes(Paths.get(FILENAME));
        } catch (IOException ioex) {
            logger.error("Failed to read file {}.", FILENAME);
        }

    }

}

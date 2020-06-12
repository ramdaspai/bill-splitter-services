package com.app.billsplitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude= { DataSourceAutoConfiguration.class })
public class BillSplitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillSplitterApplication.class, args);
    }
}
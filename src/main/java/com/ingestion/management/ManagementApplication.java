package com.ingestion.management;

import com.ingestion.management.cron.CronNews;
import com.ingestion.management.repository.NewsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.mockito.Mockito.mock;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class ManagementApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}

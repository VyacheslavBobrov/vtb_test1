package ru.bobrov.vyacheslav.test2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bobrov.vyacheslav.test2.services.PrintService;

import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class Test1Application implements CommandLineRunner {
    private final PrintService printService;

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }

    @Override
    public void run(String... args) {
        Arrays.stream(args).forEach(printService::print);
    }
}

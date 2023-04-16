package ru.bobrov.vyacheslav.test2.services;

import org.springframework.stereotype.Component;
import ru.bobrov.vyacheslav.test2.annotations.Times;

import static java.lang.System.out;

@Component
public class PrintServiceImpl implements PrintService {
    @Override
    @Times(count = 3)
    public void print(String name) {
        out.println(name);
    }
}

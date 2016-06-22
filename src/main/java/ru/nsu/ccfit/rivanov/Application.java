package ru.nsu.ccfit.rivanov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.jpa.NodeRepository;
import ru.nsu.ccfit.rivanov.logic.LoadController;

import java.util.List;

@SpringBootApplication
public class Application {
    @Autowired
    private LoadController loadController;


//    @Bean
//    CommandLineRunner init() {
//        return args -> {
//            loadController.loadToDatabase();
//        };
//    }

    @Bean
    CommandLineRunner init() {
        return args -> {

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

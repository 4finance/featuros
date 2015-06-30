package io.fourfinanceit.featuros.example;

import io.fourfinanceit.featuros.Featuros;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
@RestController
class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
        Featuros.notification();
    }

    @RequestMapping(method = GET)
    public ResponseEntity foo() {
        return ok("foo");
    }

}

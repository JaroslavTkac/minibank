package lt.jaroslav.minibank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MinibankApplication {

  static void main(String[] args) {
    SpringApplication.run(MinibankApplication.class, args);
  }

}

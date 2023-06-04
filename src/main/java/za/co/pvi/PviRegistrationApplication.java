package za.co.pvi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PviRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PviRegistrationApplication.class, args);
		
	}

}

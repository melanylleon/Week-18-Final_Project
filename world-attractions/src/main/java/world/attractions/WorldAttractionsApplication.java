package world.attractions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Tells JPA that this is a Spring Boot application. It enables auto-configuration and the component scan.
@SpringBootApplication
public class WorldAttractionsApplication {

	public static void main(String[] args) {
		//Starts Spring Boot
		SpringApplication.run(WorldAttractionsApplication.class, args);
	}

}
 
package allegro.agh.login_service;

import allegro.agh.login_service.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AutoDetailingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoDetailingApplication.class, args);
	}
}

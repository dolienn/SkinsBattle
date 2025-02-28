package pl.dolien.skinsbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkinsBattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkinsBattleApplication.class, args);
	}

}

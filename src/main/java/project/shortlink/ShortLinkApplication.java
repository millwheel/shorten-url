package project.shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.shortlink.timer.Scheduler;

@SpringBootApplication
public class ShortLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortLinkApplication.class, args);
		Scheduler scheduler = new Scheduler();
		scheduler.workTimer();
	}

}

package com.example.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

@SpringBootApplication
public class NotificationserviceApplication {
	@Bean public Consumer<String> nhannt() {
		System.out.println("Message Running");
		return message-> System.out.println("Message" + message);
	}
	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}

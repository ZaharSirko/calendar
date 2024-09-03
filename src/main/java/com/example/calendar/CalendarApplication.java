package com.example.calendar;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarApplication.class, args);
	}

	@PostConstruct
	public void openBrowser() {
		String url = "http://localhost:8080/calendar";

		List<String> commands = Arrays.asList(
				"rundll32 url.dll,FileProtocolHandler " + url,
				"open " + url, // macOS
				"xdg-open " + url // Linux
		);

		for (String command : commands) {
			try {
				Runtime.getRuntime().exec(command);
				break;
			} catch (IOException e) {
              e.printStackTrace();
			}
		}
	}

}

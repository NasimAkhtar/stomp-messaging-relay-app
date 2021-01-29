package com.example.messagingstompwebsocket;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
@RestController
public class GreetingController {

	private GreetingService greetingService;

	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		return new Greeting(
				HtmlUtils.htmlEscape(message.getName()) + " joined the chat",
				"Hello from "+ message.getName());
	}

	@MessageMapping("/message")
	@SendTo("/topic/greetings")
	public Greeting message(HelloMessage message) throws Exception {
		Greeting greeting = new Greeting(
				HtmlUtils.htmlEscape(message.getName()),
				HtmlUtils.htmlEscape(message.getMessage())
		);

		greetingService.store(greeting);
		return greeting;
	}

	@GetMapping(value = "/greetings", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGreetings() {
		return new Gson().toJson(greetingService.getGreetings());
	}

}

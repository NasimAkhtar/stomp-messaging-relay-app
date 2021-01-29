package com.example.messagingstompwebsocket;

public class Greeting {

	private  String name;
	private String content;

	public Greeting() {
	}

	public Greeting(String name, String content) {
		this.content = content;
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}
}

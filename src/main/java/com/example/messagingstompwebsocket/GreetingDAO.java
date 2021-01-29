package com.example.messagingstompwebsocket;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class GreetingDAO {

    private List<Greeting> greetings = new LinkedList<>();

    public void store(Greeting greeting) {
        greetings.add(greeting);
    }

    public List<Greeting> getGreetings() {
        return greetings;
    }
}

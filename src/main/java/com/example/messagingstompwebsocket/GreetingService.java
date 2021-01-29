package com.example.messagingstompwebsocket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {
    private GreetingDAO greetingDAO;

    public GreetingService(GreetingDAO greetingDAO) {
        this.greetingDAO = greetingDAO;
    }

    public void store(Greeting greeting) {
        greetingDAO.store(greeting);
    }

    public List<Greeting> getGreetings() {
        return greetingDAO.getGreetings();
    }
}

package ru.melonhell.cyberpunkserver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import ru.melonhell.cyberpunkserver.netty.TCPServer;

@RequiredArgsConstructor
@SpringBootApplication
public class CyberpunkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyberpunkServerApplication.class, args);
    }

    private final TCPServer tcpServer;

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return applicationReadyEvent -> tcpServer.start();
    }

}

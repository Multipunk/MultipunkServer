/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.melonhell.cyberpunkserver.netty;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import ru.melonhell.cyberpunkserver.netty.netty.TCPServer;

/**
 * Spring-Boot Java Configuration and Bootstrap
 *
 * @author Jibeom Jung akka. Manty
 */
@RequiredArgsConstructor
@SpringBootApplication
public class MantyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MantyChatApplication.class, args);
    }

    private final TCPServer tcpServer;

    /**
     * This can not be implemented with lambda, because of the spring framework limitation
     * (https://github.com/spring-projects/spring-framework/issues/18681)
     *
     * @return
     */
    @SuppressWarnings({"Convert2Lambda", "java:S1604"})
    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
                tcpServer.start();
            }
        };
    }
}
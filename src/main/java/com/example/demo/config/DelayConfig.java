package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayConfig {
    public static final String DEAD_LETTER_EXCHANGE = "DEAD_LETTER_EXCHANGE";
    public static final String DEAD_LETTER_QUEUE = "DEAD_LETTER_QUEUE";
    public static final String DEAD_LETTER_ROUTING = "DEAD_LETTER_ROUTING";
    public static final String DELAY_EXCHANGE = "DELAY_EXCHANGE";
    public static final String DELAY_QUEUE = "DELAY_QUEUE";
    public static final String DELAY_ROUTING = "DELAY_ROUTING";

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING);
    }

    @Bean
    public Queue delayQueue() {
//        return new Queue(DELAY_QUEUE);
        return QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING)
                .withArgument("x-message-ttl", 10000L)
                .build();
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING);
    }


}

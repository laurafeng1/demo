package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${rabbitmq.dead-letter-queue}")
    private String deadLetterQueue;
    @Value("${rabbitmq.dead-letter-exchange}")
    private String deadLetterExchange;
    @Value("${rabbitmq.dead-letter-routing}")
    private String deadLetterRouting;

    @Value("${rabbitmq.delay-queue}")
    private String delayQueue;
    @Value("${rabbitmq.delay-exchange}")
    private String delayExchange;
    @Value("${rabbitmq.delay-routing}")
    private String delayRouting;


    @Bean
    public Queue deadLetterQueue() {
        return new Queue(deadLetterQueue);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterRouting);
    }

    @Bean
    public Queue delayQueue() {
//        return new Queue(DELAY_QUEUE);
        return QueueBuilder.durable(delayQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRouting)
                .withArgument("x-message-ttl", 10000L)
                .build();
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(delayExchange);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delayRouting);
    }


}

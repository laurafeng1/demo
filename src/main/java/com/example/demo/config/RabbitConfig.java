package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue-name1}")
    private String queueName1;
    @Value("${rabbitmq.exchange-name1}")
    private String exchangeName1;
    @Value("${rabbitmq.routing-name1}")
    private String routingName1;


    @Bean
    public Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange1() {
        return new DirectExchange(exchangeName1);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(exchange1()).with(routingName1);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${rabbitmq.queue-name2}")
    private String queueName2;
    @Value("${rabbitmq.exchange-name2}")
    private String exchangeName2;
    @Value("${rabbitmq.routing-name2}")
    private String routingName2;


    @Bean
    public Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange2() {
        return new DirectExchange(exchangeName2);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(exchange2()).with(routingName2);
    }

    @Value("${rabbitmq.queue-name3}")
    private String queueName3;
    @Value("${rabbitmq.exchange-name3}")
    private String exchangeName3;
    @Value("${rabbitmq.routing-name3}")
    private String routingName3;


    @Bean
    public Queue queue3() {
        return new Queue(queueName3, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange3() {
        return new DirectExchange(exchangeName3);
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(queue3()).to(exchange3()).with(routingName3);
    }

    @Value("${rabbitmq.queue-name4}")
    private String queueName4;
    @Value("${rabbitmq.exchange-name4}")
    private String exchangeName4;
    @Value("${rabbitmq.routing-name4}")
    private String routingName4;


    @Bean
    public Queue queue4() {
        return new Queue(queueName4, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange4() {
        return new DirectExchange(exchangeName4);
    }

    @Bean
    public Binding binding4() {
        return BindingBuilder.bind(queue4()).to(exchange4()).with(routingName4);
    }

    @Value("${rabbitmq.queue-name6}")
    private String queueName6;
    @Value("${rabbitmq.exchange-name6}")
    private String exchangeName6;
    @Value("${rabbitmq.routing-name6}")
    private String routingName6;


    @Bean
    public Queue queue6() {
        return new Queue(queueName6, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange6() {
        return new DirectExchange(exchangeName6);
    }

    @Bean
    public Binding binding6() {
        return BindingBuilder.bind(queue6()).to(exchange6()).with(routingName6);
    }

    @Value("${rabbitmq.queue-name7}")
    private String queueName7;
    @Value("${rabbitmq.exchange-name7}")
    private String exchangeName7;
    @Value("${rabbitmq.routing-name7}")
    private String routingName7;


    @Bean
    public Queue queue7() {
        return new Queue(queueName7, false);
    }

    @Bean
    //    Messages are not published directly to a queue; instead, the producer sends messages to an exchange.
    //    An exchange is responsible for routing the messages to different queues with the help of bindings and routing keys.
    //    A binding is a link between a queue and an exchange.
    public DirectExchange exchange7() {
        return new DirectExchange(exchangeName7);
    }

    @Bean
    public Binding binding7() {
        return BindingBuilder.bind(queue7()).to(exchange7()).with(routingName7);
    }
}

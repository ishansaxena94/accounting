package com.modularbank.accounting.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TxnMQConfig {

	public static final String TXN_QUEUE = "txn_ops";
	public static final String TXN_EXCHANGE = "txn_exchange";
	public static final String TXN_ROUTING_KEY = "txn_routing_key";

	@Bean
	public Queue txnQueue() {
		return new Queue(TXN_QUEUE);
	}

	@Bean
	public TopicExchange txnTopicExchange() {
		return new TopicExchange(TXN_EXCHANGE);
	}

	@Bean
	public Binding txnBinding(Queue queue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(TXN_ROUTING_KEY);
	}

	@Bean
	public MessageConverter txnMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate txnTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(txnMessageConverter());
		return rabbitTemplate;
	}
}

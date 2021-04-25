package com.modularbank.accounting.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modularbank.accounting.configurations.MQConfig;
import com.modularbank.accounting.pubsub.AccountMessage;

@RestController
public class ProducerController {

	@Autowired
	RabbitTemplate rabbitTemplate;
	static int i = 0;

	@GetMapping("/publish")
	public void publish() {
		String greeting = "Hello, Ishan: " + i + "!";
		i++;

		AccountMessage msg = new AccountMessage();
		msg.setMessageId(UUID.randomUUID().toString());
		// msg.setMessage(greeting);
		msg.setMessageDate(new Date());

		System.out.println("Publishing: " + greeting);
		rabbitTemplate.convertAndSend(MQConfig.ACC_EXCHANGE, MQConfig.ACC_ROUTING_KEY, msg);
		System.out.println("Published: " + greeting);
	}

}

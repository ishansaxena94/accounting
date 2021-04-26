package com.modularbank.accounting.pubsub;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.modularbank.accounting.configurations.MQConfig;
import com.modularbank.accounting.model.Account;

@Component
public class AccountPublisher {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void publish(Account account, String operationType) {

		AccountMessage msg = new AccountMessage();
		msg.setMessageId(UUID.randomUUID().toString());
		msg.setMessageDate(new Date());
		msg.setOperationType(operationType);
		msg.setAccount(account);

		System.out.println("Publishing: " + account);
		rabbitTemplate.convertAndSend(MQConfig.ACC_EXCHANGE, MQConfig.ACC_ROUTING_KEY, msg);
		System.out.println("Published: " + account);
	}
}

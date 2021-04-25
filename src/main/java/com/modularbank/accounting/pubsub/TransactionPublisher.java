package com.modularbank.accounting.pubsub;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.modularbank.accounting.configurations.TxnMQConfig;
import com.modularbank.accounting.model.Transaction;

@Component
public class TransactionPublisher {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void publish(Transaction transaction, String operationType) {

		TransactionMessage msg = new TransactionMessage();
		msg.setMessageId(UUID.randomUUID().toString());
		msg.setMessageDate(new Date());
		msg.setOperationType(operationType);
		msg.setTransaction(transaction);

		System.out.println("Publishing: " + transaction);
		rabbitTemplate.convertAndSend(TxnMQConfig.TXN_EXCHANGE, TxnMQConfig.TXN_ROUTING_KEY, msg);
		System.out.println("Published : " + transaction);
	}
}

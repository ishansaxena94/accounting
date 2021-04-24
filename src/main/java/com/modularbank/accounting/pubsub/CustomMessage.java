package com.modularbank.accounting.pubsub;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {

	private String messageId;
	private String message;
	private Date messageDate;
}

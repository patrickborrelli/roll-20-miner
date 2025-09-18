package com.patrickborrelli.roll20miner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Message {

	private String avatarUrl;
	private String timestamp;
	private String author;	
	private int messageIndex;
	
	public Message(String avatarUrl, String timestamp, String author, int messageIndex) {
		this.avatarUrl = avatarUrl;
		this.timestamp = timestamp;
		this.author = author;
		this.messageIndex = messageIndex;
	}
	
	public abstract String toDisplayString();
}

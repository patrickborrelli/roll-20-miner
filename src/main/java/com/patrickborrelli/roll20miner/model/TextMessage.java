package com.patrickborrelli.roll20miner.model;

import org.jsoup.nodes.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextMessage extends Message {

	private String messageContent;
	
	public TextMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element message) {
		super(avatarUrl, timestamp, author, messageIndex); 
		
		messageContent = message.ownText();
		
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
}

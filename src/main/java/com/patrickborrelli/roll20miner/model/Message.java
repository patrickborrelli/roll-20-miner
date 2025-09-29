package com.patrickborrelli.roll20miner.model;

import com.patrickborrelli.roll20miner.util.MinerUtil;

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
	
	public String toCsvString() {
		StringBuilder builder = new StringBuilder();
		builder.append(csvEscape(getTimestamp()));
		builder.append(MinerUtil.COMMA);
		builder.append(csvEscape(getAuthor()));
		builder.append(MinerUtil.COMMA);		
		return builder.toString();
	}
	
	protected String csvEscape(String toEscape) {
		if(toEscape == null || toEscape.isEmpty()) {
	      return toEscape;
	    }

	    StringBuilder builder = new StringBuilder();

	    builder.append(MinerUtil.QUOTE);
	    builder.append(toEscape.replaceAll(MinerUtil.QUOTE, MinerUtil.DOUBLE_QUOTE));
	    builder.append(MinerUtil.QUOTE);

	    return builder.toString();
	}
}

package com.patrickborrelli.roll20miner.model;

import java.util.List;

import org.jsoup.nodes.Element;

import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PrivateMessage extends Message {
	
	private String deliveredTo;
	private String messageText;
	
	public PrivateMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element message) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		List<Element> privates = message.getElementsByClass(MinerUtil.PRIVATE_MSG);
		
		for(Element privateMsg : privates) {			
			Element sentTo = privateMsg.getElementsByClass(MinerUtil.SENDER).first();
			deliveredTo = sentTo.ownText();
			
			messageText = privateMsg.ownText();
		}
		
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
}

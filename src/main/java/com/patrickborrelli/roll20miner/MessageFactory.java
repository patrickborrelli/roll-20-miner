package com.patrickborrelli.roll20miner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.model.TextMessage;
import com.patrickborrelli.roll20miner.util.MinerUtil;

public class MessageFactory {	
	private static final Logger LOGGER = LogManager.getLogger(MessageFactory.class);
	
	public Message createMessageObject(Elements elements) {
		Message message = null;
		
		
		return message;
	}

	/**
	private Message createTextMessage(Element element) {
		Message message = new TextMessage();
		populateGenericMessage(message, element);
		//String text = retrieveMessageText(element);
		
		if(text != null && !text.isEmpty()) {
			((TextMessage)message).setMessageContent(text);
		} else {
			LOGGER.warn("Current message does not have associated text.");
		}
		
		return message;
	}

	private Message createAttackMessage(Element element) {
		Message message = new TextMessage();
		populateGenericMessage(message, element);
		
		return message;
	}
	
	private void populateGenericMessage(Message message, Element element) {
		String timestamp;
		String sender;
		String avatarUrl;
		
		timestamp = retrieveTimestamp(element);
		if(timestamp != null && !timestamp.isEmpty()) {
			message.setTimestamp(timestamp);
		} else {
			LOGGER.warn("Current message does not have a recorded timestamp.");
		}
		
		sender = retrieveSender(element);
		if(sender != null && !sender.isEmpty()) {
			message.setBy(sender);
		} else {
			LOGGER.warn("Current message does not have a recorded sender.");
		}
		
		avatarUrl = retrieveAvatar(element);
		if(avatarUrl != null && !avatarUrl.isEmpty()) {
			message.setAvatarUrl(avatarUrl);
		} else {
			LOGGER.warn("Current message does not have an associated avatar.");
		}		
	}
		
	private String retrieveTimestamp(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(MinerUtil.TIMESTAMP);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveSender(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(MinerUtil.SENDER);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveAvatar(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(MinerUtil.AVATAR_URL);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 	
			if(first.childNodeSize() > 0) {
				LOGGER.debug(first.child(0).attr("src"));
				result = first.child(0).attr("src");
			}			
		}		
		return result;
	}
	**/
}

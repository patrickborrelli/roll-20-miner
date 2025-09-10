package com.patrickborrelli.roll20miner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.model.TextMessage;

public class MessageFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(MessageFactory.class);
	
	private static final String TIMESTAMP_CLASS = "tstamp";
	private static final String SENDER_CLASS = "by";
	private static final String AVATAR_URL_CLASS = "avatar";
	private static final String ATTACK_CLASS = "sheet-atk";

	protected Message createMessageObject(Element element) {
		Message message = null;
		String timestamp = null;
		Elements elements = null;
		Element first = null;
		
		//create the appropriate concrete Message instance based on element contents:
		first = element.getElementsByClass("sheet-atk").first();
		
		if(first != null) {
			//we have an attack message
			message = createAttackMessage(element);
		} else {
			message = createTextMessage(element);
		}
		
		return message;
	}

	private Message createTextMessage(Element element) {
		Message message = new TextMessage();
		populateGenericMessage(message, element);
		String text = retrieveMessageText(element);
		
		if(text != null && !text.isEmpty()) {
			((TextMessage)message).setMessageContent(text);
		} else {
			LOGGER.warn("Current message does not have associated text.");
		}
		
		return message;
	}

	private Message createAttackMessage(Element element) {
		// TODO Auto-generated method stub
		return null;
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
		Elements elements = element.getElementsByClass(TIMESTAMP_CLASS);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveSender(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(SENDER_CLASS);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveAvatar(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(AVATAR_URL_CLASS);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 			
			LOGGER.debug(first.child(0).attr("src"));
			result = first.child(0).attr("src");
		}		
		return result;
	}
	
	private String retrieveMessageText(Element element) {
		String result = null;
		Elements elements = element.getElementsByClass(SENDER_CLASS);
		Element first = elements.first();
		
		if(!elements.isEmpty() && first != null) { 
			Element parent = first.parent();
			result = parent.childNode(first.siblingIndex() +1 ).toString();
			LOGGER.debug(result);
		}		
		return result;
	}
}

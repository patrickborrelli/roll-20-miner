package com.patrickborrelli.roll20miner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import com.patrickborrelli.roll20miner.model.AttackMessage;
import com.patrickborrelli.roll20miner.model.DamageMessage;
import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.util.MinerUtil;

public class MessageFactory {	
	private static final Logger LOGGER = LogManager.getLogger(MessageFactory.class);
	private static int messageIndex = 0;
	
	public Message createMessageObject(Element element) {
		Message message = null;
		String avatarUrl = null;
		String timestamp = null;
		String sender = null;
		
		//first attempt to extract common elements:
		timestamp = retrieveTimestamp(element);
		avatarUrl = retrieveAvatar(element);
		sender = retrieveSender(element);
		
		//TODO: build avatarLookup map/utilize if values are missing
		
		//determine the type of message for proper factory creation:
		if(element.getElementsByClass(MinerUtil.ATTACK).first() != null) {
			message = new AttackMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		} else if(element.getElementsByClass(MinerUtil.ATTACK_DMG).first() != null) {
			message = new DamageMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		}
		
		
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
		**/
		
	private String retrieveTimestamp(Element element) {
		String result = null;
		Element first = element.getElementsByClass(MinerUtil.TIMESTAMP).first();
		
		if(first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveSender(Element element) {
		String result = null;
		Element first = element.getElementsByClass(MinerUtil.SENDER).first();
		
		if(first != null) { 
			LOGGER.debug(first.text());
			result = first.text();
		}		
		return result;
	}
	
	private String retrieveAvatar(Element element) {
		String result = null;
		Element first = element.getElementsByClass(MinerUtil.AVATAR_URL).first();
		
		if(first != null) { 	
			if(first.childNodeSize() > 0) {
				LOGGER.debug(first.child(0).attr("src"));
				result = first.child(0).attr("src");
			}			
		}		
		return result;
	}

}

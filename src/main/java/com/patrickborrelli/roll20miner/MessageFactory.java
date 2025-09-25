package com.patrickborrelli.roll20miner;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import com.patrickborrelli.roll20miner.model.AttackMessage;
import com.patrickborrelli.roll20miner.model.DamageMessage;
import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.model.PrivateMessage;
import com.patrickborrelli.roll20miner.model.SimpleRollMessage;
import com.patrickborrelli.roll20miner.model.SpellAttackDamageMessage;
import com.patrickborrelli.roll20miner.model.TextMessage;
import com.patrickborrelli.roll20miner.model.TraitsMessage;
import com.patrickborrelli.roll20miner.util.MinerUtil;

public class MessageFactory {	
	private static final Logger LOGGER = LogManager.getLogger(MessageFactory.class);
	private static int messageIndex = 0;
	
	/**
	 * Responsible for mapping a name to an avatar url
	 * to facilitate cases where one or the other are not 
	 * included in the incoming element.
	 */
	private static Map<String, String> avatarUrlToNameMapping;	
	private static Map<String, String> nameToAvatarUrlMapping;
	
	public MessageFactory() {
		avatarUrlToNameMapping = new HashMap<>();
		nameToAvatarUrlMapping = new HashMap<>();
	}
	
	
	public Message createMessageObject(Element element) {
		Message message = null;
		String avatarUrl = null;
		String timestamp = null;
		String sender = null;
		
		//first attempt to extract common elements:
		timestamp = retrieveTimestamp(element);
		avatarUrl = retrieveAvatar(element);
		sender = retrieveSender(element);
		
		//add to mappings if we have the data:
		if(sender != null && !sender.isEmpty() && avatarUrl != null && !avatarUrl.isEmpty()) {
			avatarUrlToNameMapping.put(avatarUrl, sender);
			nameToAvatarUrlMapping.put(sender, avatarUrl);
		}
				
		//handle private message sender:
		if( (sender == null || sender.isEmpty() || sender.contains("To")) && 
			(avatarUrl != null && !avatarUrl.isEmpty())) {
			String realSender = avatarUrlToNameMapping.get(avatarUrl);
			if(realSender != null) {
				sender = realSender;
			}
		}
		
		
		
		//TODO: determine sender based on avatar lookup for private messages
		//TODO: build avatarLookup map/utilize if values are missing
		
		//determine the type of message for proper factory creation:
		if(element.getElementsByClass(MinerUtil.ATTACK).first() != null) {
			message = new AttackMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		} else if(element.getElementsByClass(MinerUtil.ATTACK_DMG).first() != null) {
			if(element.getElementsByClass(MinerUtil.SPELLDESC).first() != null) {
				message = new SpellAttackDamageMessage(avatarUrl, timestamp, sender, messageIndex++, element);
			} else {
				message = new DamageMessage(avatarUrl, timestamp, sender, messageIndex++, element);
			}
		} else if(element.getElementsByClass(MinerUtil.SIMPLE_ROLL).first() != null) {
			message = new SimpleRollMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		} else if(element.getElementsByClass(MinerUtil.TRAIT).first() != null) {
			message = new TraitsMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		} else if(element.getElementsByClass(MinerUtil.PRIVATE_MSG).first() != null) {
			message = new PrivateMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		} else {
			//assume it is a regular old text message:
			message = new TextMessage(avatarUrl, timestamp, sender, messageIndex++, element);
		}		
		
		return message;
	}
		
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

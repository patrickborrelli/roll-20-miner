package com.patrickborrelli.roll20miner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;

/**
 * The LogMinerParser is responsible for the coordination of all of 
 * the further processing of a provided chat log file.
 *
 *@author patrick borrelli
 */
@Data
public class LogMinerParser {
	private static final Logger LOGGER = LogManager.getLogger(LogMinerParser.class);
	
	/**
	 * Responsible for mapping a name to an avatar url
	 * to facilitate cases where one or the other are not 
	 * included in the incoming element.
	 */
	private Map<String, String> avatarUrlMapping;
	
	/**
	 * Default constructor initializes any necessary private members.
	 */
	public LogMinerParser() {
		avatarUrlMapping = new HashMap<>();
	}

	/**
	 * Responsible for parsing of the collection of Elements 
	 * contained in the chat log file.
	 * 
	 * @param contents - an Elements collection containing all messages from the chat log.
	 * @return a List of created Messages.
	 */
	public List<Message> parseElements(Elements contents) {
		List<Message> result = new ArrayList<>();
		Elements parsedElements = new Elements();
		Elements workingElements = new Elements();
		
		for(Element currElement : contents) {
			boolean isTimestamp = currElement.getElementsByClass(MinerUtil.TIMESTAMP).first() != null;
			boolean isDesc = currElement.getElementsByClass(MinerUtil.DESCRIPTION).first() != null;
			boolean isEmote = currElement.getElementsByClass(MinerUtil.EMOTE).first() != null;

			//if the current element has no timestamp, it must be a part of a 
			//multi-element message or it is a description or emote message:
			if(!isTimestamp) { 
				if(isDesc || isEmote) {
					//these will only ever be single line messages, but neither has a timestamp,
					//so push them directly to the parsed messages after pushing the working elements:
					parsedElements.add(combineElements(workingElements));
					workingElements.clear();
					parsedElements.add(currElement);
				} else {
					workingElements.add(currElement); 
				}
			} else {
				//found a timestamp, so this is the beginning or entirety of a new message.
				//send any previous working elements collection for combining:
				parsedElements.add(combineElements(workingElements));
				workingElements.clear();
				workingElements.add(currElement);				
			}
		}			
		
		return result;
	}

	private Element combineElements(Elements workingElements) {
		Element result = null;
		if(workingElements != null && !workingElements.isEmpty()) {
			//if only one element, return it, otherwise combine:
			if(workingElements.size() < 2) {
				result = workingElements.first();
			} else {
				LOGGER.debug("To combine: \n\t {}", workingElements.toString());
			}
		}
		
		
		return result;
	}
}

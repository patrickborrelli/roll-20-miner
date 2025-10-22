package com.patrickborrelli.roll20miner.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends Message {

	private static final Logger LOGGER = LogManager.getLogger(TextMessage.class);

	private String messageContent;

	public TextMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element message) {
		super(avatarUrl, timestamp, author, messageIndex);

		if (message.getElementsByTag(MinerUtil.EMOTE_MESSAGE).first() != null) {
			messageContent = message.getElementsByTag(MinerUtil.EMOTE_MESSAGE).first().ownText();
		} else {
			if (message.ownText() != null && !message.ownText().isEmpty()) {
				messageContent = message.ownText();
			} else {
				LOGGER.warn("Text message has no own text");
			}
		}
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCsvString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toCsvString());
		builder.append(csvEscape(messageContent));
		return builder.toString();
	}
}

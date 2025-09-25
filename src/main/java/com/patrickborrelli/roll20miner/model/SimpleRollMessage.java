package com.patrickborrelli.roll20miner.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SimpleRollMessage extends Message {
	
	private List<String> rolls;
	private List<String> sheetLabels;
	
	public SimpleRollMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element simpleRoll) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		sheetLabels = new ArrayList<>();
		rolls = new ArrayList<>();
		List<TextNode> nodes = new ArrayList<>();		
		StringBuilder builder = null;
		
		List<Element> allRolls = simpleRoll.getElementsByClass(MinerUtil.SIMPLE_ROLL);
		
		for(Element roll : allRolls) {
			
			builder = new StringBuilder();
			
			Element aRoll = roll.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
			
			if(aRoll != null) {
				builder.append(aRoll.attr("title")).append(MinerUtil.EQ).append(aRoll.ownText());
				
				rolls.add(MinerUtil.cleanAttackText(builder.toString()));
			}
			
			builder = new StringBuilder();
			
			Element labelText = roll.getElementsByClass(MinerUtil.LABEL).first();
			nodes = labelText.children().first().textNodes();	
			for(TextNode node : nodes) {
				builder.append(node.text()).append(" ");
			}				 
			
			sheetLabels.add(builder.toString().trim());
		}
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

}

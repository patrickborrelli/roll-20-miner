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
public class AttackMessage extends Message {
	
	private List<String> attackRolls;
	private List<String> sheetLabels;
	
	public AttackMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element attack) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		attackRolls = new ArrayList<>();
		sheetLabels = new ArrayList<>();
		List<TextNode> nodes = new ArrayList<>();		
		StringBuilder builder = null;
		
		List<Element> attacks = attack.getElementsByClass(MinerUtil.ATTACK);
		
		for(Element line : attacks) {
			builder = new StringBuilder();
			nodes = line.getElementsByClass(MinerUtil.LABEL).first().children().first().children().textNodes();
			
			for(TextNode node : nodes) {
				builder.append(node.text()).append(" ");
			}
			
			sheetLabels.add(builder.toString().trim());
			
			builder = new StringBuilder();
			
			Element attackText = line.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
			builder.append(attackText.attr("title")).append(MinerUtil.EQ).append(attackText.ownText());
			
			attackRolls.add(MinerUtil.cleanAttackText(builder.toString()));
		}			
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCsvString() {
		// TODO Auto-generated method stub
		return null;
	}
}

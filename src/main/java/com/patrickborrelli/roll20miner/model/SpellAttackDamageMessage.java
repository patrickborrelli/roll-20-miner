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
public class SpellAttackDamageMessage extends Message {
	
	private List<String> saves;
	private List<String> attackRolls;
	private List<String> damageRolls;
	private List<String> sheetLabels;
	
	public SpellAttackDamageMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element damage) {
		super(avatarUrl, timestamp, author, messageIndex); 
		
		saves = new ArrayList<>();
		attackRolls = new ArrayList<>();
		sheetLabels = new ArrayList<>();
		damageRolls = new ArrayList<>();
		
		List<TextNode> nodes = new ArrayList<>();		
		StringBuilder builder = null;
		
		List<Element> damages = damage.getElementsByClass(MinerUtil.ATTACK_DMG);
		
		for(Element line : damages) {
			builder = new StringBuilder();
			
			Element sheetAttack = line.getElementsByClass(MinerUtil.SHEET_ATTACK).first();
			
			if(sheetAttack != null) {
				Element attackText = sheetAttack.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
				builder.append(attackText.attr("title")).append(MinerUtil.EQ).append(attackText.ownText());
				
				attackRolls.add(MinerUtil.cleanAttackText(builder.toString()));
				
				builder = new StringBuilder();
				
				Element labelText = sheetAttack.getElementsByClass(MinerUtil.LABEL).first();
				nodes = labelText.children().textNodes();	
				for(TextNode node : nodes) {
					builder.append(node.text()).append(" ");
				}			
				//builder.append(labelText.children().first().children().first().textNodes().get(0).text());
				
				sheetLabels.add(builder.toString().trim());
			}
			
			builder = new StringBuilder();
			
			Element sheetDamageBlock = line.getElementsByClass(MinerUtil.SHEET_DAMAGE_TEMPLATE).first();
			Element sheetDamage = sheetDamageBlock.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
			Element sheetSubLabel = sheetDamageBlock.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
			builder.append(sheetDamage.attr("title")).append(MinerUtil.EQ).append(sheetDamage.ownText()).append(MinerUtil.SPACE).append(sheetSubLabel.ownText());
			damageRolls.add(MinerUtil.cleanAttackText(builder.toString().trim()));			
		}
		
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

}

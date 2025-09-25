package com.patrickborrelli.roll20miner.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DamageMessage extends Message {
	
	private List<String> attackRolls;
	private List<String> damageRolls;
	private List<String> sheetLabels;
	private List<String> sheetSubDamages;
	
	public DamageMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element damage) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		attackRolls = new ArrayList<>();
		sheetLabels = new ArrayList<>();
		damageRolls = new ArrayList<>();
		sheetSubDamages = new ArrayList<>();
		List<TextNode> nodes = new ArrayList<>();		
		StringBuilder builder = null;
		
		List<Element> damages = damage.getElementsByClass(MinerUtil.ATTACK_DMG);
		
		for(Element line : damages) {
			//drop any empty blocks:
			if(line.childrenSize() != 0) {			
				builder = new StringBuilder();
				
				Element sheetAttack = line.getElementsByClass(MinerUtil.SHEET_ATTACK).first();
				
				if(sheetAttack != null) {
					Element attackText = sheetAttack.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
					builder.append(attackText.attr("title")).append(MinerUtil.EQ).append(attackText.ownText());
					
					attackRolls.add(MinerUtil.cleanAttackText(builder.toString()));
					
					builder = new StringBuilder();
					
					Element labelText = sheetAttack.getElementsByClass(MinerUtil.LABEL).first();
					nodes = labelText.children().first().textNodes();	
					for(TextNode node : nodes) {
						builder.append(node.text()).append(" ");
					}				 
					
					sheetLabels.add(builder.toString().trim());			
				}
				
				Elements sheetDescriptions = line.getElementsByClass(MinerUtil.SHEET_DESCRIPTION);
				for(Element desc : sheetDescriptions) {
					//ignore sheet-info tags:
					if(!desc.hasClass(MinerUtil.SHEET_INFO)) {
						builder = new StringBuilder();
						Element subDamageText = desc.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element subLabel = desc.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
						builder.append(subLabel.ownText()).append(MinerUtil.SPACE).append(subDamageText.attr("title")).append(MinerUtil.EQ).append(subDamageText.ownText());
						sheetSubDamages.add(builder.toString().trim());
					}
				}
				
				builder = new StringBuilder();
				
				Element sheetDamageBlock = line.getElementsByClass(MinerUtil.SHEET_DAMAGE_TEMPLATE).first();
				Element sheetDamage = sheetDamageBlock.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
				Element sheetSubLabel = sheetDamageBlock.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
				builder.append(sheetDamage.attr("title")).append(MinerUtil.EQ).append(sheetDamage.ownText()).append(MinerUtil.SPACE).append(sheetSubLabel.ownText());
				damageRolls.add(MinerUtil.cleanAttackText(builder.toString().trim()));			
			}
		}
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.patrickborrelli.roll20miner.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DamageMessage extends Message {
	
	private static final Logger LOGGER = LogManager.getLogger(DamageMessage.class);
	private List<String> messageContents;
	
	public DamageMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element damage) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		StringBuilder builder = null;
		List<Element> damages = damage.getElementsByClass(MinerUtil.ATTACK_DMG);
		messageContents = new ArrayList<>();	
		
		for(Element line : damages) {
			if(line.childrenSize() != 0) {
				builder = new StringBuilder();
				
				Element sheetAttack = line.getElementsByClass(MinerUtil.SHEET_ATTACK).first();
				Element sheetDmg = line.getElementsByClass(MinerUtil.SHEET_DAMAGE_TEMPLATE).first();
				Element sheetDescription = line.getElementsByClass(MinerUtil.SHEET_DESCRIPTION).first();
				
				if(sheetAttack != null) {
					if(sheetAttack.hasClass(MinerUtil.SHEET_SAVE)) {
						builder.append(sheetAttack.getElementsByClass(MinerUtil.LABEL).get(0).text());
						builder.append(MinerUtil.SPACE);
						builder.append(sheetAttack.getElementsByClass(MinerUtil.SHEET_SAVE_DC).get(0).text());
						builder.append(MinerUtil.SPACE);
						if(!sheetAttack.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).isEmpty()) {
							builder.append(sheetAttack.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).get(0).text());
							builder.append(MinerUtil.SPACE);
						}
					} else {
						Element soloRoll = sheetAttack.getElementsByClass(MinerUtil.SHEET_SOLO).first();
						if(soloRoll != null) {
							Element rollText = soloRoll.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
							Element labelText = sheetAttack.getElementsByClass(MinerUtil.LABEL).first();
							//Element rangeText = sheetAttack.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
									
							builder.append("Using ").append(labelText.text());		
							builder.append(" to hit = ").append(rollText.ownText());
						} else {
							//must be advantage roll
							Element firstRoll = sheetAttack.getElementsByClass(MinerUtil.SHEET_ADV).first().getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
							Element secondRoll = sheetAttack.getElementsByClass(MinerUtil.SHEET_ADV).get(1).getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
							Element labelText = sheetAttack.getElementsByClass(MinerUtil.LABEL).first();
							//Element rangeText = sheetAttack.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
							
							builder.append("Using ").append(labelText.text());
							if(isAdvantage(sheetAttack.getElementsByClass(MinerUtil.SHEET_ADV).first(), sheetAttack.getElementsByClass(MinerUtil.SHEET_ADV).get(1))) {
								builder.append(" to hit, with advantage = ").append(firstRoll.ownText()).append("/").append(secondRoll.ownText());
							} else {
								builder.append(" to hit, with disadvantage = ").append(firstRoll.ownText()).append("/").append(secondRoll.ownText());
							}												
						}
					}
				}
				
				if(sheetDescription != null) {
					if(sheetDescription.hasClass(MinerUtil.SHEET_INFO)) {
						builder.append(sheetDescription.text()).append(MinerUtil.SPACE);
					} else {
						Element rollText = sheetDescription.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element labelText = sheetDescription.getElementsByClass(MinerUtil.LABEL).first();
						Element sublabelText = sheetDescription.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
						
						if(labelText != null) {
							builder.append(labelText.text()).append(MinerUtil.SPACE_PLUS).append(rollText.ownText())
							.append(MinerUtil.SPACE).append(sublabelText.ownText()).append(MinerUtil.SPACE);
						} else {
							builder.append(MinerUtil.SPACE_PLUS).append(rollText.ownText())
							.append(MinerUtil.SPACE).append(sublabelText.ownText()).append(MinerUtil.SPACE);
						}
					}					
				}
			
				/**
				 * sheet-damagetemplate is non-optional, however its contents
				 * will change if the optional sheet-atk is missing, or the sheet-atk 
				 * block is also a sheet-save block.
				 */
				if(sheetAttack != null) {
					Element soloRoll = sheetDmg.getElementsByClass(MinerUtil.SHEET_SOLO).first();
					if(soloRoll != null) {
						Element damageText = soloRoll.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element damageType = soloRoll.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
								
						builder.append("For ").append(damageText.text());		
						builder.append(" of ").append(damageType.ownText()).append(MinerUtil.SPACE).append(" damage.");
					} else {
						//The SHEET_ADV element here represents a case where we have two distinct damage types to manage:
						Element firstRoll = sheetDmg.getElementsByClass(MinerUtil.SHEET_ADV).first().getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element firstLabel = sheetDmg.getElementsByClass(MinerUtil.SHEET_ADV).first().getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
						Element secondRoll = sheetDmg.getElementsByClass(MinerUtil.SHEET_ADV).get(1).getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element secondLabel = sheetDmg.getElementsByClass(MinerUtil.SHEET_ADV).get(1).getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
					
						builder.append("For ").append(firstRoll.text());		
						builder.append(" of ").append(firstLabel.ownText()).append(MinerUtil.SPACE);
						builder.append("and ").append(secondRoll.text());		
						builder.append(" of ").append(secondLabel.ownText()).append(MinerUtil.SPACE).append(" damage.");		
					}
				} else {
					Element soloRoll = sheetDmg.getElementsByClass(MinerUtil.SHEET_SOLO).first();
					if(soloRoll != null) {
						Element rollText = soloRoll.getElementsByClass(MinerUtil.INLINE_ROLL_RESULT).first();
						Element rollSublabel = soloRoll.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
						Element labelText = sheetDmg.getElementsByClass(MinerUtil.LABEL).first();
						//Element rangeText = sheetDmg.getElementsByClass(MinerUtil.SHEET_SUB_LABEL).first();
						
						builder.append(labelText.text()).append(" for ")
							.append(rollText.text()).append(MinerUtil.SPACE).append(MinerUtil.HP)
							.append(" of ").append(rollSublabel.text()).append(MinerUtil.SPACE); 					
					} else {
						builder.append("Unparsable entry");
						LOGGER.info("Received message content we are unsure about parsing: " + sheetDmg.toString());
					}
				}
			}
		}
		messageContents.add(builder.toString());
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCsvString() {
		boolean foundFirst = false;
		StringBuilder builder = new StringBuilder();
		builder.append(super.toCsvString("Attack"));		
		
		for(String content : messageContents) {
			if(foundFirst) builder.append(MinerUtil.LINEFEED);
			builder.append(content);
			foundFirst = true;
		}		
		return builder.toString();
	}
}

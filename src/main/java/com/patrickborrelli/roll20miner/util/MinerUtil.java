package com.patrickborrelli.roll20miner.util;

/**
 * A singleton providing all hard-coded static values.
 * 
 * @author patrick borrelli
 */
public class MinerUtil {

	private static volatile MinerUtil instance;	
	
	//Message Type determination:
	public static final String ATTACK = "sheet-rolltemplate-atk";
	public static final String ATTACK_DMG = "sheet-rolltemplate-atkdmg";
	public static final String TRAIT = "sheet-rolltemplate-traits";
	public static final String SIMPLE_ROLL = "sheet-rolltemplate-simple";
	public static final String PRIVATE_MSG = "private";
	public static final String MESSAGE = "message";
	public static final String DESCRIPTION = "desc";
	public static final String EMOTE = "emote";
	
	public static final String TIMESTAMP = "tstamp";
	public static final String SENDER = "by";
	public static final String AVATAR_URL = "avatar";	
	public static final String CONTENT_CONTAINER = "content";
	
	public static final String INLINE_ROLL_RESULT = "inlinerollresult";
	public static final String LABEL = "sheet-label";
	public static final String SHEET_DESCRIPTION = "sheet-desc";
	public static final String SHEET_SUB_LABEL = "sheet-sublabel";
	public static final String SHEET_ATTACK = "sheet-atk";
	public static final String SHEET_DAMAGE_TEMPLATE = "sheet-damagetemplate";
	public static final String SHEET_DAMAGE = "sheet-damage";
	public static final String SHEET_HEADER = "sheet-header";
	public static final String SHEET_SUBHEADER = "sheet-subheader";
	
	//generic constants
	public static final String SPACE = " ";
	public static final String EMPTY_STRING = "";
	public static final String EQ = " = ";
	
	public static MinerUtil getInstance() {
		if(instance == null) {
			synchronized(MinerUtil.class) {
				if(instance == null) {
					instance = new MinerUtil();
				}
			}			
		}
		return instance;
	}
	
	private MinerUtil() {
	
	}
	
	public static String cleanAttackText(String attack) {
		//utility method to eliminate known bad formatting in Roll20 chat log messages:		
		return attack.replace("<span class=\"basicdiceroll\">", MinerUtil.EMPTY_STRING).replace("</span>", MinerUtil.EMPTY_STRING).replace("cs>20", MinerUtil.EMPTY_STRING);
	}
}

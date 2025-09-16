package com.patrickborrelli.roll20miner.util;

/**
 * A singleton providing all hard-coded static values.
 * 
 * @author patrick borrelli
 */
public class MinerUtil {

	private static volatile MinerUtil instance;	
	public static final String TIMESTAMP = "tstamp";
	public static final String SENDER = "by";
	public static final String AVATAR_URL = "avatar";
	public static final String ATTACK = "sheet-rolltemplate-atk";
	public static final String ATTACK_DMG = "sheet-rolltemplate-atkdmg";
	public static final String TRAIT = "sheet-rolltemplate-traits";
	public static final String SIMPLE_ROLL = "sheet-rolltemplate-simple";
	public static final String PRIVATE_MSG = "private";
	public static final String CONTENT_CONTAINER = "content";
	public static final String MESSAGE = "message";
	public static final String DESCRIPTION = "desc";
	public static final String EMOTE = "emote";
	
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
}

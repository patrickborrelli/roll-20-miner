package com.patrickborrelli.roll20miner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttackMessage extends Message {

	private InlineRollResult [] inlineRollResults;
	private String sheetLabel;
	private SheetDamage damage;
}

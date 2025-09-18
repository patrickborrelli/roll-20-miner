package com.patrickborrelli.roll20miner.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextMessage extends Message {

	private String messageContent;

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
}

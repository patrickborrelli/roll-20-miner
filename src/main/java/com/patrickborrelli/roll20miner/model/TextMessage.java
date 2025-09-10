package com.patrickborrelli.roll20miner.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextMessage extends Message {

	private String messageContent;
}

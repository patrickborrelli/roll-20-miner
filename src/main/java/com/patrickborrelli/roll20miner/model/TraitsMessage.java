package com.patrickborrelli.roll20miner.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import com.patrickborrelli.roll20miner.util.MinerUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TraitsMessage extends Message {
	
	private List<String> rows;
	private List<String> rowHeaders;
	private List<String> rowSubheaders;
	
	public TraitsMessage(String avatarUrl, String timestamp, String author, int messageIndex, Element trait) {
		super(avatarUrl, timestamp, author, messageIndex);  
		
		rows = new ArrayList<>();
		rowHeaders = new ArrayList<>();
		rowSubheaders = new ArrayList<>();
		
		List<Element> allTraits = trait.getElementsByClass(MinerUtil.TRAIT);
		
		for(Element aTrait : allTraits) {			
			Element header = aTrait.getElementsByClass(MinerUtil.SHEET_HEADER).first();
			if(header!= null) {
				rowHeaders.add(header.ownText());
			}
			
			Element subheader = aTrait.getElementsByClass(MinerUtil.SHEET_SUBHEADER).first();
			if(subheader != null) {
				rowSubheaders.add(subheader.ownText());
			}
			
			Element row = aTrait.getElementsByClass(MinerUtil.SHEET_DESCRIPTION).first();
			if(row != null) {
				rows.add(row.ownText());
			}
		}
		
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCsvString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toCsvString());
		
		if(rowHeaders.size() > 0) builder.append(csvEscape(getRowHeaders().get(0)));		
		if(rowSubheaders.size() > 0) builder.append(csvEscape(getRowSubheaders().get(0)));		
		if(rows.size() > 0) builder.append(csvEscape(getRows().get(0)));
		return builder.toString();
	}
}

package com.patrickborrelli.roll20miner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.model.Message;

public class Roll20LogMiner {

	private static final String FILEPATH = "D:\\local-repository\\roll-20-miner\\ChatLog.html";	
	private static List<Message> messages;

	public static void main(String[] args) throws IOException {
		MessageFactory factory = new MessageFactory();
		
		messages = new ArrayList<>();
		File file = new File(FILEPATH);

		Document doc = Jsoup.parse(file, "UTF-8");
		Elements body = doc.getElementsByClass("message general");

		for (Element element : body) {
			messages.add(factory.createMessageObject(element));
		}
		
		//TODO: write messages out to console and file system.
	}

	private static void logit(String logString) {
		System.out.println(logString);
	}

}

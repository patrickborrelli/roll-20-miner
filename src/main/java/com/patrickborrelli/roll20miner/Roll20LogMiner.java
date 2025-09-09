package com.patrickborrelli.roll20miner;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Roll20LogMiner {

	private static final String FILEPATH = "C:\\local-repository\\roll-20-miner\\ChatLog.html";

	public static void main(String[] args) throws IOException {
		File file = new File(FILEPATH);
		logit(file.getAbsolutePath());

		Document doc = Jsoup.parse(file, "UTF-8");
		Elements body = doc.getElementsByClass("message general");

		for (Element element : body) {

		}
	}

	private static void logit(String logString) {
		System.out.println(logString);
	}

}

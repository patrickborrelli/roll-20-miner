package com.patrickborrelli.roll20miner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.patrickborrelli.roll20miner.model.Message;
import com.patrickborrelli.roll20miner.util.MinerUtil;


public class Roll20LogMiner {

	private static final Logger LOGGER = LogManager.getLogger(Roll20LogMiner.class);	

	public static void main(String[] args) throws IOException {
		
		List<Message> messages = null;
		LogMinerParser parser = new LogMinerParser();
		
		if(args == null || (args.length != 1  && args.length != 3)) {
			//TODO: print out usage text and terminate
			LOGGER.error("Usage:  java Roll20LogMiner <file containing chat log> [<output file> <CSV||TXT>]");
			System.exit(1);
		} 		
		
		File file = new File(args[0]);
		
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements content = doc.getElementsByClass(MinerUtil.CONTENT_CONTAINER);
			
		if(content.size() != 1) {
			LOGGER.error("Ummm");
		} else {
			messages = parser.parseElements(content.get(0).getElementsByClass(MinerUtil.MESSAGE));
		}
				
		//for the moment, assume we are dealing with default situation (no optionals provided)
		File output = new File("output.csv");		
		PrintWriter writer = new PrintWriter(output);	    
	    		
		for(Message message : messages) {
			writer.println(message.toCsvString());
			LOGGER.info(message.toString());
		}
		writer.close();
	}
}



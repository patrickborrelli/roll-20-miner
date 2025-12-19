package com.patrickborrelli.roll20miner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpecificBlockHarvest {
	private static final Logger LOGGER = LogManager.getLogger(SpecificBlockHarvest.class);	

	public static void main(String[] args) throws IOException {
				
		if(args == null || (args.length < 3)) {
			//print out usage text and terminate
			LOGGER.error("Usage:  java Roll20LogMiner <file containing chat log> <blockClass> <output file> <ignoreClass>");
			System.exit(1);
		} 		
		
		File file = new File(args[0]);
		
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements content = doc.getElementsByClass(args[1]);
		
		//for the moment, assume we are dealing with default situation (no optional arguments provided)
		File output = new File(args[2]);		
		PrintWriter writer = new PrintWriter(output);
			
		if(null == content || content.size() == 0) {
			LOGGER.error("Ummm");
		} else {
			for(Element message : content) {
				//if we have an optional 4th argument, it is an exclusion class:
				if(args.length == 4) {
					Elements ignoreEl = message.getElementsByClass(args[3]);
					if((ignoreEl == null) || ignoreEl.isEmpty()) {
						//does not have the excluded element:
						writer.println(message.toString());
						LOGGER.info(message.toString());
					}
				} else {
					writer.println(message.toString());
					LOGGER.info(message.toString());
				}
			}
		}    		
		writer.close();
	}
}

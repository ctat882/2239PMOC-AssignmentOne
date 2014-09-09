package au.edu.unsw.sltf.csv;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import au.edu.unsw.sltf.csv.*;

public class CsvTester {
	public static void main(String[] args) throws IOException {
		
			
			
			
			/** The eventSetId provided as an input parameter */
			String eventSetId = args[0];
			/** The targetCurrency value provided as an input parameter*/
			String targetCurrency = args[1];
			
			String rate = args[2];
			
			Double actualRate = Double.parseDouble(rate);
			/* Set the directories and file paths */
			/** The directory of the market data files produced by currencyConvertMarketData */
			String marketDataDir = System.getenv("CATALINA_HOME")
					+ "/webapps/ROOT/";
			/**
			 * The file path of the market data file corresponding to the eventSetId
			 * given
			 */
			String marketDataFilePath = marketDataDir + eventSetId + ".csv";
			/**
			 * Location of the temporary directory 
			 */
			String tmpFileDirectory = System.getProperty("java.io.tmpdir");
			
			String convertedEventSetId = "";
			
			
			convertedEventSetId = UUID.randomUUID().toString();
			String outputFilePath = marketDataDir + convertedEventSetId + ".csv";
			
			
			CsvReader reader = new CsvReader(marketDataFilePath);
			if (!reader.initialiseReader()) {
				// TODO throw ImportDownloadFaultException program
				System.out.println("BAD CSV");
			}
			CsvWriter writer;
			writer = new CsvWriter(outputFilePath);
			MarketData data = reader.getMarketDataRow();
			while (data != null) {
				if (!data.getPrice().isEmpty()) {
					System.out.println(data.getDateString() + "," + data.getTimeString() +"," +  data.getPrice());
					Double price = data.getActualPrice();
					price = price * actualRate;
					long factor = (long)Math.pow(10, 2);
					double val = price * factor;
					long tmp = Math.round(val);
					price =(double) tmp / factor;
					
					data.setActualPrice(price);
					data.setCurrencyType(targetCurrency);
					System.out.println("AFTER: CODE = " + data.getCurrencyType() +
							"PRICE = " + data.getActualPrice());
					writer.writeRow(data);
				}
				
				data = reader.getMarketDataRow();
				if(data == null) System.out.println("Data == NULL");
			}
			writer.closeFile();
		
	}
}

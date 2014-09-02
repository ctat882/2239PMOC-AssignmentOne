package au.edu.unsw.sltf.csv;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import au.edu.unsw.sltf.csv.*;

public class CsvTester {

	public static void main(String[] args) throws IOException {
		
		try {
			URL link = new URL(args[0]);
			String outputFile = System.getProperty("java.io.tmpdir") + "/"
					+ "output.csv";
			System.out.println("OUTPUTFILE:"+ outputFile);
//			CsvDownloader dl = new CsvDownloader(link);
//			dl.downloadCsv();
//			System.out.println("FILE DOWNLOADED");
//			String base = link.getFile();
//			base = base.substring(base.lastIndexOf('/'), base.length());
//			String filepath = System.getProperty("java.io.tmpdir") + "/"
//					+ base;
			String filepath = System.getProperty("java.io.tmpdir") + "/test.csv";		
			CsvReader reader = new CsvReader(filepath);
			if (!reader.initialiseReader()) {
				// TODO throw ImportDownloadFaultException program
				System.out.println("BAD CSV");
			}
			CsvWriter writer;
			writer = new CsvWriter(outputFile);
			MarketData data = reader.getMarketDataRow();
			while (data != null) {
				if (data.getSec().contentEquals("ABCD")) {
//					System.out.println("TESTER: data.getDateString: " + data.getDateString());
					writer.writeRow(data);					
				}
				data = reader.getMarketDataRow();
			}
			writer.closeFile();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}

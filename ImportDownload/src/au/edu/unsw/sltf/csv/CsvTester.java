package au.edu.unsw.sltf.csv;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import au.edu.unsw.sltf.csv.*;

public class CsvTester {

	public static void main(String[] args) throws IOException {
		
		try {
//			UUID fbase = UUID.fromString("ABCD"+"28-JUN-1999"+"29-JUN-2000");
			String fbase = "ABCD"+"28-JUN-1999"+"29-JUN-2000";
			
			System.out.println("fbase = " + fbase.hashCode());
			
			URL link = new URL(args[0]);
			String outputFile = System.getProperty("java.io.tmpdir") + "/"
					+ "output.csv";
			System.out.println("CATALINA_HOME:"+ System.getenv("CATALINA_HOME"));
			System.out.println("OUTPUTFILE:"+ outputFile);
			/* Download file to java.io.tmpdir directory */
			CsvDownloader dl = new CsvDownloader(link,System.getProperty("java.io.tmpdir"));
			dl.downloadCsv();
			System.out.println("FILE DOWNLOADED");
			String base = link.getFile();
			base = base.substring(base.lastIndexOf('/'), base.length());
			String filepath = System.getProperty("java.io.tmpdir") + "/"
					+ base;
//			String filepath = System.getProperty("java.io.tmpdir") + "/test.csv";		
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

package au.edu.unsw.sltf.currencyconversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CurrencyTableDownloader {
	private URL address;
	private final String filename = "currencyTable.csv";
	private final String url = "http://www.xe.com/currencytables/?from=AUD&date=2014-08-20";
	private String destination;
	
	public CurrencyTableDownloader ( String destination)  {
		this.destination = destination;
	}
	
	public boolean initialiseDownloader() {
		boolean validUrl;
		try {
			this.address = new URL (this.url);
			validUrl = true;
		} catch (MalformedURLException e) {
			validUrl = false;
		}
		return validUrl;
	}
	
	public String downloadToCsv () throws IOException {
		String filePath = destination + "/currencyTable.csv";
		File f = new File(filePath);
		if(!f.exists()) {
			f.createNewFile();			
			int lineCount = 0;
			String line = "";
			BufferedReader in = null;
			URLConnection connection = address.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line= in.readLine())!= null && !line.contains("/currency/")) {
				lineCount++;
			}
			/* If not null, then line found */
			if(line != null) {
				String[] table = line.split("[<][/]tr[>]");
				FileWriter fw = new FileWriter(f);
				BufferedWriter writer = new BufferedWriter(fw);
				for(int i = 0; i < table.length; i++) {
					String newRow = convertToCsvRow(table[i]);
					if(!newRow.isEmpty()) {
						writer.write(newRow);
						writer.newLine();
					}
				}
				writer.close();
				fw.close();				
			}
			in.close();
		}		
		return filePath;
	}
	
	
	private String convertToCsvRow (String line) {

		String output = "";
		
		if (! Pattern.matches("^[<][!].*$", line)) {
			
			StringBuilder sB = new StringBuilder();
			/* Get Code */
			String pattern = "[>][A-Z]{3}[<]";
			Matcher matcher = Pattern.compile(pattern).matcher(line);
			String match = "";
			if(matcher.find()) {
			 match = matcher.group();
			sB.append(match.substring(match.indexOf('>') + 1, match.indexOf('<')));
			sB.append(',');
			}
			/* Get Units per AUD*/
			pattern = "[>][0-9]+[.][0-9]+[<]";
			matcher = Pattern.compile(pattern).matcher(line);
			if(matcher.find()) {
			match = matcher.group(0);
			sB.append(match.substring(match.indexOf('>') + 1, match.indexOf('<')));
			sB.append(',');
			/* Get AUD per Unit*/
			}
			if (matcher.find()) {
			match = matcher.group(0);
			sB.append(match.substring(match.indexOf('>') + 1, match.indexOf('<')));
			}
			output = sB.toString();
		
		}
		
		return output;
	}
}

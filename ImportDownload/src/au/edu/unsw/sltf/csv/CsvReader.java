/**
 *
 */
package au.edu.unsw.sltf.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Corey Tattam
 *
 */
public class CsvReader {
	private String file;
	private BufferedReader br;
	private String splitBy;
	private static final int SEC = 0;
	private static final int DATE = 1;
	private static final int TIME = 2;
	private static final int OFFSET = 3;
	private static final int EVENT = 4;
	private static final int PRICE = 5;
	private static final int VOLUME = 6;
	private static final int BIDPRICE = 7;
	private static final int BIDSIZE = 8;
	private static final int ASKPRICE = 9;
	private static final int ASKSIZE = 10;

	public CsvReader(String filename) {
		this.file = filename;
		try {
			this.br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.splitBy = ",";
	}

	public boolean initialiseReader() {
		String line = "";
		boolean lineRead;
		try {
			if ((line = this.br.readLine()) != null) {
				if (Pattern.matches("^[#]RIC.*", line)) {
					lineRead = true;
				} else
					lineRead = false;
			} else {
				this.br.close();
				lineRead = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lineRead = false;
		}
		return lineRead;
	}

	public MarketData getMarketDataRow() {
		String line = "";
		MarketData row;
		try {
			if ((line = this.br.readLine()) != null) {
				String[] data = line.split(splitBy,-11);
				if(data.length != 11) System.out.println("Error Data array size is " + data.length + ", LINE:" + line);
//				System.out.println("LINE:" + line);
				row = new MarketData();
				if(!data[SEC].isEmpty())row.setSec(data[SEC]);
				if(!data[DATE].isEmpty())row.setDate(data[DATE]);
				if(!data[TIME].isEmpty())row.setTime(data[TIME]);
//				System.out.println("data[DATE]:" + data[DATE]);
//				System.out.println("row.getDateString:" + row.getDateString());
				
				
				if(!data[OFFSET].isEmpty())row.setOffset(data[OFFSET]);
				if(!data[EVENT].isEmpty())row.setEventType(data[EVENT]);
				if(!data[PRICE].isEmpty())row.setPrice(Double.parseDouble(data[PRICE]));
				if(!data[VOLUME].isEmpty())row.setVolume(Integer.parseInt(data[VOLUME]));
				if(!data[BIDPRICE].isEmpty())row.setBidPrice(Double.parseDouble(data[BIDPRICE]));
				if(!data[BIDSIZE].isEmpty())row.setBidSize(Integer.parseInt(data[BIDSIZE]));
				if(!data[ASKPRICE].isEmpty())row.setAskPrice(Double.parseDouble(data[ASKPRICE]));
				if(!data[ASKSIZE].isEmpty())row.setAskSize(Integer.parseInt(data[ASKSIZE]));
			} else {
				row = null;
				this.br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			row = null;
		}
		return row;
	}
}

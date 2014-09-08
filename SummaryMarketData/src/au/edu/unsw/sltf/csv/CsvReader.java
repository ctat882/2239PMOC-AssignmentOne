package au.edu.unsw.sltf.csv;

import java.io.BufferedReader;
import java.io.File;
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
	
	private static final long K = 1024;
	private static final long M = K * K;
	private static final long G = M * K;
	private static final long T = G * K;

	public CsvReader(String filename) {
		this.file = filename;
		try {
			this.br = new BufferedReader(new FileReader(this.file));
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
				String[] data = line.split(splitBy, -11);
				if (data.length != 11)
					System.out.println("Error Data array size is "
							+ data.length + ", LINE:" + line);
				// System.out.println("LINE:" + line);
				row = new MarketData();
				if (!data[SEC].isEmpty())
					row.setSec(data[SEC]);
				if (!data[DATE].isEmpty())
					row.setDate(data[DATE]);
				if (!data[TIME].isEmpty())
					row.setTime(data[TIME]);
				// System.out.println("data[DATE]:" + data[DATE]);
				// System.out.println("row.getDateString:" +
				// row.getDateString());
				if (!data[OFFSET].isEmpty())
					row.setOffset(data[OFFSET]);
				if (!data[EVENT].isEmpty())
					row.setEventType(data[EVENT]);
				if (!data[PRICE].isEmpty())
					row.setPrice(data[PRICE]);
				if (!data[VOLUME].isEmpty())
					row.setVolume(Integer.parseInt(data[VOLUME]));
				if (!data[BIDPRICE].isEmpty())
					row.setBidPrice(Double.parseDouble(data[BIDPRICE]));
				if (!data[BIDSIZE].isEmpty())
					row.setBidSize(Integer.parseInt(data[BIDSIZE]));
				if (!data[ASKPRICE].isEmpty())
					row.setAskPrice(Double.parseDouble(data[ASKPRICE]));
				if (!data[ASKSIZE].isEmpty())
					row.setAskSize(Integer.parseInt(data[ASKSIZE]));
				row.setTimeStamp();
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
	
	public String getFileSize () {
		String fileSize = "";
		Long size = 0L;
		File f = new File(this.file);
		if (f.exists()) {
			size = f.length();
			fileSize = convertByteCount (size);
		}
		
		return fileSize;
	}
	
	/**
	 * Convert byte count into human readable file size.
	 * Code modified from: www.stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
	 * @param bytes
	 * @return
	 */
	private String convertByteCount (long bytes) {
		String size = "";
		final long[] dividers = new long [] {T,G,M,K,1};
		final String[] units = new String[] {"TB","GB","MB","KB","B"};
		
		for(int i = 0; i < 5; i++) {
			final long divider = dividers[i];
			if(bytes >= divider) {
				final double result = divider > 1 ? (double) bytes / (double) divider : (double) bytes;
				size = String.format("%.1f %s", Double.valueOf(result), units[i]);
				break;
			}
		}
		
		return size;
	}
}

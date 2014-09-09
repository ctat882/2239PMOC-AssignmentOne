package au.edu.unsw.sltf.currencyconversion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CurrencyCsvReader {
	
	private String file;
	private BufferedReader br;
	private static final String splitBy = ",";
	private static final int CODE = 0;
	private static final int UNITS_PER_AUD = 1;
	private static final int AUD_PER_UNIT = 2;
	
	public CurrencyCsvReader (String file) {
		this.file = file;		
	}
	
	/**
	 * Initialise the BufferedReader with the file path provided.
	 * @return Returns true if the file exists, false otherwise.
	 */
	public boolean initialiseReader () {
		boolean exists;
		try {
			this.br = new BufferedReader(new FileReader(this.file));
			exists = true;
		} catch (FileNotFoundException e) {
			exists = false;
		}
		
		return exists;
	}
	
	/**
	 * Extract a row from the current currency table CSV.
	 * @return A CurrencyData object, or null if end of file.
	 */
	public CurrencyData getCurrencyDataRow () {
		String line = "";
		CurrencyData row;
		
		try {
			if((line = this.br.readLine()) != null) {
				String[] data = line.split(splitBy);
				row = new CurrencyData();
				row.setCode(data[CODE]);
				row.setUnitsPerAUD(Double.parseDouble(data[UNITS_PER_AUD]));
				row.setAudPerUnit(Double.parseDouble(data[AUD_PER_UNIT]));
				
			}
			else {
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
	
	/**
	 * Close Reader.
	 * @throws IOException
	 */
	public void closeReader () throws IOException {
		br.close();
	}
}

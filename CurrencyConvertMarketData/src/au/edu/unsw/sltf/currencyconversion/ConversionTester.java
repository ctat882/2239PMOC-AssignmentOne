package au.edu.unsw.sltf.currencyconversion;

import java.io.IOException;
import java.net.MalformedURLException;


public class ConversionTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String eventSetId = args[0];
		String targetCurrency = args[1];
		CurrencyData data = getCurrencyData (System.getProperty("java.io.tmpdir"), targetCurrency);
		System.out.println("CODE: " + data.getCode());
		
		
		
	}
	
	/**
	 * Download the currency table, and retrieve the row matching the target currency code.
	 * @param curTableDirectory
	 * @param targetCurrency
	 * @return
	 * @throws CurrencyConvertMarketDataFaultException
	 */
	private static CurrencyData getCurrencyData (String curTableDirectory, String targetCurrency) {
		
		CurrencyData curData = null;
		CurrencyTableDownloader dl = new CurrencyTableDownloader(curTableDirectory);
		if(! dl.initialiseDownloader()) {
			System.out.println(
					"Bad market data URL"+ "program");
		}
		/* Create Currency CSV Reader */
		CurrencyCsvReader curReader;
		try {
			curReader = new CurrencyCsvReader(dl.downloadToCsv());
			/* Try and open the currency table csv file */
			if (!curReader.initialiseReader()) {
				System.out.println("Could not open Currency Table CSV" +
						"program");
			}
			/* Iterate over the currency table until target currency found*/
			curData = curReader.getCurrencyDataRow();
			boolean currencyFound = false;
			while((curData != null) && !currencyFound) {
				if(curData.getCode().contentEquals(targetCurrency)) {
					currencyFound = true;
					try {
						curReader.closeReader();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					curData = curReader.getCurrencyDataRow();
				}
			}
			
			if(!currencyFound) {
				System.out.println("Could not locate target currency in currency table"+
						"program");
			}
		} catch (IOException e) {
			System.out.println("Could not Download Currency Table"+
					"program");
		}		
	
		
		
		return curData;
	}

}

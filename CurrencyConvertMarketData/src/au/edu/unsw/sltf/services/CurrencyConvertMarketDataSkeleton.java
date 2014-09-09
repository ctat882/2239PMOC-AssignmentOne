/**
 * CurrencyConvertMarketDataSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package au.edu.unsw.sltf.services;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import au.edu.unsw.sltf.csv.CsvReader;
import au.edu.unsw.sltf.csv.CsvWriter;
import au.edu.unsw.sltf.csv.MarketData;
import au.edu.unsw.sltf.currencyconversion.CurrencyCsvReader;
import au.edu.unsw.sltf.currencyconversion.CurrencyData;
import au.edu.unsw.sltf.currencyconversion.CurrencyTableDownloader;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataDocument.CurrencyConvertMarketData;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataFaultDocument.CurrencyConvertMarketDataFault;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataResponseDocument.CurrencyConvertMarketDataResponse;

/**
 * CurrencyConvertMarketDataSkeleton java skeleton for the axisService
 */
public class CurrencyConvertMarketDataSkeleton implements
		CurrencyConvertMarketDataSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param currencyConvertMarketData0
	 * @return currencyConvertMarketDataResponse1
	 * @throws CurrencyConvertMarketDataFaultException
	 */

	public au.edu.unsw.sltf.services.CurrencyConvertMarketDataResponseDocument currencyConvertMarketData(
			au.edu.unsw.sltf.services.CurrencyConvertMarketDataDocument currencyConvertMarketData0)
			throws CurrencyConvertMarketDataFaultException {
		
		/* Declare Variables and Extract currencyConvertMarketData elements */
		CurrencyConvertMarketData marketDataDoc = currencyConvertMarketData0.getCurrencyConvertMarketData();
		
		/** The eventSetId provided as an input parameter */
		String eventSetId = marketDataDoc.getEventSetId();
		/** The targetCurrency value provided as an input parameter*/
		String targetCurrency = marketDataDoc.getTargetCurrency();
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
		
		/* Validate Input */
		if (!isValidEventSetId(marketDataFilePath)) {
			throw (createFaultException(
					"Bad Event Set ID, file does not exist", "event"));
		}
		if (targetCurrency.contentEquals("AUD")) {
			throw (createFaultException(
					"Cannot convert to AUD, only from AUD", "currency"));
		}
		
		/* Check if market data has already been converted */
		if (alreadyConverted(marketDataFilePath)) {
			throw (createFaultException(
					"The file already contains converted prices", "marketData"));
		}
		
		/* Download Currency Table and locate conversion data */
		CurrencyData curData = getCurrencyData(tmpFileDirectory,targetCurrency);
		
		convertedEventSetId = convertPrices (marketDataDir,marketDataFilePath,curData);
		
		if (convertedEventSetId.isEmpty()) {
			throw (createFaultException(
					"DEBUG: Converted Prices", "marketData"));
		}
		
		CurrencyConvertMarketDataResponseDocument resDoc = CurrencyConvertMarketDataResponseDocument.Factory.newInstance();
		CurrencyConvertMarketDataResponse res = resDoc.addNewCurrencyConvertMarketDataResponse();
		res.setEventSetId(convertedEventSetId);
		
		return resDoc;
	}
	
	/**
	 * Convert market data currency.
	 * @param marketDataDirectory
	 * @param marketDataFilePath
	 * @param curData
	 * @return Return the eventSetId of the newly converted file.
	 * @throws CurrencyConvertMarketDataFaultException
	 */
	public String convertPrices (
			String marketDataDirectory, String marketDataFilePath,
			CurrencyData curData) throws CurrencyConvertMarketDataFaultException {
		
		String newEventSetId = UUID.randomUUID().toString();
		String outputFilePath = marketDataDirectory + newEventSetId + ".csv";
		
		
		CsvReader reader = new CsvReader(marketDataFilePath);
		/* Read the first line of the csv file, should be the commented line */
		if (!reader.initialiseReader()) {
			throw (createFaultException("Bad or Missing comment line in CSV ",
					"program"));
		}
		/* Create output file */
		CsvWriter writer;
		try {
			writer = new CsvWriter(outputFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw (createFaultException("Could not create output CSV",
					"program"));
		}
		
		/* Iterate over the MarketData and filter results */
		MarketData dataRow = reader.getMarketDataRow();
		while (dataRow != null) {
			/* Alter the price and currency type */
			if(! dataRow.getPrice().isEmpty()) {
				dataRow.setCurrencyType(curData.getCode());
				convertPrice(curData,dataRow);
				try {
					writer.writeRow(dataRow);
				} catch (IOException e) {
					e.printStackTrace();
					throw (createFaultException("Could Not Write to CSV",
							"program"));
				}
			}
			dataRow = reader.getMarketDataRow();
		}
		/* Close writer */
		try {
			writer.closeFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw (createFaultException("Could not close writer", "program"));
		}
		
		return newEventSetId;
	}
	
	public void convertPrice (CurrencyData curData, MarketData dataRow) {
		Double price = dataRow.getActualPrice();
		price = price * curData.getUnitsPerAUD();
		
		long factor = (long)Math.pow(10, 2);
		double val = price * factor;
		long tmp = Math.round(val);
		price =(double) tmp / factor;
		
		dataRow.setActualPrice(price);
	}
	
	public boolean alreadyConverted (String marketDataFilePath) throws CurrencyConvertMarketDataFaultException {
		boolean converted = false;
		/* Create CSV Reader */
		CsvReader reader = new CsvReader(marketDataFilePath);
		/* Read the first line of the csv file, should be the commented line */
		if (!reader.initialiseReader()) {
			throw (createFaultException("Bad or Missing comment line in CSV ",
					"program"));
		}
		/* Iterate over the MarketData and filter results.*/
		MarketData dataRow = reader.getMarketDataRow();
		/** Used to store the last seen date and time */
		while (dataRow != null) {
			if (! dataRow.getCurrencyType().contentEquals("AUD")) {
				converted = true;
				reader.closeReader();
				break;
			}
			dataRow = reader.getMarketDataRow();
		}
		return converted;
	}
	
	/**
	 * Download the currency table, and retrieve the row matching the target currency code.
	 * @param curTableDirectory
	 * @param targetCurrency
	 * @return
	 * @throws CurrencyConvertMarketDataFaultException
	 */
	public CurrencyData getCurrencyData (String curTableDirectory, String targetCurrency) throws CurrencyConvertMarketDataFaultException {
		
		CurrencyTableDownloader dl = new CurrencyTableDownloader(curTableDirectory);
		if(! dl.initialiseDownloader()) {
			throw (createFaultException(
					"Bad market data URL", "program"));
		}
		/* Create Currency CSV Reader */
		CurrencyCsvReader curReader;
		try {
			curReader = new CurrencyCsvReader(dl.downloadToCsv());
		} catch (IOException e) {
			throw (createFaultException("Could not Download Currency Table",
					"program"));
		}		
	
		/* Try and open the currency table csv file */
		if (!curReader.initialiseReader()) {
			throw (createFaultException("Could not open Currency Table CSV",
					"program"));
		}
		/* Iterate over the currency table until target currency found*/
		CurrencyData curData = curReader.getCurrencyDataRow();
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
			throw (createFaultException("Could not locate target currency in currency table",
					"currency"));
		}
		
		return curData;
	}
	
	/**
	 * Check that the event set ID is valid. 
	 * Checks if the file exists.
	 * @param filepath Where the associated file should exist.
	 * @return Return true if the file exists, false otherwise.
	 */
	public boolean isValidEventSetId(String filepath) {
		boolean isValid = false;
		File f = new File(filepath);
		if (f.exists())
			isValid = true;
		return isValid;
	}
	
	/**
	 * Create ImportDownloadFaultException based on fault type and message.
	 * 
	 * @param msg
	 * @param faultType
	 * @return
	 */
	public au.edu.unsw.sltf.services.CurrencyConvertMarketDataFaultException createFaultException(
			String msg, String faultType) {
		CurrencyConvertMarketDataFaultException faultExcep = new CurrencyConvertMarketDataFaultException();
		/*
		 * ImportDownloadFaultException takes a Import....FaultDocument as the
		 * message
		 */
		CurrencyConvertMarketDataFaultDocument faultDoc = CurrencyConvertMarketDataFaultDocument.Factory
				.newInstance();
		CurrencyConvertMarketDataFault idf = CurrencyConvertMarketDataFault.Factory.newInstance();
		// Set the fault type
		
		if (faultType.contentEquals("event"))
			idf.setFaultType(CurrencyConvertMarketDataFaultType.INVALID_EVENT_SET_ID);
		else if (faultType.contentEquals("program"))
			idf.setFaultType(CurrencyConvertMarketDataFaultType.PROGRAM_ERROR);
		else if (faultType.contentEquals("marketData"))
			idf.setFaultType(CurrencyConvertMarketDataFaultType.INVALID_MARKET_DATA);
		else if (faultType.contentEquals("currency"))
			idf.setFaultType(CurrencyConvertMarketDataFaultType.INVALID_TARGET_CURRENCY);
		// Set the fault message
		idf.setFaultMessage(msg);
		faultDoc.setCurrencyConvertMarketDataFault(idf);
		faultExcep.setFaultMessage(faultDoc);
		return faultExcep;
	}
}

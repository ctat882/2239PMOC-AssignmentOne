/**
 * SummaryMarketDataServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package au.edu.unsw.sltf.services;

import java.io.File;
import java.util.Calendar;

import au.edu.unsw.sltf.csv.CsvReader;
import au.edu.unsw.sltf.csv.MarketData;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument.SummaryMarketData;
import au.edu.unsw.sltf.services.SummaryMarketDataFaultDocument.SummaryMarketDataFault;
import au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument.SummaryMarketDataResponse;

/**
 * SummaryMarketDataServiceSkeleton java skeleton for the axisService
 */
public class SummaryMarketDataServiceSkeleton implements
		SummaryMarketDataServiceSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param summaryMarketData0
	 * @return summaryMarketDataResponse1
	 * @throws SummaryMarketDataFaultException
	 */

	public au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument summaryMarketData(
			au.edu.unsw.sltf.services.SummaryMarketDataDocument summaryMarketData0)
			throws SummaryMarketDataFaultException {
		
		/* Declare Variables and Extract importMarketData elements */
		SummaryMarketData marketDataDoc = summaryMarketData0.getSummaryMarketData();
		/** The eventSetId provided as the input parameter */
		String eventSetId = marketDataDoc.getEventSetId();
		/** The directory of the market data files produced by ImportMarketData */
		String marketDataDir = System.getenv("CATALINA_HOME")
				+ "/webapps/ROOT/";
		/**
		 * The file path of the market data file corresponding to the eventSetId
		 * given
		 */
		String marketDataFilePath = marketDataDir + eventSetId + ".csv";
		
		// The Return variables
		String sec = "";
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		String marketType = "";
		String currencyCode = "AUD";
		String fileSize = "";	
		
		
		/* Validate Input */
		if (!isValidEventSetId(marketDataFilePath)) {
			throw (createFaultException(
					"Bad Event Set ID, file does not exist", "event"));
		}
		
		/* Create CSV Reader */
		CsvReader reader = new CsvReader(marketDataFilePath);
		/* Read the first line of the csv file, should be the commented line */
		if (!reader.initialiseReader()) {
			throw (createFaultException("Bad or Missing comment line in CSV ",
					"program"));
		}
		
		/* Iterate over the MarketData and filter results.*/
		MarketData dataRow = reader.getMarketDataRow();
		boolean hasOneDataRow = false;
		/** Used to store the last seen date and time */
		MarketData tmpData = new MarketData();
		while (dataRow != null) {
			/* On first pass extract necessary data */
			if(! hasOneDataRow) {
				hasOneDataRow =  true;
				sec = dataRow.getSec();
				start = dataRow.getDate();
				Calendar startTime = dataRow.getTime();
				// TODO:
				// Have to set time-stamp as date and time are stored separately
				// Should implement function for this, can use for end time-stamp
				start.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
				start.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
				start.set(Calendar.SECOND, startTime.get(Calendar.SECOND));
				start.set(Calendar.MILLISECOND, startTime.get(Calendar.MILLISECOND));				
				
			}
			/* Check market type */
			if(marketType.isEmpty()) marketType = dataRow.getEventType();
			else if (!marketType.contentEquals(dataRow.getEventType())) {
				marketType = "Mixed";
			}
			
			/* Check and get the currency code */
			if(! dataRow.getCurrencyType().contentEquals("AUD")) {
				currencyCode = dataRow.getCurrencyType();
			}
			/* Store the last seen dataRow in the tmp variable */
			tmpData = dataRow;
			dataRow = reader.getMarketDataRow();
		}
		
		/* If there is now data in the file throw exception */
		if(! hasOneDataRow) {
			throw (createFaultException("No content in CSV ",
					"program"));
		}
		/* Set end date and time */
		end = tmpData.getDate();
		Calendar endTime = tmpData.getTime();
		end.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
		end.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
		end.set(Calendar.SECOND, endTime.get(Calendar.SECOND));
		end.set(Calendar.MILLISECOND, endTime.get(Calendar.MILLISECOND));
		
		/* Get File size */
		fileSize = reader.getFileSize();
		
		/* Create Return Document */
		SummaryMarketDataResponseDocument resDoc = SummaryMarketDataResponseDocument.Factory.newInstance();
		SummaryMarketDataResponse res = resDoc.addNewSummaryMarketDataResponse();
		/* Set return parameters */
		res.setCurrencyCode(currencyCode);
		res.setFileSize(fileSize);
		res.setSec(sec);
		res.setStartDate(start);
		res.setEndDate(end);
		res.setMarketType(marketType);
		res.setEventSetId(eventSetId);
		
		return resDoc;
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
	public au.edu.unsw.sltf.services.SummaryMarketDataFaultException createFaultException(
			String msg, String faultType) {
		SummaryMarketDataFaultException faultExcep = new SummaryMarketDataFaultException();
		/*
		 * ImportDownloadFaultException takes a Import....FaultDocument as the
		 * message
		 */
		SummaryMarketDataFaultDocument faultDoc = SummaryMarketDataFaultDocument.Factory
				.newInstance();
		SummaryMarketDataFault idf = SummaryMarketDataFault.Factory.newInstance();
		// Set the fault type
		
		if (faultType.contentEquals("event"))
			idf.setFaultType(SummaryMarketDataFaultType.INVALID_EVENT_SET_ID);
		else if (faultType.contentEquals("program"))
			idf.setFaultType(SummaryMarketDataFaultType.PROGRAM_ERROR);
		// Set the fault message
		idf.setFaultMessage(msg);
		faultDoc.setSummaryMarketDataFault(idf);
		faultExcep.setFaultMessage(faultDoc);
		return faultExcep;
	}

}

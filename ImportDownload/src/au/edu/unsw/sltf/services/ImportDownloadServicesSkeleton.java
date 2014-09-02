/**
 * ImportDownloadServicesSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012 (05:33:49 IST)
 */
package au.edu.unsw.sltf.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;

import au.edu.unsw.sltf.csv.CsvDownloader;
import au.edu.unsw.sltf.csv.CsvReader;
import au.edu.unsw.sltf.csv.CsvWriter;
import au.edu.unsw.sltf.csv.MarketData;
import au.edu.unsw.sltf.services.ImportDownloadFaultDocument.ImportDownloadFault;
import au.edu.unsw.sltf.services.ImportMarketDataDocument.ImportMarketData;
import au.edu.unsw.sltf.services.ImportMarketDataResponseDocument.ImportMarketDataResponse;
import au.edu.unsw.sltf.services.impl.ImportDownloadFaultDocumentImpl;

/**
 * ImportDownloadServicesSkeleton java skeleton for the axisService
 */
public class ImportDownloadServicesSkeleton implements
		ImportDownloadServicesSkeletonInterface {
	/**
	 * Auto generated method signature
	 *
	 * @param importMarketData0
	 * @return importMarketDataResponse1
	 * @throws ImportDownloadFaultException
	 */
	public au.edu.unsw.sltf.services.ImportMarketDataResponseDocument importMarketData(
			au.edu.unsw.sltf.services.ImportMarketDataDocument importMarketData0)
			throws ImportDownloadFaultException {
		// TODO : fill this with the necessary business logic
		// throw new java.lang.UnsupportedOperationException("Please implement "
		// + this.getClass().getName() + "#importMarketData");
		// Extract data elements
		ImportMarketData req = importMarketData0.getImportMarketData();
		String sec = req.getSec();
		if (! isValidSec(sec)) throw (createFaultException("Bad SEC","sec"));
		Calendar start = req.getStartDate();
		Calendar end = req.getEndDate();
		String outputFile = System.getProperty("catalina.home") + "/"
				+ "webapps/ROOT/"+ "output.csv";
		
			URL dataSource;
			try {
				dataSource = new URL(req.getDataSourceURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw (createFaultException("Incorrect URL","url"));
			}
			/* If input is validated, then download the file */
			CsvDownloader dl = new CsvDownloader(dataSource);
			try {
				dl.downloadCsv();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw (createFaultException("Bad CSV","program"));
			}
			/* Read the newly downloaded file */
			String filepath = System.getProperty("java.io.tmpdir") + "/"
					+ dataSource.getFile();
			CsvReader reader = new CsvReader(filepath);
			/* Read the first line of the csv file, should be the commented line */
			if (!reader.initialiseReader()) {
				// TODO throw ImportDownloadFaultException program
				throw (createFaultException("Bad CSV","program"));
			}
			/* Create output file */
			CsvWriter writer;
			try {
				writer = new CsvWriter(outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw (createFaultException("Could Not Create output CSV","program"));
			}
			/* Iterate over the MarketData */
			MarketData data = reader.getMarketDataRow();
			while (data != null) {
				if (isValidData(data, sec, start, end)) {
					try {
						writer.writeRow(data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw (createFaultException("Could Not Write to CSV","program"));
					}
				}
				data = reader.getMarketDataRow();
			}
			try {
				writer.closeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
		
		ImportMarketDataResponseDocument resDoc = ImportMarketDataResponseDocument.Factory
				.newInstance();
		ImportMarketDataResponse res = resDoc.addNewImportMarketDataResponse();
		// Give the output file path as the eventSetId
		res.setEventSetId(outputFile);
		// res.setReturn(returnStr);
		return resDoc;
	}
	
	
	/**
	 * Create ImportDownloadFaultException based on fault type and message.
	 * @param msg
	 * @param faultType
	 * @return
	 */
	public au.edu.unsw.sltf.services.ImportDownloadFaultException createFaultException (String msg, String faultType) {
		ImportDownloadFaultException faultExcep = new ImportDownloadFaultException();
		/* ImportDownloadFaultException takes a Import....FaultDocument as the message*/
		ImportDownloadFaultDocument faultDoc = ImportDownloadFaultDocument.Factory.newInstance();		
		ImportDownloadFault idf = ImportDownloadFault.Factory.newInstance();
		// Set the fault type
		if (faultType.contentEquals("url")) idf.setFaultType(ImportDownloadFaultType.INVALID_URL);
		else if (faultType.contentEquals("sec")) idf.setFaultType(ImportDownloadFaultType.INVALID_SEC_CODE);
		else if (faultType.contentEquals("event")) idf.setFaultType(ImportDownloadFaultType.INVALID_EVENT_SET_ID);
		else if (faultType.contentEquals("program")) idf.setFaultType(ImportDownloadFaultType.PROGRAM_ERROR);
		// Set the fault message
		idf.setFaultMessage(msg);
		faultDoc.setImportDownloadFault(idf);
		faultExcep.setFaultMessage(faultDoc);
		return faultExcep;
	}
	
	
	
	/**
	 * Auto generated method signature
	 *
	 * @param downloadFile2
	 * @return downloadFileResponse3
	 * @throws ImportDownloadFaultException
	 */
	public au.edu.unsw.sltf.services.DownloadFileResponseDocument downloadFile(
			au.edu.unsw.sltf.services.DownloadFileDocument downloadFile2)
			throws ImportDownloadFaultException {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#downloadFile");
	}

	/**
	 * Check that the MarketData row meets search criteria.
	 * 
	 * @param data
	 *            The MarketData row.
	 * @param sec
	 *            The Security Code to filter.
	 * @param start
	 *            The start date.
	 * @param end
	 *            The end date.
	 * @return Return true if the row matches the search criteria, false
	 *         otherwise.
	 */
	private boolean isValidData(MarketData data, String sec, Calendar start,
			Calendar end) {
		boolean isValid = true;
		if (!data.getSec().contentEquals(sec))
			isValid = false;
		else if (data.getDate().before(start))
			isValid = false;
		else if (data.getDate().after(end))
			isValid = false;
		return isValid;
	}

	/**
	 * Check if the dates are valid. (i.e the end date is after the start date).
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean isValidDates(Calendar start, Calendar end) {
		boolean isValid = true;
		if (start.after(end) || start.equals(end))
			isValid = false;
		return isValid;
	}

	/**
	 * Check that the Security Code is valid.
	 * 
	 * @param sec
	 * @return
	 */
	private boolean isValidSec(String sec) {
		boolean isValid = true;
		if (!Pattern.matches("^[A-Z]{3,4}$", sec))
			isValid = false;
		return isValid;
	}
}

/**
 * ImportDownloadServicesSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012 (05:33:49 IST)
 */
package au.edu.unsw.sltf.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Pattern;
import au.edu.unsw.sltf.csv.CsvDownloader;
import au.edu.unsw.sltf.csv.CsvReader;
import au.edu.unsw.sltf.csv.CsvWriter;
import au.edu.unsw.sltf.csv.MarketData;
import au.edu.unsw.sltf.services.DownloadFileDocument.DownloadFile;
import au.edu.unsw.sltf.services.DownloadFileResponseDocument.DownloadFileResponse;
import au.edu.unsw.sltf.services.ImportDownloadFaultDocument.ImportDownloadFault;
import au.edu.unsw.sltf.services.ImportMarketDataDocument.ImportMarketData;
import au.edu.unsw.sltf.services.ImportMarketDataResponseDocument.ImportMarketDataResponse;

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
		/* Declare Variables and Extract importMarketData elements */
		ImportMarketData req = importMarketData0.getImportMarketData();
		/* Check for Null values */
		if(req.getSec().isEmpty()) throw (createFaultException("Missing Security Code", "sec"));
		if(req.getDataSourceURL().isEmpty()) throw (createFaultException("Missing URL", "url"));
		
		/** The Security Code passed in from importMarketData */
		String sec = req.getSec();
		/** The start date passed in from importMarketData */
		Calendar start = req.getStartDate();
		/** The end date passed in from importMarketData */
		Calendar end = req.getEndDate();
		/** The URL passed in from importMarketData */
		URL dataSource;
		
		/* Validate Input */
		if (!isValidSec(sec))
			throw (createFaultException("Invalid Security Code (SEC). Code must be 3-4 letters", "sec"));
		if(!isValidURL(req.getDataSourceURL()))
			throw (createFaultException("Incorrect URL", "url"));
		
		try {
			dataSource = new URL(req.getDataSourceURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw (createFaultException("Incorrect URL", "url"));
		}
		if (!isValidDates(start, end))
			throw (createFaultException("Invalid Dates", "program"));
		
		/**
		 * Location of the temporary directory to store the downloaded csv file,
		 * retrieved from the URL parameter
		 */
		String tmpFileDirectory = System.getProperty("java.io.tmpdir");
		/** The temporary file path */
		String tmpFile = req.getDataSourceURL().substring(
				req.getDataSourceURL().lastIndexOf('/'),
				req.getDataSourceURL().length());
		String tmpFilePath = tmpFileDirectory + "/" + tmpFile;
		/** Location of the filtered Market Data file */
		String outputFileDirectory = System.getenv("CATALINA_HOME")
				+ "/webapps/ROOT/";
		/** The output file's base name - Randomly generated */
		String eventSetId = UUID.randomUUID().toString();
		/** The output file's full file name */
		String outputFilePath = outputFileDirectory + eventSetId + ".csv";
		
		// boolean test = false;
		/* If input is validated, then download the file to the temp directory */
		CsvDownloader dl = new CsvDownloader(dataSource, tmpFileDirectory);
		try {
			dl.downloadCsv();
			// test = true;
		} catch (IOException e) {
			throw (createFaultException("Error downloading the dataSource CSV file", "program"));
		}
		// TODO: DOWNLOAD CHECKED AND WORKED
		// if(test)throw
		// (createFaultException("Got past the download section","program"));
		/* Read the newly downloaded file */
		CsvReader reader = new CsvReader(tmpFilePath);
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
			/* Filter the current row */
			if (isValidData(dataRow, sec, start, end)) {
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
		/* Output eventSetId */
		ImportMarketDataResponseDocument resDoc = ImportMarketDataResponseDocument.Factory
				.newInstance();
		ImportMarketDataResponse res = resDoc.addNewImportMarketDataResponse();
		// Give the output file path as the eventSetId
		res.setEventSetId(eventSetId);
		// res.setReturn(returnStr);
		return resDoc;
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
		/* Declare Variables and Extract importMarketData elements */
		DownloadFile dl = downloadFile2.getDownloadFile();
		
		if (dl.getEventSetId().isEmpty()) {
			throw (createFaultException(
					"Missing Event Set ID", "event"));
		}
		/** The eventSetId provided as the input parameter */
		String eventSetId = dl.getEventSetId();
		/** The directory of the market data files produced by ImportMarketData */
		String marketDataDir = System.getenv("CATALINA_HOME")
				+ "/webapps/ROOT/";
		/**
		 * The file path of the market data file corresponding to the eventSetId
		 * given
		 */
		String marketDataFilePath = marketDataDir + eventSetId + ".csv";
		/** The URL address of the market data file */
		String dataURL = "http://localhost:8080/" + eventSetId + ".csv";
		/* Validate Input */
		if (!isValidEventSetId(marketDataFilePath)) {
			throw (createFaultException(
					"Bad Event Set ID, file does not exist", "event"));
		}
		/* Return the URL location of the market data file */
		DownloadFileResponseDocument resDoc = DownloadFileResponseDocument.Factory
				.newInstance();
		DownloadFileResponse res = resDoc.addNewDownloadFileResponse();
		res.setDataURL(dataURL);
		return resDoc;
	}

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
	public au.edu.unsw.sltf.services.ImportDownloadFaultException createFaultException(
			String msg, String faultType) {
		ImportDownloadFaultException faultExcep = new ImportDownloadFaultException();
		/*
		 * ImportDownloadFaultException takes a Import....FaultDocument as the
		 * message
		 */
		ImportDownloadFaultDocument faultDoc = ImportDownloadFaultDocument.Factory
				.newInstance();
		ImportDownloadFault idf = ImportDownloadFault.Factory.newInstance();
		// Set the fault type
		if (faultType.contentEquals("url"))
			idf.setFaultType(ImportDownloadFaultType.INVALID_URL);
		else if (faultType.contentEquals("sec"))
			idf.setFaultType(ImportDownloadFaultType.INVALID_SEC_CODE);
		else if (faultType.contentEquals("event"))
			idf.setFaultType(ImportDownloadFaultType.INVALID_EVENT_SET_ID);
		else if (faultType.contentEquals("program"))
			idf.setFaultType(ImportDownloadFaultType.PROGRAM_ERROR);
		// Set the fault message
		idf.setFaultMessage(msg);
		faultDoc.setImportDownloadFault(idf);
		faultExcep.setFaultMessage(faultDoc);
		return faultExcep;
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
		else if (data.getTimeStamp().before(start))
			isValid = false;
		else if (data.getTimeStamp().after(end))
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
	
	
	
	private boolean isValidURL(String url) {
		boolean isValid = true;
		if (!Pattern.matches("^http.*$", url))
			isValid = false;
		return isValid;
	}
	
}
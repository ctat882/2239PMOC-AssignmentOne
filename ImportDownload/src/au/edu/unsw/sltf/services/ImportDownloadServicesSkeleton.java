
/**
 * ImportDownloadServicesSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
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
import au.edu.unsw.sltf.services.ImportMarketDataDocument.ImportMarketData;
import au.edu.unsw.sltf.services.ImportMarketDataResponseDocument.ImportMarketDataResponse;
import au.edu.unsw.sltf.services.impl.ImportDownloadFaultDocumentImpl;
    /**
     *  ImportDownloadServicesSkeleton java skeleton for the axisService
     */
    public class ImportDownloadServicesSkeleton implements ImportDownloadServicesSkeletonInterface{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param importMarketData0 
             * @return importMarketDataResponse1 
             * @throws ImportDownloadFaultException 
         */
        
    	public au.edu.unsw.sltf.services.ImportMarketDataResponseDocument importMarketData
          (au.edu.unsw.sltf.services.ImportMarketDataDocument importMarketData0) throws ImportDownloadFaultException {
                //TODO : fill this with the necessary business logic
//                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#importMarketData");
                // Extract data elements
                ImportMarketData req = importMarketData0.getImportMarketData();
                String sec = req.getSec();
                Calendar start = req.getStartDate();
                Calendar end = req.getEndDate();
                try {
					URL dataSource = new URL(req.getDataSourceURL());
					/* If input is validated, then download the file */
					CsvDownloader dl = new CsvDownloader(dataSource);
					dl.downloadCsv();
					String filepath = System.getProperty("java.io.tmpdir") + "//" + dataSource.getFile();
					CsvReader reader = new CsvReader(filepath);
					if(! reader.initialiseReader()) {
						//TODO throw ImportDownloadFaultException program
					}
					String outputFile = System.getProperty("catalina.home") + "//" + "output.csv";
					CsvWriter writer = new CsvWriter(outputFile);
					MarketData data = reader.getMarketDataRow();
					while (data != null) {
						if (isValidData(data,sec,start,end)) {
							writer.writeRow(data);
						}
						data = reader.getMarketDataRow();
					}
					writer.closeFile();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// TODO throw ImportDownloadFaultException
					ImportDownloadFaultException faultExcep = new ImportDownloadFaultException ();
					ImportDownloadFaultDocument fault = ImportDownloadFaultDocument.Factory.newInstance();
					fault.addNewImportDownloadFault();
					
					//ImportDownloadFaultType faultType = ImportDownloadFaultType.Factory.newInstance(ImportDownloadFaultType.Enum.INT_INVALID_URL,null);
					
				}
                
                
                
                
                

                ImportMarketDataResponseDocument resDoc = ImportMarketDataResponseDocument.Factory.newInstance();
                ImportMarketDataResponse res = resDoc.addNewImportMarketDataResponse();
//                res.setReturn(returnStr);

                return resDoc;
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param downloadFile2 
             * @return downloadFileResponse3 
             * @throws ImportDownloadFaultException 
         */
        
         public au.edu.unsw.sltf.services.DownloadFileResponseDocument downloadFile
          (au.edu.unsw.sltf.services.DownloadFileDocument downloadFile2) throws ImportDownloadFaultException{
                //TODO : fill this with the necessary business logic
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#downloadFile");
         }
         
         /**
          * Check that the MarketData row meets search criteria.
          * @param data The MarketData row.
          * @param sec The Security Code to filter.
          * @param start The start date.
          * @param end The end date.
          * @return Return true if the row matches the search criteria, false otherwise.
          */
         private boolean isValidData (MarketData data, String sec, Calendar start, Calendar end ) {
        	 boolean isValid = true;
        	 if (!data.getSec().contentEquals(sec)) isValid = false;
        	 else if (data.getDate().before(start)) isValid = false;
        	 else if (data.getDate().after(end)) isValid = false;
        	 return isValid;
         }
         
         private boolean isValidDates (Calendar start, Calendar end) {
        	 boolean isValid = true;
        	 if(start.after(end) || start.equals(end)) isValid = false;
        	 
        	 return isValid;
         }
         
         private boolean isValidSec (String sec) {
        	 boolean isValid = true;
        	 if(! Pattern.matches("^[A-Z]{3,4}$",sec)) isValid = false;
        	 
        	 return isValid;
         }
     
    }
    
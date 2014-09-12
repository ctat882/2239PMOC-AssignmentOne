package au.edu.unsw.sltf.logic;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.axis2.AxisFault;

import au.edu.unsw.sltf.client.CurrencyConvertMarketDataFaultException;
import au.edu.unsw.sltf.client.CurrencyConvertMarketDataStub;
import au.edu.unsw.sltf.client.ImportDownloadFaultException;
import au.edu.unsw.sltf.client.ImportDownloadServicesStub;
import au.edu.unsw.sltf.client.SummaryMarketDataFaultException;
import au.edu.unsw.sltf.client.SummaryMarketDataServiceStub;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataDocument;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataDocument.CurrencyConvertMarketData;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataFaultDocument;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataFaultDocument.CurrencyConvertMarketDataFault;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataResponseDocument;
import au.edu.unsw.sltf.services.CurrencyConvertMarketDataResponseDocument.CurrencyConvertMarketDataResponse;
import au.edu.unsw.sltf.services.DownloadFileDocument;
import au.edu.unsw.sltf.services.DownloadFileDocument.DownloadFile;
import au.edu.unsw.sltf.services.DownloadFileResponseDocument;
import au.edu.unsw.sltf.services.DownloadFileResponseDocument.DownloadFileResponse;
import au.edu.unsw.sltf.services.ImportDownloadFaultDocument;
import au.edu.unsw.sltf.services.ImportDownloadFaultDocument.ImportDownloadFault;
import au.edu.unsw.sltf.services.ImportMarketDataDocument;
import au.edu.unsw.sltf.services.ImportMarketDataDocument.ImportMarketData;
import au.edu.unsw.sltf.services.ImportMarketDataResponseDocument;
import au.edu.unsw.sltf.services.ImportMarketDataResponseDocument.ImportMarketDataResponse;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataDocument.SummaryMarketData;
import au.edu.unsw.sltf.services.SummaryMarketDataFaultDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataFaultDocument.SummaryMarketDataFault;
import au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument;
import au.edu.unsw.sltf.services.SummaryMarketDataResponseDocument.SummaryMarketDataResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web service client that connects to the ImportDownloadServices
 * Web service.
 */
public class WebServiceClient {
	
	private String sec;
	private Calendar startDate;
	private Calendar endDate;
	private String url;
	private String wsURLImport = "http://localhost:8080/axis2/services/ImportDownloadServices";
	private String wsURLSummary = "http://localhost:8080/axis2/services/SummaryMarketDataService";
	private String wsURLCurrency = "http://localhost:8080/axis2/services/CurrencyConvertMarketData";	
	private String eventSetID;
	private String targetCurrency;
	private ImportDownloadServicesStub importDownloadStub;
	private SummaryMarketDataServiceStub summaryMarketStub;
	private CurrencyConvertMarketDataStub currencyConvertStub;
	
	public WebServiceClient(){};
	
	public WebServiceClient(String sec, Calendar startDate, Calendar endDate, String url){
		this.sec = sec;
		this.startDate = startDate;
		this.endDate = endDate;
		this.url = url;
		
		try{
			this.importDownloadStub = new ImportDownloadServicesStub(wsURLImport);
		} catch (Exception ex){
            ex.printStackTrace();

		}
		
	}
	
	public WebServiceClient(String eventSetID, String service){
		this.eventSetID = eventSetID;
		
		// If ImportDownload service called..
		if (service.equals("import")){
			try{
				this.importDownloadStub = new ImportDownloadServicesStub(wsURLImport);
			} catch (Exception ex){
	            ex.printStackTrace();	
			}
		}
		// If summaryMarketData service called..
		else if (service.equals("summary")){
			try{
				this.summaryMarketStub = new SummaryMarketDataServiceStub(wsURLSummary);
			} catch (Exception ex){
	            ex.printStackTrace();	
			}
		}		
		
	}
	
	public static WebServiceClient currencyClient(String eventSetID, String currency){
		WebServiceClient client = new WebServiceClient();
		client.eventSetID = eventSetID;
		client.targetCurrency = currency;
		try {
			client.currencyConvertStub = new CurrencyConvertMarketDataStub(client.wsURLCurrency);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
		
	}
	
   public String callImportMarketDataOperation() throws Exception {
	   
	   
        // Ready the request for rdthImport operation.
        ImportMarketDataDocument reqDoc = ImportMarketDataDocument.Factory.newInstance();
        ImportMarketData req = reqDoc.addNewImportMarketData();
        
      
        
        System.out.println("start date = " + this.startDate);
        System.out.println("end date = " + this.endDate);
        
        
        req.setStartDate(this.startDate);
        
        req.setEndDate(this.endDate);
        
        req.setSec(this.sec);
      
        req.setDataSourceURL(this.url);
        
       
        System.out.println(req.getSec());
        System.out.println(req.getDataSourceURL());
        System.out.println(req.getStartDate());
        System.out.println(req.getEndDate());
        
        
        // Use the stub (from generated code) to make the call.
        
        try{
        ImportMarketDataResponseDocument respDoc =  this.importDownloadStub.importMarketData(reqDoc);
        ImportMarketDataResponse resp = respDoc.getImportMarketDataResponse();
        
        String result = resp.getEventSetId();         
        return result;
        
        }catch(ImportDownloadFaultException e){
        	ImportDownloadFaultDocument faultDoc = ImportDownloadFaultDocument.Factory.newInstance();
        	ImportDownloadFault idf = ImportDownloadFault.Factory.newInstance();
        	faultDoc = e.getFaultMessage();
        	idf = faultDoc.getImportDownloadFault();
        	
        	String result = idf.getFaultMessage().toString();
        	       
        	return result;
        	
        }
       
        
    }
   
   
    public String callDownloadFileOperation() throws Exception{
        // Ready the request for downloadFile operation.
        DownloadFileDocument reqDoc = DownloadFileDocument.Factory.newInstance();
        DownloadFile req = reqDoc.addNewDownloadFile();
        
        req.setEventSetId(this.eventSetID);  
        
        // Use the stub (from generated code) to make the call.
        try{
        DownloadFileResponseDocument respDoc = this.importDownloadStub.downloadFile(reqDoc);
        DownloadFileResponse resp = respDoc.getDownloadFileResponse();
        
        String result = resp.getDataURL();
        return result;
        
        }
        catch (ImportDownloadFaultException e){
        	ImportDownloadFaultDocument faultDoc = ImportDownloadFaultDocument.Factory.newInstance();
        	ImportDownloadFault idf = ImportDownloadFault.Factory.newInstance();
        	
        	faultDoc = e.getFaultMessage();
        	idf = faultDoc.getImportDownloadFault();
        	
        	String result = idf.getFaultMessage();
        	return result;
        }        
    }
    
    public String callSummaryMarketDataOperation() throws Exception{
    	SummaryMarketDataDocument reqDoc = SummaryMarketDataDocument.Factory.newInstance();
    	SummaryMarketData req = reqDoc.addNewSummaryMarketData();
    	
    	req.setEventSetId(this.eventSetID);
    	
    	// Use the stub (from generated code) to make the call.
    	try{
    		SummaryMarketDataResponseDocument respDoc = this.summaryMarketStub.summaryMarketData(reqDoc);
    		SummaryMarketDataResponse resp = respDoc.getSummaryMarketDataResponse();
    		
    		String result = resp.getSec() + "|" + resp.getStartDate() + "|" + resp.getEndDate() + "|" + resp.getMarketType() + "|" + resp.getCurrencyCode() + "|" + resp.getFileSize();
    		return result;
    	}catch (SummaryMarketDataFaultException e){
    		SummaryMarketDataFaultDocument faultDoc = SummaryMarketDataFaultDocument.Factory.newInstance();
    		SummaryMarketDataFault smdf = SummaryMarketDataFault.Factory.newInstance();
    		
    		faultDoc = e.getFaultMessage();
    		smdf = faultDoc.getSummaryMarketDataFault();
    		
    		String result = smdf.getFaultMessage();
    		return result;
    	}
    	
    }
    
    public String callCurrencyConvertMarketDataOperation() throws Exception{
    	
    	CurrencyConvertMarketDataDocument reqDoc =  CurrencyConvertMarketDataDocument.Factory.newInstance();
    	CurrencyConvertMarketData req = reqDoc.addNewCurrencyConvertMarketData();
    	
    	req.setEventSetId(this.eventSetID);
    	req.setTargetCurrency(this.targetCurrency);
    	
    	// Use the stub (from generated code) to make the call.
    	try{
    		CurrencyConvertMarketDataResponseDocument respDoc = this.currencyConvertStub.currencyConvertMarketData(reqDoc);
    		CurrencyConvertMarketDataResponse resp = respDoc.getCurrencyConvertMarketDataResponse();
    		
    		String result = resp.getEventSetId();
    		return result;
    		
    	}catch (CurrencyConvertMarketDataFaultException e){
    		
    		CurrencyConvertMarketDataFaultDocument faultDoc = CurrencyConvertMarketDataFaultDocument.Factory.newInstance();
    		CurrencyConvertMarketDataFault ccmdf = CurrencyConvertMarketDataFault.Factory.newInstance();
    		
    		faultDoc = e.getFaultMessage();
    		ccmdf = faultDoc.getCurrencyConvertMarketDataFault();
    		
    		String result = ccmdf.getFaultMessage();
    		return result;
    		
    	}
    }
    
    
}


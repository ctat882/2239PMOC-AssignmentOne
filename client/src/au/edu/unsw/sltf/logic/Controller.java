package au.edu.unsw.sltf.logic;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.sltf.logic.WebServiceClient;
import au.edu.unsw.sltf.client.InputValidator;
import au.edu.unsw.sltf.client.SummaryMarketDataFaultException;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub        
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
	}
	
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebServiceClient client;
		
		String action = request.getParameter("action");
		String forwardPage;
		
		// If Import Market Data Service used..
		if (action.equals("import")){
			String sec = request.getParameter("sec").trim();
			
							/****	   Get Start Date		*****/
			String startDatepicker = request.getParameter("startDatepicker");
		
			Pattern p = Pattern.compile("\\s*([0-9]{2})/([0-9]{2})/([0-9]{4})\\s*");
			Matcher m = p.matcher(startDatepicker);
			
			
			
			String startYear = "";
			String startMonth = "";
			String startDay = "";
			
			while (m.find()){			
				 startYear = m.group(3);
				 startMonth = m.group(1);
				 startDay = Integer.toString(Integer.parseInt(m.group(2)));
			}
			String startHour = request.getParameter("startHour");
			String startMin = request.getParameter("startMin");
			String startSec = request.getParameter("startSec");			
			String startMillSec = request.getParameter("startMillSec");
			
			
			
			Calendar startDate = Calendar.getInstance();
			try{
			startDate.set(Calendar.YEAR, Integer.parseInt(startYear));
			startDate.set(Calendar.MONTH, Integer.parseInt(startMonth)-1);
			startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDay));
			startDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
			startDate.set(Calendar.MINUTE, Integer.parseInt(startMin));
			startDate.set(Calendar.SECOND, Integer.parseInt(startSec));
			startDate.set(Calendar.MILLISECOND, Integer.parseInt(startMillSec));
			}
			catch(Exception e){
				startDate.set(Calendar.YEAR, 9999);
			}
			
			
			
							/****   Get End Date	****/
			
			String endDatepicker = request.getParameter("endDatepicker");
			
			String endYear = "";
			String endMonth = "";
			String endDay = "";
			
			m.reset(endDatepicker);
			
			while (m.find()){
				 endYear = m.group(3);
				 endMonth = m.group(1);
				 endDay = Integer.toString(Integer.parseInt(m.group(2)));
			}			
			String endHour = request.getParameter("endHour");
			String endMin = request.getParameter("endMin");
			String endSec = request.getParameter("endSec");
			String endMillSec = request.getParameter("endMillSec");
			
			
			Calendar endDate = Calendar.getInstance();
			try{
			endDate.set(Calendar.YEAR, Integer.parseInt(endYear));
			endDate.set(Calendar.MONTH, Integer.parseInt(endMonth)-1);
			endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDay));
			endDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
			endDate.set(Calendar.MINUTE, Integer.parseInt(endMin));
			endDate.set(Calendar.SECOND, Integer.parseInt(endSec));
			endDate.set(Calendar.MILLISECOND, Integer.parseInt(endMillSec));
			}catch(Exception e){
				endDate.set(Calendar.YEAR, 0000);
			}
			
						
			String url = request.getParameter("url");
			
		
			
			client = new WebServiceClient(sec, startDate, endDate, url);
			
			try{
				String result = client.callImportMarketDataOperation();	
				System.out.println("RESULT = " + result);
				request.setAttribute("eventSetID", result);
				forwardPage = "importMarketData.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
				dispatcher.forward(request, response);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			
		}
		// If "Download File" service used...
		else if(action.equals("download")){
			
			String eventSetID = request.getParameter("eventSetID").trim();
			
			client = new WebServiceClient(eventSetID, "import");
			
			try{
				String result = client.callDownloadFileOperation();
				System.out.println("RESULKT = " +result);
				
				Pattern p = Pattern.compile("^http.*");
				Matcher m = p.matcher(result);
				
				if (m.matches()){
					request.setAttribute("dataURL", result);
				}
				else {
					request.setAttribute("errorDownloadFile", result);
				}
				forwardPage = "downloadFile.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
				dispatcher.forward(request, response);
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		// If "Summary Market Data" service used...
		else if(action.equals("summary")){
			String eventSetID = request.getParameter("eventSetID").trim();
			
			client = new WebServiceClient(eventSetID, "summary");
			
			try{
				String result = client.callSummaryMarketDataOperation();
				System.out.println("RESULKT = " +result);
				
				Pattern p = Pattern.compile("(.+)\\|(.+)\\|(.+)\\|(.+)\\|(.+)\\|(.+)");
				Matcher m = p.matcher(result);
				
				if (m.matches()){	
					request.setAttribute("eventSetIDSummary", eventSetID);
					request.setAttribute("sec", m.group(1));
					request.setAttribute("startDate", m.group(2));
					request.setAttribute("endDate", m.group(3));
					request.setAttribute("marketType", m.group(4));
					request.setAttribute("currencyCode", m.group(5));
					request.setAttribute("fileSize", m.group(6));
					
					System.out.println("sec = " + m.group(1));
				}
				else{
					request.setAttribute("errorSummaryMarket", result);
				}
				
				forwardPage = "summaryMarket.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
				dispatcher.forward(request, response);
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		// If "Currency Convert Market Data" service used...
		else if(action.equals("convert")){
			String eventSetID = request.getParameter("eventSetID").trim();
			String targetCurrency = request.getParameter("targetCurrency").trim();
			
			System.out.println("even  = " +eventSetID);
			System.out.println("targetCurrency  = " +targetCurrency);
			
			client = WebServiceClient.currencyClient(eventSetID, targetCurrency);
			
			try{
				String result = client.callCurrencyConvertMarketDataOperation();
				System.out.println("RESULKT = " +result);
				
				request.setAttribute("eventSetIDCurrency", result);
				
				forwardPage = "currencyMarketData.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
				dispatcher.forward(request, response);
			}catch (Exception e){
				e.printStackTrace();
			}
			
		}
	}

}

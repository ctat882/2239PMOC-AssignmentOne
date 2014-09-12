<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="services.css"> 
<title>Summary Market Data</title>
</head>
<body>
	<div id="wrap">
   		<ul id="nav">
   			<li><a href="welcome.jsp">Home</a></li>
      		<li><a href="importMarketData.jsp">Import Market Data</a></li>
      		<li><a href="downloadFile.jsp">Download File</a></li>
      		<li><a href="summaryMarket.jsp">Summary Market Data</a></li>
    	  	<li><a href="currencyMarketData.jsp">Currency Conversion</a></li>      
	   </ul> 
	</div>


	<h1>Summary Market Data</h1>
	<h2>Description</h2>
	
	<div class="textParagraph">
		<p>
			The <i>Summary Market Data</i> operation takes the Market Data File corresponding to the given <i>Event Set ID</i> and summarizes the contents of the file.
		</p>		
	</div>
	<h2>Input Parameters</h2>
	<div class="textParagraph">
		 <p>
			<b>Event Set ID:</b> A unique ID that references a Market Data File
		</p>	
	</div>
	<h2>Output Parameters</h2>
	<div class="textParagraph">
		<p>
			<b>Security:</b> The security code of the financial instrument (i.e., stock)
		</p>
		<p> 
			<b>Start Date:</b> The date from which information about the financial instrument begins
		</p>
		<p>
			<b>End Date:</b> The date at which information about the financial instrument ends
		</p>
		<p>
			<b>Market Type:</b> There are two types: "Quote" and "Trade". "Quotes" usually have Bid Price, Bid Size, Ask Price and Ask Size as associated data. "Trade" will usually have Price and Volume as associated data.
		</p>
		<p>	
			<b>Currency Code:</b> The currency denomination
		</p>
		<p> 
			<b>File Size:</b> The size of the file.			
		</p>
	</div>
	
	<h2><a href="summaryMarketErrorCodes.jsp">Error Codes</a> <a style="padding-left: 40px;"href="http://localhost:8080/axis2/services/SummaryMarketDataService?wsdl">WSDL</a></h2>
	
	<h2>Example Input</h2>
	
	<div class="textParagraph">
		<p>
			Please copy the output of the "Import Market Data Operation" below
		</p>
	</div>
	
	<form name="input" action='Controller' method='post'>
		<table>								
			<tr>
				<td>Event Set ID: <input type="text" name="eventSetID" /> </td>
			</tr>
		</table>
		<div class="buttonHolder">
			<input type="submit" name="test" value="Test!">
			<input type="hidden" name="action" value="summary">
		</div>
	</form>
	
	<c:choose>
	  <c:when test="${not empty errorSummaryMarket}">
	    <h2>Result</h2>
	    <p>${errorSummaryMarket}</p>
	  </c:when>
	  <c:when test="${not empty sec}">
	    <h2>Result</h2>
	    <p><b>Event Set ID: </b>${eventSetIDSummary}</p>
	   	<p><b>Security: </b>${sec}</p>
	   	<p><b>Start Date: </b>${startDate}</p>
	   	<p><b>End Date: </b>${endDate}</p>
	   	<p><b>Market Type: </b>${marketType}</p>
	   	<p><b>Currency Code: </b>${currencyCode}</p>
	   	<p><b>File Size: </b>${fileSize}</p>      
	  </c:when>
	</c:choose>

</body>
</html>
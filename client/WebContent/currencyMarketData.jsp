<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="services.css"> 
<title>Currency Convert Market Data</title>
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

<h1>Currency Convert Market Data</h1>
	<h2>Description</h2>
	
	<div class="textParagraph">
		<p>
			The <i>Currency Convert Market Data</i> operation converts all price information in a given Market Data file to another currency
		</p>		
	</div>
	<h2>Input Parameters</h2>
	<div class="textParagraph">
		 <p>
			<b>Event Set ID:</b> A unique ID that references the Market Data file to be altered
		</p>
		<p>
			<b>Target Currency:</b> The currency to which the price information in the Market Data file should be converted.					
		</p>	
	</div>
	<h2>Output Parameters</h2>
	<div class="textParagraph">
		<p>
			<b>Event Set ID:</b> A unique ID that references the newly created file containing all the same information as the original Market Data file but with the currency denomination changed.
		</p>
	</div>
	
	<h2><a href="currencyConvertMarketDataErrorCodes.jsp">Error Codes</a> <a style="padding-left: 40px;"href="http://localhost:8080/axis2/services/CurrencyConvertMarketData?wsdl">WSDL</a></h2>
	
	<h2>Example Input</h2>
	
	<div class="textParagraph">
		<p>
			<b>Event Set Id: </b>Please copy the output of the "Import Market Data Operation" below
		</p>
		<p>
			<b>Target Currency: </b>USD
		</p>
	</div>
	
	<form name="input" action='Controller' method='post'>
		<table>								
			<tr>
				<td>Event Set ID: <input type="text" name="eventSetID" /> </td>
			</tr>
			<tr>
				<td>Target Currency: <input type="text" name="targetCurrency" /> </td>
			</tr>
		</table>
		<div class="buttonHolder">
			<input type="submit" name="test" value="Test!">
			<input type="hidden" name="action" value="convert">
		</div>
	</form>
	
	<c:if test="${not empty eventSetIDCurrency}">
		<h2>Result</h2>
   		<p>${eventSetIDCurrency}</p>   
	</c:if>
	

</body>
</html>
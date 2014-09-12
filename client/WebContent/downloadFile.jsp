<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="services.css"> 
<title>Download File</title>
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

<h1>Download File</h1>
	<h2>Description</h2>
	
	<div class="textParagraph">
		<p>
			The <i>Download File</i> operation makes the Market Data file (output of <i>Import Market Data</i> operation) available for download over the internet.
		</p>		
	</div>
	
	<h2>Input Parameters</h2>
	<div class="textParagraph">
		 <p>
			<b>Event Set ID:</b> A unique ID that references the newly created file according to the parameters specified in the "Import Market Data" operation.
		</p>	
	</div>
	<h2>Output Parameters</h2>
	<div class="textParagraph">
		<p>
			<b>Data URL:</b> This is a http:// URL that points to the Market Data file referenced by the eventSetId input parameter. A user can download the Market Data file contents using this URL (e.g. by navigating to this URL using a Web browser).
		</p>
	</div>
	
	<h2><a href="downloadFileErrorCodes.jsp">Error Codes</a> <a style="padding-left: 40px;"href="http://localhost:8080/axis2/services/ImportDownloadServices?wsdl">WSDL</a></h2>
	
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
			<input type="hidden" name="action" value="download">
		</div>
	</form>

	<c:choose>
	  <c:when test="${not empty dataURL}">
	    <h2>Result</h2>
	    <p><a href="${dataURL}">${dataURL}</a></p>
	  </c:when>
	  <c:when test="${not empty errorDownloadFile}">
	    <h2>Result</h2>
	   	<p>${errorDownloadFile}</p> 
	  </c:when>
	</c:choose>

</body>
</html>
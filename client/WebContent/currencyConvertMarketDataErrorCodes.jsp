<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="error.css"> 
<title>Currency Convert Error Codes</title>
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

	<h1>Currency Convert Error Codes</h1>

	<h3>"Could not locate target currency in currency table"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the specified currency does not appear in our currency table.
		</p>		
	</div>
	
	<h3>"Bad Event Set ID"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the Event Set ID does not exist.
		</p>		
	</div>
	
	<h3>"Cannot convert to AUD, only from AUD"</h3>
	<div class="textParagraph">		
		<p>
			The current Market Data file is already denominated in $A.
		</p>		
	</div>
	
	<h3>"The file already contains converted prices"</h3>
	<div class="textParagraph">		
		<p>
			The prices in the Market Data file have are already listed in the currency you specified.
		</p>		
	</div>
	<h3>"Bad or Missing comment line in CSV"</h3>
	<div class="textParagraph">		
		<p>
			This error is thrown if the Market Data File does not have a comment in the first line.
		</p>		
	</div>

</body>
</html>
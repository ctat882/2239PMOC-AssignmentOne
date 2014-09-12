<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="error.css"> 
<title>Import Market Data Error Codes</title>
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
<h1>Import Market Data Error Codes</h1>

<h3>"Invalid Security Code (SEC). Code must be 3-4 letters"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the security contains anything other than 3-4 Upper Case Letters.
		</p>
		<p>
			<i>Valid Input: </i><br>"ABCD", "BHP"    
		</p>
		<p>
			<i>Invalid Input: </i><br>"AB", "12"
		</p>
	</div>
	
	<h3>"Invalid Dates"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if: 			
		</p>
		<ul>
			<li style="text-align:left;">The dates are NOT in the format: MM/DD/YYYY</li> 
			<li style="text-align:left;">The Start Date is after the End Date</li> 
		</ul>
		<p>
			<i>Valid Input: </i><br>Start Date: "06/26/2001"     <br>End Date: 07/27/2001"
		</p>
		<p>
			<i>Invalid Input: </i><br>Start Date: "2001/06/26"     <br>End Date: 2001/07/27"
		</p>
	</div>
	
	<h3>"Incorrect URL"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the URL does not point to a downloadable Market File.			
		</p>
	</div>
	
	<h3>"Error downloading the dataSource CSV file"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the specified CSV file could not be downloaded. <br>			
		</p>
	</div>
	
		<h3>"Missing Security Code / URL"</h3>
	<div class="textParagraph">		
		<p>
			This error will be thrown if the Security Code or URL Fields are empty.<br>			
		</p>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Test our Services!</title>	
	<link rel="stylesheet" href="welcome.css"> 
</head>


<body>

	<h1>COMP9321 Assignment 1: Web Services</h1>
	

	<div id="wrap">

   		<ul id="nav">
   			<li><a href="welcome.jsp">Home</a></li>
      		<li><a href="importMarketData.jsp">Import Market Data</a></li>
      		<li><a href="downloadFile.jsp">Download File</a></li>
      		<li><a href="summaryMarket.jsp">Summary Market Data</a></li>
    	  	<li><a href="currencyMarketData.jsp">Currency Conversion</a></li>      
	   </ul> 
	</div>

	<h2>Overview</h2>

	<div class="textParagraph">
		<p>
			The Web Services listed on this site allow users to retrieve, alter and summarize information contained with a "Market Data File". 
			A Market Data File contains data about financial instruments. 
		</p>
		<p>
			Please click on the links above for more information about our services and have a trial run.
		</p>
	</div>
	

	
</body>
</html>
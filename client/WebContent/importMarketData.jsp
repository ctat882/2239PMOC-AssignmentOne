<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="services.css"> 
	<link rel="stylesheet" href="/resources/demos/style.css"> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script src="welcome.js"></script>
<title>Import Market Data</title>
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

<h1>Import Market Data</h1>
	<h2>Description</h2>
	<div class="textParagraph">
		<p>
			The <i>Import Market Data</i> operation reads a Market Data file that exists outside of the system, 
			filters the content according to the given parameters,
			and produces another Market Data file to store it inside the system.
			The filter conditions are specified by the input parameters.
		</p>
	</div>
	<h2>Input Parameters</h2>
	<div class="textParagraph">
		<p>
			<b>Security:</b> The security code of the financial instrument (i.e., stock)
		</p>
		<p>
			<b>Start Date:</b> The date from which to begin retrieving information regarding the financial instrument. 
		</p>
		<p>
			<b>End Date:</b> The date up until which information regarding the financial instrument is retrieved.
		</p>
		<p>
			<b>Data Source URL:</b> The URL that points to the input Market Data File
		</p>
		<p>	
			<b>NOTE:</b> Dates must be entered using the following format: <i>MM/DD/YYYY</i> 
		</p>
	</div>
	<h2>Output Parameters</h2>
	<div class="textParagraph">
		<p>
			<b>Event Set ID:</b> A unique ID that references the newly created file according to the parameters specified above.
		</p>
	</div>	
	
	<h2><a href="importMarketDataErrorCodes.jsp">Error Codes</a> <a style="padding-left: 40px;"href="http://localhost:8080/axis2/services/ImportDownloadServices?wsdl">WSDL</a></h2>
		
	<h2>Example Input</h2>
	
	<div class="textParagraph">
		<p> 
			<b>Security:</b> ABCD 
		</p>
		<p>
			<b>Start Date:</b> 06/26/2001
		</p>
		<p>
			<b>End Date:</b> 07/27/2001
		</p>
		<p>
			<b>Data Source URL:</b> http://www.cse.unsw.edu.au/~hpaik/9322/assignments/common/files_csv_spec/FinDataSimple.csv
		</p>
	</div>
	
	
	<form name="input" action='Controller' method='post'>
		<table>		
			<tr>
				<td>Security</td>
			</tr>			
			<tr>
				<td> <input type="text" name="sec" /> </td>
			</tr>
			
			<tr>
				<td>Start Date</td>								
				<td>Hr</td>
				<td>Min</td>
				<td>Sec</td>
				<td>ms</td>
			</tr>
			<tr>
				<td><input type="text" class="datepicker" name="startDatepicker"></td>				
				<td><select name="startHour">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>					 
					</select>
				</td>
				<td><select name="startMin">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>
					  <option value="23">23</option>
					  <option value="24">24</option>
					  <option value="25">25</option>
					  <option value="26">26</option>
					  <option value="27">27</option>
					  <option value="28">28</option>
					  <option value="29">29</option>
					  <option value="30">30</option>
					  <option value="31">31</option>
					  <option value="32">32</option>
					  <option value="33">33</option>
					  <option value="34">34</option>
					  <option value="35">35</option>
					  <option value="36">36</option>
					  <option value="37">37</option>
					  <option value="38">38</option>
					  <option value="39">39</option>
					  <option value="40">40</option>
					  <option value="41">41</option>
					  <option value="42">42</option>
					  <option value="43">43</option>
					  <option value="44">44</option>
					  <option value="45">45</option>
					  <option value="46">46</option>
					  <option value="47">47</option>
					  <option value="48">48</option>
					  <option value="49">49</option>
					  <option value="50">50</option>
					  <option value="51">51</option>
					  <option value="52">52</option>
					  <option value="53">53</option>
					  <option value="54">54</option>
					  <option value="55">55</option>
					  <option value="56">56</option>
					  <option value="57">57</option>
					  <option value="58">58</option>
					  <option value="59">59</option>						 
					</select>
				</td>				
				<td><select name="startSec">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>
					  <option value="23">23</option>
					  <option value="24">24</option>
					  <option value="25">25</option>
					  <option value="26">26</option>
					  <option value="27">27</option>
					  <option value="28">28</option>
					  <option value="29">29</option>
					  <option value="30">30</option>
					  <option value="31">31</option>
					  <option value="32">32</option>
					  <option value="33">33</option>
					  <option value="34">34</option>
					  <option value="35">35</option>
					  <option value="36">36</option>
					  <option value="37">37</option>
					  <option value="38">38</option>
					  <option value="39">39</option>
					  <option value="40">40</option>
					  <option value="41">41</option>
					  <option value="42">42</option>
					  <option value="43">43</option>
					  <option value="44">44</option>
					  <option value="45">45</option>
					  <option value="46">46</option>
					  <option value="47">47</option>
					  <option value="48">48</option>
					  <option value="49">49</option>
					  <option value="50">50</option>
					  <option value="51">51</option>
					  <option value="52">52</option>
					  <option value="53">53</option>
					  <option value="54">54</option>
					  <option value="55">55</option>
					  <option value="56">56</option>
					  <option value="57">57</option>
					  <option value="58">58</option>
					  <option value="59">59</option>						 
					</select>
				</td>
				<td> <input type="text" value="000" name="startMillSec" class="millisec" maxlength="3" /> </td>
			</tr>
			
			
			
			<tr>	
				<td>End Date</td>				
				<td>Hr</td>
				<td>Min</td>
				<td>Sec</td>
				<td>ms</td>
			</tr>	
			<tr>
				<td><input type="text" class="datepicker" name="endDatepicker"></td>			
				<td><select name="endHour">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>					 
					</select>
				</td>
				<td><select name="endMin">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>
					  <option value="23">23</option>
					  <option value="24">24</option>
					  <option value="25">25</option>
					  <option value="26">26</option>
					  <option value="27">27</option>
					  <option value="28">28</option>
					  <option value="29">29</option>
					  <option value="30">30</option>
					  <option value="31">31</option>
					  <option value="32">32</option>
					  <option value="33">33</option>
					  <option value="34">34</option>
					  <option value="35">35</option>
					  <option value="36">36</option>
					  <option value="37">37</option>
					  <option value="38">38</option>
					  <option value="39">39</option>
					  <option value="40">40</option>
					  <option value="41">41</option>
					  <option value="42">42</option>
					  <option value="43">43</option>
					  <option value="44">44</option>
					  <option value="45">45</option>
					  <option value="46">46</option>
					  <option value="47">47</option>
					  <option value="48">48</option>
					  <option value="49">49</option>
					  <option value="50">50</option>
					  <option value="51">51</option>
					  <option value="52">52</option>
					  <option value="53">53</option>
					  <option value="54">54</option>
					  <option value="55">55</option>
					  <option value="56">56</option>
					  <option value="57">57</option>
					  <option value="58">58</option>
					  <option value="59">59</option>						 
					</select>
				</td>				
				<td><select name="endSec">
					  <option value="00">00</option>
					  <option value="01">01</option>
					  <option value="02">02</option>
					  <option value="03">03</option>
					  <option value="04">04</option>
					  <option value="05">05</option>
					  <option value="06">06</option>
					  <option value="07">07</option>
					  <option value="08">08</option>
					  <option value="09">09</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="13">13</option>
					  <option value="14">14</option>
					  <option value="15">15</option>
					  <option value="16">16</option>
					  <option value="17">17</option>
					  <option value="18">18</option>
					  <option value="19">19</option>
					  <option value="20">20</option>
					  <option value="21">21</option>
					  <option value="22">22</option>
					  <option value="23">23</option>
					  <option value="23">23</option>
					  <option value="24">24</option>
					  <option value="25">25</option>
					  <option value="26">26</option>
					  <option value="27">27</option>
					  <option value="28">28</option>
					  <option value="29">29</option>
					  <option value="30">30</option>
					  <option value="31">31</option>
					  <option value="32">32</option>
					  <option value="33">33</option>
					  <option value="34">34</option>
					  <option value="35">35</option>
					  <option value="36">36</option>
					  <option value="37">37</option>
					  <option value="38">38</option>
					  <option value="39">39</option>
					  <option value="40">40</option>
					  <option value="41">41</option>
					  <option value="42">42</option>
					  <option value="43">43</option>
					  <option value="44">44</option>
					  <option value="45">45</option>
					  <option value="46">46</option>
					  <option value="47">47</option>
					  <option value="48">48</option>
					  <option value="49">49</option>
					  <option value="50">50</option>
					  <option value="51">51</option>
					  <option value="52">52</option>
					  <option value="53">53</option>
					  <option value="54">54</option>
					  <option value="55">55</option>
					  <option value="56">56</option>
					  <option value="57">57</option>
					  <option value="58">58</option>
					  <option value="59">59</option>						 
					</select>
				</td>
				<td> <input type="text" value="000" name="endMillSec" class="millisec" maxlength="3" /> </td>
				
			
			<tr>
				<td>Data Source URL</td>		
			</tr>				
			<tr>
				<td> <input type="text" name="url" /> </td>
			</tr>
						
		</table>
		<div class="buttonHolder">
				<input type="submit" name="test" value="Test!">
				<input type="hidden" name="action" value="import">
		</div>
	</form>
	
	<c:if test="${not empty eventSetID}">
		<h2>Result</h2>
   		<p>${eventSetID}</p>   
	</c:if>


</body>
</html>
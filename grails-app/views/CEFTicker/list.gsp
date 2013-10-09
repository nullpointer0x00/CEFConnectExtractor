<html>
	<head>
		<meta name="layout" content="main">
	</head>
	<body>
		<g:javascript library="datatables" src="jquery.dataTables.js"/>
		<g:javascript>
			$('#dataTablesList').dataTable( {
        		"bProcessing": true,
        		"bServerSide": true,
        		"sPaginationType": "two_button",
        		"sAjaxSource": "/CEFConnectExtractor/CEFTicker/tickerData",
        		"oLanguage": {
         		"sLengthMenu": 'Display '+ '<select><option value="10">10</option>' + 
         									'<option value="50">50</option>' +
           									'<option value="100">100</option>'+
           									'<option value="250">250</option>'+
           									'<option value="500">500</option>'+
           									'<option value="-1">All</option>'+
           									'</select> records'
       			}
    		} );
		</g:javascript>
    	<g:remoteLink action="refreshTickers" update="tickerList">Refresh Tickers</g:remoteLink>
    	<g:link action="downloadExcelDocument">Download Excel</g:link>
		
		<table id="dataTablesList">
		<thead>
			<tr role="row">
				<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Ticker: blah" style="width: 100px;">Ticker</th>
				<th role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="Current: " style="width: 100px;">Current</th>
				<th role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="6 Month: " style="width: 100px;">6 Months</th>
				<th role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="1 Year: " style="width: 100px;">1 Year</th>
				<th role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="3 Year: " style="width: 100px;">3 Year</th>
				<th role="columnheader" tabindex="0" aria-controls="example" rowspan="1" colspan="1" aria-label="5 Year: " style="width: 100px;">5 Year</th>
			</tr>
			
		</thead>
		
		</table>
	</body>
</html>
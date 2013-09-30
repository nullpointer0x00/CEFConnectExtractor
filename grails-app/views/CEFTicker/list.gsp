<html>
	<head>
		<meta name="layout" content="main">
	</head>
	<body>
    	<g:remoteLink action="refreshTickers" update="tickerList">Refresh Tickers</g:remoteLink>
    	<g:remoteLink action="downloadExcelDocument">Download</g:remoteLink>
		<div id="tickerList">
		</div>
	</body>
</html>
package org.beatnikstree.cefconnect

import groovy.json.JsonSlurper

import org.beatnikstree.cefconnect.domain.CEFTicker;
import org.beatnikstree.cefconnect.util.StringUtil;


/***
 * 
 * NOTE: CEFConnect URL with full parameter list
 * http://www.cefconnect.com/Resources/Funds/?props=Ticker,Name,DistributionRateNAV,LastUpdated,Discount,DistributionRatePrice,ReturnOnNAV,CategoryId,CategoryName,IsManagedDistribution,Price,PriceChange,NAV,NAVPublished,Cusip
 * 
 */
class CEFTickerExtractor {
	
	def List<CEFTicker> tickers = new ArrayList<CEFTicker>();
	
	String jsonTickersUrl = "http://www.cefconnect.com/Resources/Funds/?props=Ticker"
	def htmlParser
	def json
	
	public init(){
		def urlObj = new URL(jsonTickersUrl)
		def urlConn = urlObj.openConnection()
		def jsonString = StringUtil.getStringFromInputStream(urlConn.getInputStream())
		json = new JsonSlurper().parseText(jsonString) 
		populateTickers()
	}
	
	private void populateTickers(){
		def valueExtractor = new CEFTickerValueExtractor()
		json.each {
			def ticker = new CEFTicker()
			ticker.ticker = it.Ticker
			valueExtractor.ticker = ticker
			valueExtractor.init()
			valueExtractor.extract()
			valueExtractor.ticker.save()
		}
//		for(int i = 0; i < 4 ; i++ ){
//			def ticker = new CEFTicker()
//			ticker.ticker = json.get(i).Ticker
//			valueExtractor.ticker = ticker
//			valueExtractor.init()
//			valueExtractor.extract()
//			valueExtractor.ticker.save()
//		}
	}
	
}

package org.beatnikstree.cefconnect

import groovy.json.JsonSlurper

import org.beatnikstree.cefconnect.domain.CEFTicker;
import org.beatnikstree.cefconnect.util.StringUtil;


/***
 * 
 * 
 * NOTE: CEFConnect URL with full parameter list
 * http://www.cefconnect.com/Resources/Funds/?props=Ticker,Name,DistributionRateNAV,LastUpdated,Discount,DistributionRatePrice,ReturnOnNAV,CategoryId,CategoryName,IsManagedDistribution,Price,PriceChange,NAV,NAVPublished,Cusip
 * 
 */
class CEFTickerExtractor {
	
	def List<CEFTicker> tickers = new ArrayList<CEFTicker>();
	String jsonTickersUrl = "http://www.cefconnect.com/Resources/Funds/?props=Ticker"
	def json
	
	public init(){
		def urlObj = new URL(jsonTickersUrl)
		def urlConn = urlObj.openConnection()
		def jsonString = StringUtil.getStringFromInputStream(urlConn.getInputStream())
		json = new JsonSlurper().parseText(jsonString) 
		populateTickers()
	}
	
	private void populateTickers(){
		json.each {
			def ticker = new CEFTicker()
			ticker.ticker = it.Ticker
			def valueExtractor = new CEFTickerValueExtractor()
			valueExtractor.ticker = ticker
			valueExtractor.init()
			valueExtractor.extract()
			this.tickers.add(valueExtractor.ticker)
		}
	}
	
	public static void main(String args){
		
	}
}

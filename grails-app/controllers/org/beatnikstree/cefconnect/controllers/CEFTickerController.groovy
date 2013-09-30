package org.beatnikstree.cefconnect.controllers

import org.beatnikstree.cefconnect.CEFTickerExtractor;
import org.beatnikstree.cefconnect.domain.CEFTicker

class CEFTickerController {
	
	List tickerList = null;
	
	def index() {
		redirect (action: 'list')
	}
	
	def list() {
		
	} 
	
	def clear(){
		tickerList = null
	}
	
	def refeshTickers() {
		clear()
		def extractor = new CEFTickerExtractor()
		extractor.init()
		tickerList = extractor.tickers 
		writeTable()
	}
	
	private writeTable() {
		def html = ""
		if(tickerList != null){
		html = "<table>"
		for(def i = 0; i < tickerList.size(); i++){
			html += "<tr>"
			html += "<td>" + tickerList.get(i).ticker + "</td>"
			html += "<td>" + tickerList.get(i).currentPremium + "</td>"
			html += "<td>" + tickerList.get(i).sixMonthPremium + "</td>"
			html += "<td>" + tickerList.get(i).oneYearPremium + "</td>"
			html += "<td>" + tickerList.get(i).threeYearPremium + "</td>"
			html += "<td>" + tickerList.get(i).fiveYearPremium + "</td>"
			html += "</tr>"
		}
		html += "</table>"
		render html
		}
	}
}

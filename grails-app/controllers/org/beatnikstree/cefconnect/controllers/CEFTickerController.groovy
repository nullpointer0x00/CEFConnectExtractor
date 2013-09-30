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
	
	private test() {
		def html = ""
		if(tickerList != null){
		html = "<table>"
		for(def i = 0; i < list.size(); i++){
			html += "<tr>"
			html += "<tr>" + list.get(i).ticker + "</tr>"
			html += "<tr>" + list.get(i).currentPremium + "</tr>"
			html += "<tr>" + list.get(i).sixMonthPremium + "</tr>"
			html += "<tr>" + list.get(i).oneYearPremium + "</tr>"
			html += "<tr>" + list.get(i).threeYearPremium + "</tr>"
			html += "<tr>" + list.get(i).fiveYearPremium + "</tr>"
			html += "</tr>"
		}
		html += "</table>"
		render html
		}
	}
}

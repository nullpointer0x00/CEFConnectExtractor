package org.beatnikstree.cefconnect.controllers

import java.io.File;
import org.beatnikstree.cefconnect.CEFTickerExtractor;
import org.beatnikstree.cefconnect.CEFXlsDocumentBuilder
import org.beatnikstree.cefconnect.domain.CEFTicker

class CEFTickerController {
	
	def index() {
		redirect (action: 'list')
	}
	
	def list() {
	} 
	
	def clearAll() {
		CEFTicker.where { }.deleteAll()
	}
	
	def refreshTickers() {
		def extractor = new CEFTickerExtractor()
		extractor.init()
		extractor.tickers 		
		writeTable()
	}
	
	def downloadExcelDocument = {
		def documentBuilder = new CEFXlsDocumentBuilder()
		log.info "v1"
		documentBuilder.tickerList = CEFTicker.list()
		documentBuilder.createXlsxDocument()
		
		//TODO: write response for download
	}
	
	
	private writeTable() {
		def html = ""
		def tickerList = CEFTicker.findAll()
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

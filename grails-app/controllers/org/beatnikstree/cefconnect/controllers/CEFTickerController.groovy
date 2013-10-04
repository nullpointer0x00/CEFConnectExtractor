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
		log.info "refreshTickers"
		def extractor = new CEFTickerExtractor()
		extractor.init()
		extractor.tickers 		
	}
	
	def downloadExcelDocument() {
		log.info "download Excel Document"
		def documentBuilder = new CEFXlsDocumentBuilder()
		documentBuilder.tickerList = CEFTicker.list()
		def filePath = documentBuilder.createXlsxDocument()
		log.info "fileName: ${filePath}"
		File f = new File(filePath)
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=CEFConnect.xls")
		response.outputStream << f.newInputStream()
	}
	
	
	def tickerData() {
		def tickerList = CEFTicker.findAll()
		def json = '{ "aaData": [ '
		if(tickerList != null){
			for(def i = 0; i < tickerList.size(); i++){
				json += '["' + tickerList.get(i).ticker + '",'
				json += '"' + tickerList.get(i).currentPremium + '",'
				json += '"' + tickerList.get(i).sixMonthPremium + '",'
				json += '"' + tickerList.get(i).oneYearPremium + '",'
				json += '"' + tickerList.get(i).threeYearPremium + '",'
				json += '"' + tickerList.get(i).fiveYearPremium + '"],'
			}
			json = json.substring(0, json.length() -1)
		}
		json += "] }"
		render json
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

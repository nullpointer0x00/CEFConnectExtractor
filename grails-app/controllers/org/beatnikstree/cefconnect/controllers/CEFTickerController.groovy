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
		log.error "refreshTickers"
		def extractor = new CEFTickerExtractor()
		extractor.init()
		extractor.tickers 		
	}
	
	def downloadExcelDocument() {
		log.error "download Excel Document"
		def documentBuilder = new CEFXlsDocumentBuilder()
		documentBuilder.tickerList = CEFTicker.list()
		def filePath = documentBuilder.createXlsxDocument()
		log.error "fileName: ${filePath}"
		File f = new File(filePath)
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=CEFConnect.xls")
		response.outputStream << f.newInputStream()
	}
	
	
	def tickerData() {
		Map<String, String> map = request.getParameterMap()
		def value = map.get("sSearch")[0]
		def order = map.get("sSortDir_0")[0]
		def displayLength = map.get("iDisplayLength")[0]
		def tickerList = null
		if(value != null && !value.equals("")){
			tickerList = CEFTicker.findAllByTickerLike("%" + value.toUpperCase() + "%", [sort: "ticker", order: order, max: displayLength])
		} else {
			tickerList = CEFTicker.findAll([sort: "ticker", order: order, max: displayLength])
		}
//		for(String parameter : map.keySet()){
//			def value1 = map.get(parameter)
//			log.error "key: ${parameter} value: ${value1}"
//		}
		def json = '{ '
		if(tickerList != null){
			json += '"iTotalRecords":' + '"' + CEFTicker.findAll().size() +'",' 
			json += '"iTotalDisplayRecords":' + '"' + tickerList.size() +'",'
			json += '"aaData": [' 
			for(def i = 0; i < tickerList.size(); i++){
				json += '["' + tickerList.get(i).ticker + '",'
				json += '"' + tickerList.get(i).currentPremium + '",'
				json += '"' + tickerList.get(i).sixMonthPremium + '",'
				json += '"' + tickerList.get(i).oneYearPremium + '",'
				json += '"' + tickerList.get(i).threeYearPremium + '",'
				json += '"' + tickerList.get(i).fiveYearPremium + '"],'
			}
			if(tickerList.size() !=  0){
				json = json.substring(0, json.length() -1) + "]"
			} else {
				json += "]"
			}
		}
		json += " }"
		log.error "${json}"
		render json
	}
	
}

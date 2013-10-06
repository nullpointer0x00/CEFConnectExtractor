package org.beatnikstree.cefconnect.controllers

import groovy.util.logging.Log4j

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
		Map<String, String> map = request.getParameterMap()
		def value = map.get("sSearch")[0]
		def order = map.get("sSortDir_0")[0]
		def displayLength = map.get("iDisplayLength")[0]
		def sortColumn = map.get("iSortCol_0")[0].toInteger()
		def tickerList = null
		if(value != null && !value.equals("")){
			tickerList = CEFTicker.findAllByTickerLike("%" + value.toUpperCase() + "%", [sort: "ticker", order: order, max: displayLength])
		} else {
			if(sortColumn == 0){
				tickerList = CEFTicker.findAll([sort: "ticker", order: order, max: displayLength])
			} else if (sortColumn == 1){
				tickerList = CEFTicker.findAll([sort: "currentPremium", order: order, max: displayLength])
			} else if (sortColumn == 2){
				tickerList = CEFTicker.findAll([sort: "sixMonthPremium", order: order, max: displayLength])
			} else if (sortColumn == 3){
				tickerList = CEFTicker.findAll([sort: "oneYearPremium", order: order, max: displayLength])
			} else if (sortColumn == 4){
				tickerList = CEFTicker.findAll([sort: "threeYearPremium", order: order, max: displayLength])
			} else if (sortColumn == 5){
				tickerList = CEFTicker.findAll([sort: "fiveYearPremium", order: order, max: displayLength])
			} 
		}
		for(String parameter : map.keySet()){
			def value1 = map.get(parameter)
			log.info "key: ${parameter} value: ${value1}"
		}
		def json = '{ '
		if(tickerList != null){
			json += '"iTotalRecords":' + '"' + CEFTicker.findAll().size() +'",' 
			json += '"iTotalDisplayRecords":' + '"' + tickerList.size() +'",'
			json += '"aaData": [' 
			for(def i = 0; i < tickerList.size(); i++){
				json += '["' + tickerList.get(i).ticker + '",'
				json += '"' 
				json += (tickerList.get(i).currentPremium != null) ? tickerList.get(i).currentPremium : "" 
				json += '",'
				json += '"' 
				json += (tickerList.get(i).sixMonthPremium != null) ? tickerList.get(i).sixMonthPremium : ""
				json += '",'
				json += '"' 
				json += (tickerList.get(i).oneYearPremium != null) ? tickerList.get(i).oneYearPremium : ""
				json += '",'
				json += '"' 
				json += (tickerList.get(i).threeYearPremium != null) ? tickerList.get(i).threeYearPremium : ""
				json += '",'
				json += '"' 
				json += (tickerList.get(i).fiveYearPremium != null) ? tickerList.get(i).fiveYearPremium : ""
				json += '"],'
			}
			if(tickerList.size() !=  0){
				json = json.substring(0, json.length() -1) + "]"
			} else {
				json += "]"
			}
		}
		json += " }"
		log.info "${json}"
		render json
	}
	
}

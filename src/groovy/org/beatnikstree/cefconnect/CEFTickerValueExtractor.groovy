package org.beatnikstree.cefconnect

import org.beatnikstree.cefconnect.domain.CEFTicker
import org.ccil.cowan.tagsoup.Parser;
import groovy.util.logging.Log4j

@Grapes( @Grab('org.ccil.cowan.tagsoup:tagsoup:1.2') )
class CEFTickerValueExtractor {
	
	String BASE_URL = "http://www.cefconnect.com/Details/Summary.aspx?Ticker="
	CEFTicker ticker 
	def htmlParser 
	
	String SUMMARY_GRID_ID = 'ContentPlaceHolder1_cph_main_cph_main_SummaryGrid'
	String DISCOUNT_GRID_ID = 'ContentPlaceHolder1_cph_main_cph_main_ucPricing_DiscountGrid'
	
	public void extract(){
		def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
		def slurper = new XmlSlurper(tagsoupParser)
		def url = BASE_URL + ticker.ticker
		this.htmlParser = slurper.parse(url)
		if(htmlParser != null){
			def currentPremium =  htmlParser.'**'.find{ it.@id == SUMMARY_GRID_ID }.tr[1].td[3]
			def sixMonthPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[1].td[1]
			def oneYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[2].td[1]
			def threeYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[3].td[1]
			def fiveYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[4].td[1]
			if(currentPremium.text().replace("%", "").toDouble()){
				ticker.currentPremium = currentPremium.text().replace("%", "").toDouble()
			} else {
				ticker.currentPremium = 0.0
			}
			if(sixMonthPremium.text().replace("%", "").isDouble()){
				ticker.sixMonthPremium = sixMonthPremium.text().replace("%", "").toDouble()
			} 
			if(oneYearPremium.text().replace("%", "").isDouble()){
				ticker.oneYearPremium = oneYearPremium.text().replace("%", "").toDouble()
			} 
			if(threeYearPremium.text().replace("%", "").isDouble()){
				ticker.threeYearPremium = threeYearPremium.text().replace("%", "").toDouble()
			}
			if(fiveYearPremium.text().replace("%", "").isDouble()){
				ticker.fiveYearPremium = fiveYearPremium.text().replace("%", "").toDouble()
			}
			ticker.updated = new Date()
			ticker.validate()
			if(ticker.hasErrors()){
				ticker.errors.allErrors.each{log.error it}
			 } else {
			 	ticker.save(flush:true)
			 }
		}
	}
	
}

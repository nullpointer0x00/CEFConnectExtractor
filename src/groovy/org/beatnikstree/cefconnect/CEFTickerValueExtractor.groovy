package org.beatnikstree.cefconnect

import org.apache.commons.logging.Log;
import org.beatnikstree.cefconnect.domain.CEFTicker
import org.ccil.cowan.tagsoup.Parser;

@Grapes( @Grab('org.ccil.cowan.tagsoup:tagsoup:1.2') )
class CEFTickerValueExtractor {
	
	String BASE_URL = "http://www.cefconnect.com/Details/Summary.aspx?Ticker="
	CEFTicker ticker 
	def htmlParser 
	
	String SUMMARY_GRID_ID = 'ContentPlaceHolder1_cph_main_cph_main_SummaryGrid'
	String DISCOUNT_GRID_ID = 'ContentPlaceHolder1_cph_main_cph_main_ucPricing_DiscountGrid'
	
	public void init(){
		def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
		def slurper = new XmlSlurper(tagsoupParser)
		def url = BASE_URL + ticker.ticker
		this.htmlParser = slurper.parse(url)
	}
	
	public CEFTicker extract(){
		if(htmlParser != null){
			def currentPremium =  htmlParser.'**'.find{ it.@id == SUMMARY_GRID_ID }.tr[1].td[3]
			def sixMonthPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[1].td[1]
			def oneYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[2].td[1]
			def threeYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[3].td[1]
			def fiveYearPremium = htmlParser.'**'.find{ it.@id == DISCOUNT_GRID_ID }.tr[4].td[1]
			ticker.currentPremium = currentPremium
			ticker.sixMonthPremium = sixMonthPremium
			ticker.oneYearPremium = oneYearPremium
			ticker.threeYearPremium = threeYearPremium
			ticker.fiveYearPremium = fiveYearPremium
			log.info " ${currentPremium} ${sixMonthPremium} ${oneYearPremium} ${threeYearPremium} ${fiveYearPremium}"
			return ticker
		}
	}
	
}

package org.beatnikstree.cefconnect.domain

class CEFTicker {

	String ticker
	Double currentPremium
	Double sixMonthPremium
	Double oneYearPremium
	Double threeYearPremium
	Double fiveYearPremium 
	static constraints = {
		ticker unique: true
		currentPremium nullable: true
		sixMonthPremium nullable: true
		oneYearPremium nullable: true
		threeYearPremium nullable: true
		fiveYearPremium nullable: true
	}
	
}

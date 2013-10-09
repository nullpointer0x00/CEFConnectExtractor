package org.beatnikstree.cefconnect

import java.io.FileOutputStream

import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.beatnikstree.cefconnect.domain.CEFTicker

class CEFXlsDocumentBuilder {
	
	def tickerList
	
	
	def createXlsxDocument(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Ticker")
		row.createCell(1).setCellValue("Current")
		row.createCell(2).setCellValue("6 Months")
		row.createCell(3).setCellValue("1 Year")
		row.createCell(4).setCellValue("3 Year")
		row.createCell(5).setCellValue("5 Year")
		def rowNum = 1
		if(tickerList != null){
			for(CEFTicker ticker : tickerList){
				row = sheet.createRow(rowNum);
				row.createCell(0).setCellValue(ticker.ticker)
				row.createCell(1).setCellValue(ticker.currentPremium)
				row.createCell(2).setCellValue(ticker.sixMonthPremium)
				row.createCell(3).setCellValue(ticker.oneYearPremium)
				row.createCell(4).setCellValue(ticker.threeYearPremium)
				row.createCell(5).setCellValue(ticker.fiveYearPremium)
				rowNum++
			}
		}//else send empty document
		
		//TODO: change this path.
		File f = new File("CEFConnect.xls")
		def path = f.getAbsolutePath()
		FileOutputStream fos = new FileOutputStream(f)
		wb.write(fos)
		fos.close()
		f.getAbsolutePath()
	}
}

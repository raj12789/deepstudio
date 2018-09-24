package in.deepstudio.reports.service.impl;

import in.deepstudio.reports.model.PVQBillReport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

public class PvqBillExcelReportBuilder {

	
	public void buildExcelDocument(List<PVQBillReport> pvqBillReportList , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Excel Report Generating...");
		HSSFWorkbook workbook = new HSSFWorkbook();
		// create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("DeepStudio Order Bill Summary");
        sheet.setDefaultColumnWidth(15);
         
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        // create header row
        HSSFRow header = sheet.createRow(0);
        
        HSSFCell cell = header.createCell(0);
        Font headingFont = workbook.createFont();
        headingFont.setFontName("Arial");
        cell.setCellValue("Deep Studio Photography Videography Bill");
        CellStyle headingStyle =  workbook.createCellStyle();
        headingStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headingStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        headingStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headingFont.setColor(HSSFColor.WHITE.index);
        headingStyle.setFont(headingFont);
        cell.setCellStyle(headingStyle);
        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        
        header = sheet.createRow(1);
        header.createCell(0).setCellValue("SR");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("BILL NUMBER");
        header.getCell(1).setCellStyle(style);
        
        header.createCell(2).setCellValue("BILL DATE");
        header.getCell(2).setCellStyle(style);
        
        header.createCell(3).setCellValue("NAME");
        header.getCell(3).setCellStyle(style);
        
        
        header.createCell(4).setCellValue("BILL");
        header.getCell(4).setCellStyle(style);
        
        header.createCell(5).setCellValue("PAID");
        header.getCell(5).setCellStyle(style);
       
        header.createCell(6).setCellValue("DISCOUNT");
        header.getCell(6).setCellStyle(style);
        
        header.createCell(7).setCellValue("UNPAID");
        header.getCell(7).setCellStyle(style);
         
        System.out.println("Excel Report Generated");
        // create data rows
        int rowCount = 2;
         
        for (PVQBillReport pvq : pvqBillReportList) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(pvq.getId()+1);
            aRow.createCell(1).setCellValue(pvq.getBillNumber());
            aRow.createCell(2).setCellValue(pvq.getBillDate());
            aRow.createCell(3).setCellValue(pvq.getCustName());
            aRow.createCell(4).setCellValue(pvq.getQuatationBillAmount());
            aRow.createCell(5).setCellValue(pvq.getTotalPaidAmount());
            aRow.createCell(6).setCellValue(pvq.getDiscountAmount());
            aRow.createCell(7).setCellValue(pvq.getDueAmount());
        }
        OutputStream out = null;
        try {
        	response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=\"workbook.xls\"");
    		out = response.getOutputStream();
        	workbook.write(out);
        }catch(FileNotFoundException fne){
        	fne.printStackTrace();
        }catch(IllegalStateException ise){
        	System.out.println(ise.getMessage());
        }catch(IOException ioe){
        	ioe.printStackTrace();
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }finally{
        	out.close();
        	workbook.close();
        }
	}
}

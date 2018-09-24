package in.deepstudio.reports.controller;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoService;
import in.deepstudio.pvq.domain.PvqBillInfo;
import in.deepstudio.pvq.service.PvqBillInfoService;
import in.deepstudio.reports.model.FramingCenterBillReport;
import in.deepstudio.reports.model.PVQBillReport;
import in.deepstudio.reports.service.impl.FramingBillExcelReportBuilder;
import in.deepstudio.reports.service.impl.PvqBillExcelReportBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reportsManager/generateExcel")
public class BillReportController {

	/**
     * Handle request to the default page
     */
	
	@Autowired
	private PvqBillInfoService pvqBillInfoService;
	@Autowired
	private FrameCustBillingInfoService frameCustBillingInfoService;
	
    @RequestMapping(method = RequestMethod.GET)
    public String viewHome() {
        return "reportsManager/billSummaryReport";
    }
 
    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(value = "/downloadPvqExcelReport", method = RequestMethod.GET)
    public ModelAndView downloadPvqExcelReport(HttpServletRequest request, HttpServletResponse response) {
        // create some sample data
        List<PVQBillReport> pvqBillReportList = new ArrayList<PVQBillReport>();
        List<PvqBillInfo> pvqList = pvqBillInfoService.findAll();
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        if(pvqList !=null){
        	for(int i=0 ; i <= pvqList.size()-1 ; i++){
        		PVQBillReport pbr = new PVQBillReport();
        		pbr.setId(i);
        		pbr.setBillNumber(pvqList.get(i).getBillNumber());
        		pbr.setBillDate(form.format(pvqList.get(i).getBillDate()));
        		pbr.setCustName(pvqList.get(i).getCustName());
        		pbr.setQuatationBillAmount(pvqList.get(i).getQuatationBillAmount());
        		pbr.setTotalPaidAmount(pvqList.get(i).getTotalPaidAmount());
        		pbr.setDiscountAmount(pvqList.get(i).getDueAmount());
        		pbr.setDueAmount(pvqList.get(i).getDueAmount());
        		pvqBillReportList.add(pbr);
        	}
        	//System.out.println("PVQ BIll List Size :"+pvqBillReportList);
        	PvqBillExcelReportBuilder excelView = new PvqBillExcelReportBuilder();
    		try {
				excelView.buildExcelDocument(pvqBillReportList,request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
        // return a view which will be resolved by an excel view resolver
         return new ModelAndView("reportsManager/billSummaryReport", "pvqBillReportList", pvqBillReportList);
    }
	
    @RequestMapping(value = "/downloadFrameExcelReport", method = RequestMethod.GET)
    public ModelAndView downloadFrameExcelReport(HttpServletRequest request, HttpServletResponse response) {
        // create some sample data
        List<FramingCenterBillReport> framingCenterBillReportList = new ArrayList<FramingCenterBillReport>();
        List<FrameCustBillingInfo> frameList = frameCustBillingInfoService.findAll();
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        if(frameList !=null){
        	for(int i=0 ; i <= frameList.size()-1 ; i++){
        		FramingCenterBillReport fr = new FramingCenterBillReport();
        		fr.setId(i);
        		fr.setBillNumber(frameList.get(i).getCustBillNumber());
        		fr.setBillDate(form.format(frameList.get(i).getBillDate()));
        		fr.setCustName(frameList.get(i).getCustName());
        		fr.setQuatationBillAmount(String.valueOf(frameList.get(i).getFinalBillAmount()));
        		fr.setTotalPaidAmount(frameList.get(i).getTotalPaidAmount());
        		fr.setDiscountAmount(frameList.get(i).getDueAmount());
        		fr.setDueAmount(frameList.get(i).getDueAmount());
        		framingCenterBillReportList.add(fr);
        	}
        	//System.out.println("Frame BIll List Size :"+framingCenterBillReport);
        	FramingBillExcelReportBuilder excelView = new FramingBillExcelReportBuilder();
    		try {
				excelView.buildExcelDocument(framingCenterBillReportList,request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
        // return a view which will be resolved by an excel view resolver
         return new ModelAndView("reportsManager/billSummaryReport", "framingCenterBillReportList", framingCenterBillReportList);
    }
    
}

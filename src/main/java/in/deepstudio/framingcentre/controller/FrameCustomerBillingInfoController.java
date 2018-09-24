package in.deepstudio.framingcentre.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.deepstudio.Utils.BillDeskValidator;
import in.deepstudio.Utils.Constants;
import in.deepstudio.framingcentre.domain.FrameBillPaymentHistory;
import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import in.deepstudio.framingcentre.domain.FrameCustBillingInfoDetail;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameRate;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.service.FrameBillPaymentHistoryService;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoDetailService;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoService;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameClientTypeService;
import in.deepstudio.framingcentre.service.FrameInfoService;
import in.deepstudio.framingcentre.service.FrameNumberService;
import in.deepstudio.framingcentre.service.FrameRateService;
import in.deepstudio.framingcentre.service.FrameSizeService;
import in.deepstudio.framingcentre.service.FrameThicknessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;

@Controller
@RequestMapping("/framingCenter/frameBilling")
public class FrameCustomerBillingInfoController {
	static Logger logger = LoggerFactory.getLogger(FrameCustomerBillingInfoController.class);
	
	@Autowired
	private FrameCustBillingInfoService frameCustBillingInfoService;
	@Autowired
	private FrameCustBillingInfoDetailService frameCustBillingInfoDetailService;
	@Autowired
	private FrameBillPaymentHistoryService frameBillPaymentHistoryService;
	@Autowired
	private FrameDetailsService frameDetailsService;
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	@Autowired
	private FrameRateService frameRateService;
	@Autowired
	private FrameInfoService frameInfoService;
	@Autowired
	private FrameNumberService frameNumberService;
	@Autowired
	private FrameSizeService frameSizeService;
	@Autowired
	private FrameThicknessService frameThicknessService;
	@Autowired
	private BillDeskValidator billDeskValidator;
	
	@RequestMapping
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/customerBillInfo");
		mv.addObject("billingInfo", new FrameCustBillingInfo());
		mv.addObject("billingInfoType", "new");
		return mv;
	}
	
	
	@ModelAttribute(value="billStatus")
	public List<String> populateBillStatusList()
	{
		List<String> billStatusList = new ArrayList<String>();
		billStatusList.addAll(Arrays.asList(Constants.billStatusList));
		logger.info("billStatus populated: "+billStatusList);
		return billStatusList;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "billDesk")
	public ModelAndView billDesk() {
		ModelAndView mv = new ModelAndView("framingCenter/billDesk");
		mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("frameClientTypes", frameClientTypeService.findAll());
		mv.addObject("billingInfo", new FrameCustBillingInfo());
		mv.addObject("billingInfoType", "new");
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		return mv;
	}
	
	
	@RequestMapping(value = {"/", "/viewAllBills"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("billingInfo") FrameCustBillingInfo frameCustBillingInfo,
			@ModelAttribute("billingInfoType") String billingInfoType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/customerBillInfo");
		List<FrameCustBillingInfo> billList = frameCustBillingInfoService.findAll();
		if(billList !=null){
			mv.addObject("billingInfos", billList);
			mv.addObject("billingInfo", frameCustBillingInfo);
			if(!billingInfoType.equalsIgnoreCase("")){
				mv.addObject("billingInfoType", billingInfoType);
			}else{
				mv.addObject("billingInfoType", "new");
			}
		}
		return mv;
	}
	
	//Save Frame_Info
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveFrameInfo(
			@ModelAttribute("billingInfo") FrameCustBillingInfo frameCustBillingInfo,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		billDeskValidator.validate(frameCustBillingInfo, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("billingInfoType", "new");
			model.addAttribute("frameDetails", frameDetailsService.findAll());
			model.addAttribute("frameClientTypes", frameClientTypeService.findAll());
			model.addAttribute("objFrameDetails", new FrameDetails());
			model.addAttribute("frameTypes", frameInfoService.findAll());
			model.addAttribute("frameNumbers", frameNumberService.findAll());
			model.addAttribute("frameSizes", frameSizeService.findAll());
			model.addAttribute("frameThickness", frameThicknessService.findAll());
			return "framingCenter/billDesk";
		}
		redirectAttributes.addFlashAttribute("billingInfoType", "new");
		if(frameCustBillingInfo.getCustBillingId() == null){
			logger.info("Creating New Record...");
			frameCustBillingInfo.setCreatedDate(new Date());
		}else {
			logger.info("Updating Existing Record...");
			FrameCustBillingInfo cInfo =  frameCustBillingInfoService.findOne(frameCustBillingInfo.getCustBillingId());
			frameCustBillingInfo.setCreatedDate(cInfo.getCreatedDate());
		}
		frameCustBillingInfo.setUpdatedDate(new Date());
		frameCustBillingInfo.setIpAddress(request.getRemoteAddr());
		
		
		int totalPaidAmount = 0;
		int lastVersion = 0;
		List<FrameBillPaymentHistory> hsList = null;
		if(frameCustBillingInfo.getCustBillingId() != null){
			FrameCustBillingInfo frameCustInfo =  frameCustBillingInfoService.findOne(frameCustBillingInfo.getCustBillingId());
			hsList = frameCustInfo.getFrameBillPaymentHistory();
			for(FrameBillPaymentHistory b : hsList){
				totalPaidAmount = totalPaidAmount + Integer.valueOf(b.getDepositeAmount());
				b.setFrameCustBillingInfo(frameCustBillingInfo);
			}
		}
		if (hsList != null && !hsList.isEmpty()) {
				lastVersion = hsList.get(hsList.size()-1).getFrameBillPaymentVersion();
				System.out.println("Last Version :"+lastVersion);
		}else{
			System.out.println("First Version is going to be placed:"+lastVersion);
		}
		
		
		
		frameCustBillingInfo.setTotalPaidAmount(String.valueOf(Integer.valueOf(frameCustBillingInfo.getDepositeAmount()) +totalPaidAmount));
		frameCustBillingInfo.setDueAmount(String.valueOf(Integer.valueOf(frameCustBillingInfo.getFinalBillAmount()) - Integer.valueOf(frameCustBillingInfo.getTotalPaidAmount())));
		
		FrameBillPaymentHistory frameBillPaymentHistory = new FrameBillPaymentHistory();
		frameBillPaymentHistory.setDepositeAmount(frameCustBillingInfo.getDepositeAmount());
		frameBillPaymentHistory.setFrameBillPaymentVersion(lastVersion+1);
		frameBillPaymentHistory.setFrameCustBillingInfo(frameCustBillingInfo);
		
		hsList = new ArrayList<>();
		hsList.add(frameBillPaymentHistory);
		frameCustBillingInfo.setFrameBillPaymentHistory(hsList);
		
		
		List<FrameCustBillingInfoDetail> bill = frameCustBillingInfo.getFrameCustBillingInfoDetail();
		logger.info("Bills Size------"+bill.size());
		
		for(FrameCustBillingInfoDetail b : bill) {
			b.setFrameCustBillingInfo(frameCustBillingInfo);
		}
		
		frameCustBillingInfoService.save(frameCustBillingInfo);
		redirectAttributes.addFlashAttribute("billingInfo", new FrameCustBillingInfo());
		
		return "redirect:/framingCenter/frameBilling/viewAllBills";
	}
	
	
	//Delete Frame_Rate
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteBillDetails(@RequestParam("custBillingId") Long id,
			@ModelAttribute("billingInfo") FrameCustBillingInfo frameCustBillingInfo,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		frameCustBillingInfo.setUpdatedDate(new Date());
		frameCustBillingInfo.setIpAddress(request.getRemoteAddr());
		frameCustBillingInfoService.delete(id);
		redirectAttributes.addFlashAttribute("billingInfo", new FrameCustBillingInfo());
		redirectAttributes.addFlashAttribute("billingInfoType", "new");
		return "redirect:/framingCenter/frameBilling/viewAllBills";
	}
	
	//Edit Frame_Rate
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public ModelAndView editBillDetails(@RequestParam("custBillingId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		ModelAndView mv = new ModelAndView("framingCenter/billDesk");
		FrameCustBillingInfo frameCustBillingInfo =  frameCustBillingInfoService.findOne(id);
		frameCustBillingInfo.setDepositeAmount("0");
		mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("frameClientTypes", frameClientTypeService.findAll());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("billingInfo", frameCustBillingInfo);
		mv.addObject("billingInfoType", "update");
		return mv;
	}
	
	@RequestMapping(value= "/create" ,params={"addRow"})
	@PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addRow(final FrameCustBillingInfo frameCustBillingInfo, final BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("framingCenter/billDesk");
		FrameCustBillingInfoDetail objFrameDetail = new FrameCustBillingInfoDetail();
		objFrameDetail.setActualRate(0);
		objFrameDetail.setSellingRate(0);
		objFrameDetail.setQuantity(0);
		frameCustBillingInfo.getFrameCustBillingInfoDetail().add(objFrameDetail);
		logger.info("Size After Add... "+frameCustBillingInfo.getFrameCustBillingInfoDetail().size());
		mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("frameClientTypes", frameClientTypeService.findAll());
		mv.addObject("billingInfo", frameCustBillingInfo);
		mv.addObject("billingInfoType", "new");
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
	    return mv;
    }
    
    
    @RequestMapping(value= "/create",params={"removeRow"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView removeRow(final FrameCustBillingInfo frameCustBillingInfo, final BindingResult bindingResult, final HttpServletRequest req) {
    	ModelAndView mv = new ModelAndView("framingCenter/billDesk");
    	final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        
    	FrameCustBillingInfoDetail fcDetail = frameCustBillingInfo.getFrameCustBillingInfoDetail().get(rowId.intValue());
    	if(fcDetail.getBillDetailId() != null) {
    		frameCustBillingInfoDetailService.delete(fcDetail.getBillDetailId());
    	}
    	frameCustBillingInfo.getFrameCustBillingInfoDetail().remove(rowId.intValue());
        
    	logger.info("Size after Remove... "+frameCustBillingInfo.getFrameCustBillingInfoDetail().size());
        mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("frameClientTypes", frameClientTypeService.findAll());
        mv.addObject("billingInfo", frameCustBillingInfo);
		mv.addObject("billingInfoType", "new");
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		
        return mv;
        
    }
	
    @RequestMapping(value= Constants.RETRIEVE_ACTUAL_PRICE,method = RequestMethod.GET,headers ="Accept=*/*")
    @PreAuthorize("hasAuthority('ADMIN')")
	public @ResponseBody String actualPrice(@RequestParam("frameDetail") String frameDetailId,
				@RequestParam("clientType") String clientTypeId){
    	logger.info("FrameDetail :"+frameDetailId);
    	logger.info("clientType :"+clientTypeId);
    	String actualPrice="0";
    	String result="";
    	String Msg="Failed";
    	if(frameDetailId != null && frameDetailId.length() > 0 && clientTypeId != null && clientTypeId.length() > 0){
	    	FrameDetails frameDetail = frameDetailsService.findOne(Long.parseLong(frameDetailId));
	    	FrameClientType frameClientType = frameClientTypeService.findOne(Long.parseLong(clientTypeId));
	    	if(frameDetail != null && frameClientType != null){
	    		FrameRate frameRate = frameRateService.findByFrameDetailsAndFrameClientType(frameDetail, frameClientType);
	    		if(frameRate != null && Strings.nullToEmpty(frameRate.getPrice()).length() > 0){
	    			Msg="Success";
	    			actualPrice=frameRate.getPrice();
	    		}
	    	}
    	}
    	result = "{\"Msg\":\""+Msg+"\",\"Result\":\""+actualPrice+"\"}";
    	return result;
	}
    
    @RequestMapping(value=Constants.FIND_FRAMES,method = RequestMethod.GET,headers ="Accept=*/*")
    @PreAuthorize("hasAuthority('ADMIN')")
	public @ResponseBody String findFrame(@RequestParam("frameTypeId") String frameInfoId,
			@RequestParam("frameNumberId") String frameNumberId,
			@RequestParam("frameThicknessId") String framethicknessId,
			@RequestParam("frameSizeId") String framesizeId
			){	
			logger.info("frameTypeId"+frameInfoId);
			logger.info("frameNumberId"+frameNumberId);
			logger.info("frameThicknessId"+framethicknessId);
			logger.info("frameSizeId"+framesizeId);
			String result="";
			String frameDetailResult = "";
			String Msg="";
			FrameInfo frameInfo = frameInfoService.findOne(Long.parseLong(frameInfoId));
			FrameNumber frameNumber = frameNumberService.findOne(Long.parseLong(frameNumberId));
			FrameThickness framethickness = frameThicknessService.findOne(Long.parseLong(framethicknessId));
			FrameSize framesize = frameSizeService.findOne(Long.parseLong(framesizeId));
			FrameDetails frameDetail = frameDetailsService.findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(frameInfo, frameNumber, framesize,framethickness);
			if(frameDetail != null && Strings.nullToEmpty(String.valueOf(frameDetail.getFrameQualifiedId())) != null){
				logger.info("frameDetailId : "+Strings.nullToEmpty(String.valueOf(frameDetail.getFrameQualifiedId())));
				Msg="Frame Combination Successfully Added.";
				frameDetailResult=String.valueOf(frameDetail.getFrameQualifiedId());
			}else{
				Msg="Frame Combination is Not Available.";
				frameDetailResult="";
			}
			result = "{\"Msg\":\""+Msg+"\",\"Result\":\""+frameDetailResult+"\"}";
			logger.info("json frameDetailResult : "+result);
		return result;
		
	}
}

package in.deepstudio.pvq.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.PvqBillDeskValidator;
import in.deepstudio.framingcentre.controller.FrameCustomerBillingInfoController;
import in.deepstudio.pvq.domain.PvqBillConcernDetail;
import in.deepstudio.pvq.domain.PvqBillInfo;
import in.deepstudio.pvq.domain.PvqBillOrderSummary;
import in.deepstudio.pvq.domain.PvqBillPaymentHistory;
import in.deepstudio.pvq.domain.PvqConcernRate;
import in.deepstudio.pvq.domain.PvqConcernType;
import in.deepstudio.pvq.repository.PvqBillOrderSummaryrRepository;
import in.deepstudio.pvq.service.PvqBillConcernDetailService;
import in.deepstudio.pvq.service.PvqBillInfoService;
import in.deepstudio.pvq.service.PvqBillPaymentHistoryService;
import in.deepstudio.pvq.service.PvqConcernRateService;
import in.deepstudio.pvq.service.PvqConcernTypeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/pvqManager/pvqBillDesk")
public class PvqBillInfoController {

static Logger logger = LoggerFactory.getLogger(FrameCustomerBillingInfoController.class);
	
	@Autowired
	private PvqBillInfoService pvqBillInfoService;
	@Autowired
	private PvqBillConcernDetailService pvqBillConcernDetailService;
	@Autowired
	private PvqBillPaymentHistoryService pvqBillPaymentHistoryService;
	@Autowired
	private PvqBillOrderSummaryrRepository pvqBillOrderSummaryrRepository;
	@Autowired
	private PvqConcernTypeService pvqConcernTypeService;
	@Autowired
	private PvqConcernRateService pvqConcernRateService;
	@Autowired
	private PvqBillDeskValidator pvqBillDeskValidator;
	
	@RequestMapping
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("pvqManager/pvqCustomerBillInfo");
		mv.addObject("objPvq", new PvqBillInfo());
		mv.addObject("pvqType", "new");
		return mv;
	}
	
	
	@ModelAttribute(value="orderStatus")
	public List<String> populateOrderStatusList(){
		List<String> orderStatusList = new ArrayList<String>();
		orderStatusList.addAll(Arrays.asList(Constants.orderStatusList));
		logger.info("orderStatusList populated: "+orderStatusList);
		return orderStatusList;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "billDesk")
	public ModelAndView billDesk() {
		ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
		mv.addObject("objPvq", new PvqBillInfo());
		mv.addObject("pvqType", "new");
		mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
		return mv;
	}
	
	@RequestMapping(value = {"/", "/viewAllBills"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objPvq") PvqBillInfo pvqBillInfo,
			@ModelAttribute("pvqType") String pvqType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("pvqManager/pvqCustomerBillInfo");
		List<PvqBillInfo> pvqList = pvqBillInfoService.findAll();
		if(pvqList !=null){
			mv.addObject("pvqList", pvqList);
			mv.addObject("objPvq", pvqBillInfo);
			if(!pvqType.equalsIgnoreCase("")){
				mv.addObject("pvqType", pvqType);
			}else{
				mv.addObject("pvqType", "new");
			}
		}
		return mv;
	}
	
	//Save PVQ_Info
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String savePvqInfo(
			@ModelAttribute("objPvq") PvqBillInfo pvqBillInfo,
			BindingResult result ,
			@RequestParam("billStatus") String billStatus,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		
		pvqBillDeskValidator.validate(pvqBillInfo, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("pvqType", "new");
			model.addAttribute("concernTypes", pvqConcernTypeService.findAll());
			model.addAttribute("concernRates", pvqConcernRateService.findAll());
			return "pvqManager/pvqBillDesk";
		}
		redirectAttributes.addFlashAttribute("pvqType", "new");
		if(pvqBillInfo.getPvqBillDetailId() == null){
			logger.info("Creating New Record...");
			pvqBillInfo.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			PvqBillInfo cInfo =  pvqBillInfoService.findOne(pvqBillInfo.getPvqBillDetailId());
			pvqBillInfo.setCreatedDate(cInfo.getCreatedDate());
		}
		pvqBillInfo.setBillStatus(billStatus);
		pvqBillInfo.setUpdatedDate(new Date());
		pvqBillInfo.setIpAddress(request.getRemoteAddr());
		
		int totalPaidAmount = 0;
		int lastVersion = 0;
		List<PvqBillPaymentHistory> hsList = null;
		if(pvqBillInfo.getPvqBillDetailId() != null){
			PvqBillInfo pvqInfo = pvqBillInfoService.findOne(pvqBillInfo.getPvqBillDetailId());
			hsList = pvqInfo.getPvqBillPaymentHistories();
			for(PvqBillPaymentHistory b : hsList){
				totalPaidAmount = totalPaidAmount + Integer.valueOf(b.getDepositeAmount());
				b.setPvqBillInfo(pvqBillInfo);
			}
		}
		if (hsList != null && !hsList.isEmpty()) {
				lastVersion = hsList.get(hsList.size()-1).getPvqBillPaymentVersion();
				System.out.println("Last Version :"+lastVersion);
				
		}else{
			System.out.println("First Version is going to be placed:"+lastVersion);
		}
		
		
		pvqBillInfo.setTotalPaidAmount(String.valueOf(Integer.valueOf(pvqBillInfo.getDepositeAmount()) +totalPaidAmount));
		pvqBillInfo.setDueAmount(String.valueOf(Integer.valueOf(pvqBillInfo.getQuatationBillAmount()) - Integer.valueOf(pvqBillInfo.getTotalPaidAmount())));
		
		PvqBillPaymentHistory pvqBillPaymentHistory = new PvqBillPaymentHistory();
		pvqBillPaymentHistory.setDepositeAmount(pvqBillInfo.getDepositeAmount());
		pvqBillPaymentHistory.setPvqBillPaymentVersion(lastVersion+1);
		pvqBillPaymentHistory.setPvqBillInfo(pvqBillInfo);
		
		hsList = new ArrayList<>();
		hsList.add(pvqBillPaymentHistory);
		pvqBillInfo.setPvqBillPaymentHistories(hsList);
		
		
		//Entry of PvqBillInfoID into All PvqBillConcernDetail Objects.
		List<PvqBillConcernDetail> bill = pvqBillInfo.getPvqBillConcernDetails();
		logger.info("PvqBillConcernDetail Size------"+bill.size());
		
		for(PvqBillConcernDetail b : bill) {
			b.setPvqBillInfo(pvqBillInfo);
		}
		
		//Entry of PvqBillInfoID into All PvqBillOrderSummary Objects.
		List<PvqBillOrderSummary> bill1 = pvqBillInfo.getPvqBillOrderSummary();
		logger.info("PvqBillOrderSummary Size------"+bill.size());
		
		for(PvqBillOrderSummary b : bill1) {
			b.setPvqBillInfo(pvqBillInfo);
		}
		
		pvqBillInfoService.save(pvqBillInfo);
		redirectAttributes.addFlashAttribute("objPvq", new PvqBillInfo());
		
		return "redirect:/pvqManager/pvqBillDesk/viewAllBills";
	}
	
	
	//Delete Pvq_Rate
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteBillDetails(@RequestParam("custBillingId") Long id,
			@ModelAttribute("objPvq") PvqBillInfo pvqBillInfo,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		pvqBillInfo.setUpdatedDate(new Date());
		pvqBillInfo.setIpAddress(request.getRemoteAddr());
		pvqBillInfoService.delete(id);
		redirectAttributes.addFlashAttribute("objPvq", new PvqBillInfo());
		redirectAttributes.addFlashAttribute("pvqType", "new");
		return "redirect:/pvqManager/pvqBillDesk/viewAllBills";
	}
	
	//Edit Pvq_Rate
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public ModelAndView editBillDetails(@RequestParam("custBillingId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
		PvqBillInfo pvqBillInfo =  pvqBillInfoService.findOne(id);
		pvqBillInfo.setDepositeAmount("0");
		mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
		mv.addObject("objPvq", pvqBillInfo);
		mv.addObject("pvqType", "update");
		return mv;
	}
	
	@RequestMapping(value= "/create" ,params={"addRow"})
	@PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addRow(final PvqBillInfo pvqBillInfo, final BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
		PvqBillConcernDetail objPvqBillConcernDetail = new PvqBillConcernDetail();
		objPvqBillConcernDetail.setActualRate(0);
		objPvqBillConcernDetail.setSellingRate(0);
		objPvqBillConcernDetail.setQuantity(0);
		pvqBillInfo.getPvqBillConcernDetails().add(objPvqBillConcernDetail);
		logger.info("Size After Add... "+pvqBillInfo.getPvqBillConcernDetails().size());
		mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
		mv.addObject("objPvq", pvqBillInfo);
		mv.addObject("pvqType", "new");
		
		return mv;
    }
    
    
    @RequestMapping(value= "/create",params={"removeRow"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView removeRow(final PvqBillInfo pvqBillInfo, final BindingResult bindingResult, final HttpServletRequest req) {
    	ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
    	final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        
    	PvqBillConcernDetail objPvqBillConcernDetail = pvqBillInfo.getPvqBillConcernDetails().get(rowId.intValue());
    	if(objPvqBillConcernDetail.getPvqBillConcernDetailId() != null) {
    		pvqBillConcernDetailService.delete(objPvqBillConcernDetail.getPvqBillConcernDetailId());
    	}
    	pvqBillInfo.getPvqBillConcernDetails().remove(rowId.intValue());
        logger.info("Size after Remove... "+pvqBillInfo.getPvqBillConcernDetails().size());
    	mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
        mv.addObject("objPvq", pvqBillInfo);
		mv.addObject("pvqType", "new");
		
        return mv;
    }
	
    @RequestMapping(value= "/create" ,params={"addOrderSummaryRow"})
	@PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addOrderSummaryRow(final PvqBillInfo pvqBillInfo, final BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
		PvqBillOrderSummary objPvqBillOrderSummary = new PvqBillOrderSummary();
		pvqBillInfo.getPvqBillOrderSummary().add(objPvqBillOrderSummary);
		logger.info("Size After Add... "+pvqBillInfo.getPvqBillOrderSummary().size());
		mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
		mv.addObject("objPvq", pvqBillInfo);
		mv.addObject("pvqType", "new");
		
		return mv;
    }
    
    
    @RequestMapping(value= "/create",params={"removeOrderSummaryRow"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView removeOrderSummaryRow(final PvqBillInfo pvqBillInfo, final BindingResult bindingResult, final HttpServletRequest req) {
    	ModelAndView mv = new ModelAndView("pvqManager/pvqBillDesk");
    	final Integer rowId = Integer.valueOf(req.getParameter("removeOrderSummaryRow"));
        
    	PvqBillOrderSummary objPvqBillOrderSummary = pvqBillInfo.getPvqBillOrderSummary().get(rowId.intValue());
    	if(objPvqBillOrderSummary.getPvqBillOrderSummaryId() != null) {
    		pvqBillOrderSummaryrRepository.delete(objPvqBillOrderSummary.getPvqBillOrderSummaryId());
    	}
    	pvqBillInfo.getPvqBillOrderSummary().remove(rowId.intValue());
        logger.info("Size after Remove... "+pvqBillInfo.getPvqBillOrderSummary().size());
    	mv.addObject("concernTypes", pvqConcernTypeService.findAll());
    	mv.addObject("concernRates", pvqConcernRateService.findAll());
		mv.addObject("objPvq", pvqBillInfo);
		mv.addObject("pvqType", "new");
		
        return mv;
    }
    
    @RequestMapping(value= Constants.RETRIEVE_ACTUAL_PRICE,method = RequestMethod.GET,headers ="Accept=*/*")
    @PreAuthorize("hasAuthority('ADMIN')")
	public @ResponseBody String actualPrice(@RequestParam("concernType") String concernTypeId,
				@RequestParam("concernName") String concernName){
    	logger.info("concernTypeId :"+concernTypeId);
    	logger.info("concernName :"+concernName);
    	String actualPrice="0";
    	String result="";
    	String Msg="Failed";
    	if(concernTypeId != null && concernTypeId.length() > 0 && concernName != null && concernName.length() > 0){
    		PvqConcernType pvqConcernType = pvqConcernTypeService.findOne(Long.parseLong(concernTypeId));
	    	if(pvqConcernType != null){
	    		PvqConcernRate pvqConcernRate = pvqConcernRateService.findByPvqConcernTypeAndPvqConcernName(pvqConcernType,concernName);
	    		if(pvqConcernRate != null && Strings.nullToEmpty(pvqConcernRate.getPvqConcernRateAmt()).length() > 0){
	    			Msg="Success";
	    			actualPrice=pvqConcernRate.getPvqConcernRateAmt();
	    		}
	    	}
    	}
    	result = "{\"Msg\":\""+Msg+"\",\"Result\":\""+actualPrice+"\"}";
    	return result;
	}
    
}

package in.deepstudio.pvq.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.PvqConcernRateValidator;
import in.deepstudio.pvq.domain.PvqConcernRate;
import in.deepstudio.pvq.service.PvqConcernRateService;
import in.deepstudio.pvq.service.PvqConcernTypeService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/pvqManager/pvqConcernRate")
public class PvqConcernRateController {

	static Logger logger = LoggerFactory.getLogger(PvqConcernRateController.class);
	@Autowired
	private PvqConcernRateService pvqConcernRateService;
	@Autowired
	private PvqConcernRateValidator validator;
	@Autowired
	private PvqConcernTypeService pvqConcernTypeService;
	
	
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("pvqManager/pvqConcernRate");
		mv.addObject("pvqList", pvqConcernRateService.findAll());
		mv.addObject("objPvq", new PvqConcernRate());
		mv.addObject("pvqConcernTypes", pvqConcernTypeService.findAll());
		mv.addObject("pvqType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllPvqDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objPvq") PvqConcernRate objPvq,
			@ModelAttribute("pvqType") String pvqType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("pvqManager/pvqConcernRate");
		List<PvqConcernRate> pvqList = pvqConcernRateService.findAll();
		if(pvqList !=null){
			mv.addObject("pvqList", pvqList);
			mv.addObject("objPvq", objPvq);
			mv.addObject("pvqConcernTypes", pvqConcernTypeService.findAll());
			if(!pvqType.equalsIgnoreCase("")){
				mv.addObject("pvqType", pvqType);
			}else{
				mv.addObject("pvqType", "new");
			}
		}
		return mv;
	}
	
	//Save PVQ_ConvernType
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String savePvqInfo(
			@ModelAttribute("objPvq") PvqConcernRate objPvq,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objPvq, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("pvqType", "new");
			model.addAttribute("pvqList", pvqConcernRateService.findAll());
			model.addAttribute("pvqConcernTypes", pvqConcernTypeService.findAll());
			return "pvqManager/pvqConcernRate";
		}
		redirectAttributes.addFlashAttribute("pvqType", "new");
		if(objPvq.getPvqConcernRateId() == null){
			logger.info("Creating New Record...");
			objPvq.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			PvqConcernRate pvqInfo =  pvqConcernRateService.findOne(objPvq.getPvqConcernRateId());
			objPvq.setCreatedDate(pvqInfo.getCreatedDate());
		}
		objPvq.setUpdatedDate(new Date());
		objPvq.setIpAddress(request.getRemoteAddr());
		
		pvqConcernRateService.save(objPvq);
		
		redirectAttributes.addFlashAttribute("objPvq", new PvqConcernRate());
		
		return "redirect:/pvqManager/pvqConcernRate/viewAllPvqDetails";
	}
	
	//Delete PVQ_ConvernType
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deletePVQInfo(@RequestParam("pvqId") Long id,
			@ModelAttribute("objPvq") PvqConcernRate objPvq,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objPvq.setUpdatedDate(new Date());
		objPvq.setIpAddress(request.getRemoteAddr());
		pvqConcernRateService.delete(id);
		redirectAttributes.addFlashAttribute("objPvq", new PvqConcernRate());
		redirectAttributes.addFlashAttribute("pvqType", "new");
		return "redirect:/pvqManager/pvqConcernRate/viewAllPvqDetails";
	}
	
	//Edit PVQ_ConvernType
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editPVQInfo(@RequestParam("pvqId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		PvqConcernRate pvqInfo =  pvqConcernRateService.findOne(id);
		redirectAttributes.addFlashAttribute("objPvq", pvqInfo);
		redirectAttributes.addFlashAttribute("pvqType", "update");
		return "redirect:/pvqManager/pvqConcernRate/viewAllPvqDetails";
	}
	
}

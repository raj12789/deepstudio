package in.deepstudio.pvq.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.PvqConcernTypeValidator;
import in.deepstudio.pvq.domain.PvqConcernType;
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
@RequestMapping(value="/pvqManager/pvqConcernType")
public class PvqConcernTypeController {

	static Logger logger = LoggerFactory.getLogger(PvqConcernTypeController.class);
	@Autowired
	private PvqConcernTypeService pvqConcernTypeService;
	@Autowired
	private PvqConcernTypeValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("pvqManager/pvqConcernType");
		mv.addObject("pvqList", pvqConcernTypeService.findAll());
		mv.addObject("objPvq", new PvqConcernType());
		mv.addObject("pvqType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllPvqDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objPvq") PvqConcernType objPvq,
			@ModelAttribute("pvqType") String pvqType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("pvqManager/pvqConcernType");
		List<PvqConcernType> pvqList = pvqConcernTypeService.findAll();
		if(pvqList !=null){
			mv.addObject("pvqList", pvqList);
			mv.addObject("objPvq", objPvq);
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
			@ModelAttribute("objPvq") PvqConcernType objPvq,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objPvq, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("pvqType", "new");
			model.addAttribute("pvqList", pvqConcernTypeService.findAll());
			return "pvqManager/pvqConcernType";
		}
		redirectAttributes.addFlashAttribute("pvqType", "new");
		if(objPvq.getPvqConcernTypeId() == null){
			logger.info("Creating New Record...");
			objPvq.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			PvqConcernType pvqInfo =  pvqConcernTypeService.findOne(objPvq.getPvqConcernTypeId());
			objPvq.setCreatedDate(pvqInfo.getCreatedDate());
		}
		objPvq.setUpdatedDate(new Date());
		objPvq.setIpAddress(request.getRemoteAddr());
		
		pvqConcernTypeService.save(objPvq);
		
		redirectAttributes.addFlashAttribute("objPvq", new PvqConcernType());
		
		return "redirect:/pvqManager/pvqConcernType/viewAllPvqDetails";
	}
	
	//Delete PVQ_ConvernType
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deletePVQInfo(@RequestParam("pvqId") Long id,
			@ModelAttribute("objPvq") PvqConcernType objPvq,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objPvq.setUpdatedDate(new Date());
		objPvq.setIpAddress(request.getRemoteAddr());
		pvqConcernTypeService.delete(id);
		redirectAttributes.addFlashAttribute("objPvq", new PvqConcernType());
		redirectAttributes.addFlashAttribute("pvqType", "new");
		return "redirect:/pvqManager/pvqConcernType/viewAllPvqDetails";
	}
	
	//Edit PVQ_ConvernType
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editPVQInfo(@RequestParam("pvqId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		PvqConcernType pvqInfo =  pvqConcernTypeService.findOne(id);
		redirectAttributes.addFlashAttribute("objPvq", pvqInfo);
		redirectAttributes.addFlashAttribute("pvqType", "update");
		return "redirect:/pvqManager/pvqConcernType/viewAllPvqDetails";
	}
	
}

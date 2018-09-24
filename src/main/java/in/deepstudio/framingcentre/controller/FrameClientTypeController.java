package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameClientTypeValidator;
import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.service.FrameClientTypeService;
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
@RequestMapping(value="/framingCenter/frameClientType")
public class FrameClientTypeController {

	static Logger logger = LoggerFactory.getLogger(FrameClientTypeController.class);
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	@Autowired(required=false)
	private FrameClientTypeValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameClientType");
		mv.addObject("frameList", frameClientTypeService.findAll());
		mv.addObject("objFrame", new FrameClientType());
		mv.addObject("frameType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objFrame") FrameClientType objFrame,
			@ModelAttribute("frameType") String frameType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameClientType");
		List<FrameClientType> frameInfoList = frameClientTypeService.findAll();
		if(frameInfoList !=null){
			mv.addObject("frameList", frameInfoList);
			mv.addObject("objFrame", objFrame);
			if(!frameType.equalsIgnoreCase("")){
				mv.addObject("frameType", frameType);
			}else{
				mv.addObject("frameType", "new");
			}
		}
		return mv;
	}
	
	//Save Frame_Info
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveFrameInfo(
			@ModelAttribute("objFrame") FrameClientType objFrame,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objFrame, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameType", "new");
			model.addAttribute("frameList", frameClientTypeService.findAll());
			return "framingCenter/frameClientType";
		}
		redirectAttributes.addFlashAttribute("frameType", "new");
		if(objFrame.getClientTypeId() == null){
			logger.info("Creating New Record...");
			objFrame.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			FrameClientType fInfo =  frameClientTypeService.findOne(objFrame.getClientTypeId());
			objFrame.setCreatedDate(fInfo.getCreatedDate());
		}
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		
		frameClientTypeService.save(objFrame);
		
		redirectAttributes.addFlashAttribute("objFrame", new FrameClientType());
		
		return "redirect:/framingCenter/frameClientType/viewAllFrameDetails";
	}
	
	//Delete Frame_Info
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameInfo(@RequestParam("frameId") Long id,
			@ModelAttribute("objFrame") FrameClientType objFrame,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		frameClientTypeService.delete(id);
		redirectAttributes.addFlashAttribute("objFrame", new FrameClientType());
		redirectAttributes.addFlashAttribute("frameType", "new");
		return "redirect:/framingCenter/frameClientType/viewAllFrameDetails";
	}
	
	//Edit Frame_Info
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameInfo(@RequestParam("frameId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameClientType fInfo =  frameClientTypeService.findOne(id);
		redirectAttributes.addFlashAttribute("objFrame", fInfo);
		redirectAttributes.addFlashAttribute("frameType", "update");
		return "redirect:/framingCenter/frameClientType/viewAllFrameDetails";
	}
	
}

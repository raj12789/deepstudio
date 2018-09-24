package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameThicknessValidator;
import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.service.FrameThicknessService;

import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping(value="/framingCenter/frameThickness")
public class FrameThicknessController {

	static Logger logger = LoggerFactory.getLogger(FrameThicknessController.class);
	@Autowired
	private FrameThicknessService frameThicknessService;
	@Autowired(required=false)
	private FrameThicknessValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameThickness");
		mv.addObject("frameList", frameThicknessService.findAll());
		mv.addObject("objFrame", new FrameThickness());
		mv.addObject("frameType", "new");
		return mv;
    }
	
	@ModelAttribute(value="measurement")
	public List<String> populateHoobiesList()
	{
		List<String> measurementList = new ArrayList<String>();
		measurementList.addAll(Arrays.asList(Constants.measurementList));
		logger.info("measurementList populated: "+measurementList);
		return measurementList;
	}
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objFrame") FrameThickness objFrame,
			@ModelAttribute("frameType") String frameType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameThickness");
		List<FrameThickness> frameInfoList = frameThicknessService.findAll();
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
			@ModelAttribute("objFrame") FrameThickness objFrame,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objFrame, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameType", "new");
			model.addAttribute("frameList", frameThicknessService.findAll());
			return "framingCenter/frameThickness";
		}
		redirectAttributes.addFlashAttribute("frameType", "new");
		if(objFrame.getFrameThicknessId() == null){
			logger.info("Creating New Record...");
			objFrame.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			FrameThickness fInfo =  frameThicknessService.findOne(objFrame.getFrameThicknessId());
			objFrame.setCreatedDate(fInfo.getCreatedDate());
		}
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		
		frameThicknessService.save(objFrame);
		
		redirectAttributes.addFlashAttribute("objFrame", new FrameThickness());
		
		return "redirect:/framingCenter/frameThickness/viewAllFrameDetails";
	}
	
	//Delete Frame_Info
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameInfo(@RequestParam("frameId") Long id,
			@ModelAttribute("objFrame") FrameThickness objFrame,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		frameThicknessService.delete(id);
		redirectAttributes.addFlashAttribute("objFrame", new FrameThickness());
		redirectAttributes.addFlashAttribute("frameType", "new");
		return "redirect:/framingCenter/frameThickness/viewAllFrameDetails";
	}
	
	//Edit Frame_Info
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameInfo(@RequestParam("frameId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameThickness fInfo =  frameThicknessService.findOne(id);
		redirectAttributes.addFlashAttribute("objFrame", fInfo);
		redirectAttributes.addFlashAttribute("frameType", "update");
		return "redirect:/framingCenter/frameThickness/viewAllFrameDetails";
	}
	
}

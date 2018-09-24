package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.CommonUtils;
import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameNumberValidator;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.service.FrameNumberService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/framingCenter/frameNumber")
public class FrameNumberController {

	static Logger logger = LoggerFactory.getLogger(FrameThicknessController.class);
	@Autowired
	private FrameNumberService frameNumberService;
	@Autowired(required=false)
	private FrameNumberValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameNumber");
		mv.addObject("frameList", frameNumberService.findAll());
		mv.addObject("objFrame", new FrameNumber());
		mv.addObject("frameType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objFrame") FrameNumber objFrame,
			@ModelAttribute("frameType") String frameType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameNumber");
		List<FrameNumber> frameInfoList = frameNumberService.findAll();
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
			@ModelAttribute("objFrame") FrameNumber objFrame,
			BindingResult result ,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objFrame, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameType", "new");
			model.addAttribute("frameList", frameNumberService.findAll());
			return "framingCenter/frameNumber";
		}
		redirectAttributes.addFlashAttribute("frameType", "new");
		if(objFrame.getFrameNumberId() == null){
			logger.info("Creating New Record...");
			objFrame.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			FrameNumber fInfo =  frameNumberService.findOne(objFrame.getFrameNumberId());
			objFrame.setCreatedDate(fInfo.getCreatedDate());
			objFrame.setFrameNumberPhotos(fInfo.getFrameNumberPhotos());
		}
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		
		
		if(!file.isEmpty()) {
			objFrame.setFrameNumberPhotos(CommonUtils.uploadFileHandler(file.getOriginalFilename(), file,request,"img_framenumber"));
		}
		
		frameNumberService.save(objFrame);
		
		redirectAttributes.addFlashAttribute("objFrame", new FrameNumber());
		
		return "redirect:/framingCenter/frameNumber/viewAllFrameDetails";
	}
	
	//Delete Frame_Info
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameInfo(@RequestParam("frameId") Long id,
			@ModelAttribute("objFrame") FrameNumber objFrame,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		frameNumberService.delete(id);
		redirectAttributes.addFlashAttribute("objFrame", new FrameNumber());
		redirectAttributes.addFlashAttribute("frameType", "new");
		return "redirect:/framingCenter/frameNumber/viewAllFrameDetails";
	}
	
	//Edit Frame_Info
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameInfo(@RequestParam("frameId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameNumber fInfo =  frameNumberService.findOne(id);
		redirectAttributes.addFlashAttribute("objFrame", fInfo);
		redirectAttributes.addFlashAttribute("frameType", "update");
		return "redirect:/framingCenter/frameNumber/viewAllFrameDetails";
	}
	
}


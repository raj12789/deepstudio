package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameDetailsValidator;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameInfoService;
import in.deepstudio.framingcentre.service.FrameNumberService;
import in.deepstudio.framingcentre.service.FrameSizeService;
import in.deepstudio.framingcentre.service.FrameThicknessService;

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
@RequestMapping(value="/framingCenter/frameDetails")
public class FrameDetailsController {
	static Logger logger = LoggerFactory.getLogger(FrameRateController.class);
	@Autowired
	private FrameDetailsService frameDetailsService;
	@Autowired
	private FrameDetailsValidator validator;
	@Autowired
	private FrameInfoService frameInfoService;
	@Autowired
	private FrameNumberService frameNumberService;
	@Autowired
	private FrameSizeService frameSizeService;
	@Autowired
	private FrameThicknessService frameThicknessService;
	
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameDetails");
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("frameDetailsType", "new");
		mv.addObject("frameDetailsList", frameDetailsService.findAll());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objFrameDetails") FrameDetails frameDetail,
			@ModelAttribute("frameDetailsType") String frameDetailsType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameDetails");
		List<FrameDetails> frameDetailsList = frameDetailsService.findAll();
		if(frameDetailsList !=null){
			mv.addObject("frameDetailsList", frameDetailsList);
			mv.addObject("objFrameDetails", frameDetail);
			mv.addObject("frameTypes", frameInfoService.findAll());
			mv.addObject("frameNumbers", frameNumberService.findAll());
			mv.addObject("frameSizes", frameSizeService.findAll());
			mv.addObject("frameThickness", frameThicknessService.findAll());
			if(!frameDetailsType.equalsIgnoreCase("")){
				mv.addObject("frameDetailsType", frameDetailsType);
			}else{
				mv.addObject("frameDetailsType", "new");
			}
		}
		return mv;
	}
	
	//Save Frame_Rate
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveFrameDetails(
			@ModelAttribute("objFrameDetails") FrameDetails frameDetail,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(frameDetail, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameDetailsType", "new");
			model.addAttribute("frameDetailsList", frameDetailsService.findAll());
			model.addAttribute("frameTypes", frameInfoService.findAll());
			model.addAttribute("frameNumbers", frameNumberService.findAll());
			model.addAttribute("frameSizes", frameSizeService.findAll());
			model.addAttribute("frameThickness", frameThicknessService.findAll());
			return "framingCenter/frameDetails";
		}
		redirectAttributes.addFlashAttribute("frameDetailsType", "new");
		if(frameDetail.getFrameQualifiedId() == null){
			logger.info("Creating New Record...");
			frameDetail.setCreatedDate(new Date());
		}else {
			logger.info("Updating Existing Record...");
			FrameDetails fDetails =  frameDetailsService.findOne(frameDetail.getFrameQualifiedId());
			frameDetail.setCreatedDate(fDetails.getCreatedDate());			
		}
		frameDetail.setUpdatedDate(new Date());
		frameDetail.setIpAddress(request.getRemoteAddr());
		
		frameDetailsService.save(frameDetail);
		
		redirectAttributes.addFlashAttribute("objFrameDetails", new FrameDetails());
		
		return "redirect:/framingCenter/frameDetails/viewAllFrameDetails";
	}
	
	//Delete Frame_Rate
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameRate(@RequestParam("frameQualifiedId") Long id,
			@ModelAttribute("objFrameDetails") FrameDetails frameDetail,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		frameDetail.setUpdatedDate(new Date());
		frameDetail.setIpAddress(request.getRemoteAddr());
		frameDetailsService.delete(id);
		redirectAttributes.addFlashAttribute("objFrameDetails", new FrameDetails());
		redirectAttributes.addFlashAttribute("frameDetailsType", "new");
		return "redirect:/framingCenter/frameDetails/viewAllFrameDetails";
	}
	
	//Edit Frame_Rate
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameRate(@RequestParam("frameQualifiedId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameDetails frameDetail =  frameDetailsService.findOne(id);
		redirectAttributes.addFlashAttribute("objFrameDetails", frameDetail);
		redirectAttributes.addFlashAttribute("frameDetailsType", "update");
		return "redirect:/framingCenter/frameDetails/viewAllFrameDetails";
	}
	
}

package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.CommonUtils;
import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameSizeValidator;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.service.FrameSizeService;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/framingCenter/frameSize")
public class FrameSizeController {

	static Logger logger = LoggerFactory.getLogger(FrameSizeController.class);
	@Autowired
	private FrameSizeService frameSizeService;
	@Autowired(required=false)
	private FrameSizeValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameSize");
		mv.addObject("frameList", frameSizeService.findAll());
		mv.addObject("objFrame", new FrameSize());
		mv.addObject("frameType", "new");
		return mv;
    }
	
	@ModelAttribute(value="measurement")
	public List<String> populateMeasurementList()
	{
		List<String> measurementList = new ArrayList<String>();
		measurementList.addAll(Arrays.asList(Constants.measurementList));
		logger.info("measurementList populated: "+measurementList);
		return measurementList;
	}
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objFrame") FrameSize objFrame,
			@ModelAttribute("frameType") String frameType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameSize");
		List<FrameSize> frameInfoList = frameSizeService.findAll();
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
			@ModelAttribute("objFrame") FrameSize objFrame,
			BindingResult result ,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objFrame, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameType", "new");
			model.addAttribute("frameList", frameSizeService.findAll());
			return "framingCenter/frameSize";
		}
		redirectAttributes.addFlashAttribute("frameType", "new");
		if(objFrame.getFrameSizeId() == null){
			logger.info("Creating New Record...");
			objFrame.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			FrameSize fInfo =  frameSizeService.findOne(objFrame.getFrameSizeId());
			objFrame.setCreatedDate(fInfo.getCreatedDate());
			objFrame.setFrameSizeChart(fInfo.getFrameSizeChart());
		}
		objFrame.setUpdatedDate(new Date());
		objFrame.setIpAddress(request.getRemoteAddr());
		
		if(!file.isEmpty()) {
			objFrame.setFrameSizeChart(CommonUtils.uploadFileHandler(file.getOriginalFilename(), file,request,"img_framesizechart"));
		}
		
		frameSizeService.save(objFrame);
		
		redirectAttributes.addFlashAttribute("objFrame", new FrameSize());
		
		return "redirect:/framingCenter/frameSize/viewAllFrameDetails";
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
		frameSizeService.delete(id);
		redirectAttributes.addFlashAttribute("objFrame", new FrameSize());
		redirectAttributes.addFlashAttribute("frameType", "new");
		return "redirect:/framingCenter/frameSize/viewAllFrameDetails";
	}
	
	//Edit Frame_Info
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameInfo(@RequestParam("frameId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameSize fInfo =  frameSizeService.findOne(id);
		redirectAttributes.addFlashAttribute("objFrame", fInfo);
		redirectAttributes.addFlashAttribute("frameType", "update");
		return "redirect:/framingCenter/frameSize/viewAllFrameDetails";
	}
	
}


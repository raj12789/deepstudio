package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.CommonUtils;
import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameInfoValidator;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.service.FrameInfoService;

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
@RequestMapping(value="/framingCenter/frameInfo")
public class FrameInfoController {
	static Logger logger = LoggerFactory.getLogger(FrameInfoController.class);
	@Autowired
	private FrameInfoService frameInfoService;
	@Autowired(required=false)
	private FrameInfoValidator validator;
	
	
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("framingCenter/frameInfo");
		mv.addObject("frameInfos", frameInfoService.findAll());
		mv.addObject("frameInfo", new FrameInfo());
		mv.addObject("frameInfoType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllFrameDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("frameInfo") FrameInfo frameInfo,
			@ModelAttribute("frameInfoType") String frameInfoType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameInfo");
		List<FrameInfo> frameInfoList = frameInfoService.findAll();
		if(frameInfoList !=null){
			mv.addObject("frameInfos", frameInfoList);
			mv.addObject("frameInfo", frameInfo);
			if(!frameInfoType.equalsIgnoreCase("")){
				mv.addObject("frameInfoType", frameInfoType);
			}else{
				mv.addObject("frameInfoType", "new");
			}
		}
		return mv;
	}
	
	//Save Frame_Info
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveFrameInfo(
			@ModelAttribute("frameInfo") FrameInfo frameInfo,
			BindingResult result ,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(frameInfo, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameInfoType", "new");
			model.addAttribute("frameInfos", frameInfoService.findAll());
			return "framingCenter/frameInfo";
		}
		redirectAttributes.addFlashAttribute("frameInfoType", "new");
		if(frameInfo.getFrameInfoId() == null){
			logger.info("Creating New Record...");
			frameInfo.setCreatedDate(new Date());
		}else {
			logger.info("Updating Existing Record...");
			FrameInfo fInfo =  frameInfoService.findOne(frameInfo.getFrameInfoId());
			frameInfo.setCreatedDate(fInfo.getCreatedDate());
			frameInfo.setFrameTypePhoto(fInfo.getFrameTypePhoto());
		}
		frameInfo.setUpdatedDate(new Date());
		frameInfo.setIpAddress(request.getRemoteAddr());
		
		if(!file.isEmpty()) {
			frameInfo.setFrameTypePhoto(CommonUtils.uploadFileHandler(file.getOriginalFilename(), file,request,"img_frameinfo"));
		}
		
		frameInfoService.save(frameInfo);
		
		redirectAttributes.addFlashAttribute("frameInfo", new FrameInfo());
		
		return "redirect:/framingCenter/frameInfo/viewAllFrameDetails";
	}
	
	//Delete Frame_Info
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameInfo(@RequestParam("frameInfoId") Long id,
			@ModelAttribute("frameInfo") FrameInfo frameInfo,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		frameInfo.setUpdatedDate(new Date());
		frameInfo.setIpAddress(request.getRemoteAddr());
		frameInfoService.delete(id);
		redirectAttributes.addFlashAttribute("frameInfo", new FrameInfo());
		redirectAttributes.addFlashAttribute("frameInfoType", "new");
		return "redirect:/framingCenter/frameInfo/viewAllFrameDetails";
	}
	
	//Edit Frame_Info
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editFrameInfo(@RequestParam("frameInfoId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		FrameInfo fInfo =  frameInfoService.findOne(id);
		redirectAttributes.addFlashAttribute("frameInfo", fInfo);
		redirectAttributes.addFlashAttribute("frameInfoType", "update");
		return "redirect:/framingCenter/frameInfo/viewAllFrameDetails";
	}
	
}
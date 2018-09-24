package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.CommonUtils;
import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.FrameRateValidator;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameRate;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.service.FrameClientTypeService;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameInfoService;
import in.deepstudio.framingcentre.service.FrameNumberService;
import in.deepstudio.framingcentre.service.FrameRateService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;

@Controller
@RequestMapping(value="/framingCenter/frameRate")
public class FrameRateController {
	static Logger logger = LoggerFactory.getLogger(FrameRateController.class);
	@Autowired
	private FrameRateService frameRateService;
	@Autowired(required=false)
	private FrameRateValidator validator;
	
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	
	@Autowired
	private FrameDetailsService frameDetailsService;
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
		ModelAndView mv = new ModelAndView("framingCenter/frameRate");
		mv.addObject("frameRates", frameRateService.findAll());
		mv.addObject("frameRate", new FrameRate());
		mv.addObject("frameRateType", "new");
		mv.addObject("objFrameDetails", new FrameDetails());
		mv.addObject("clientTypes", frameClientTypeService.findAll());
		mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllFrameRates"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("frameRate") FrameRate frameRate,
			@ModelAttribute("frameRateType") String frameRateType,
			@ModelAttribute("objFrameDetails") FrameDetails frameDetail){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("framingCenter/frameRate");
		List<FrameRate> frameRateList = frameRateService.findAll();
		if(frameRateList !=null){
			mv.addObject("frameDetails", frameDetailsService.findAll());
			mv.addObject("clientTypes", frameClientTypeService.findAll());
			mv.addObject("frameRates", frameRateList);
			mv.addObject("frameRate", frameRate);
			mv.addObject("objFrameDetails", frameDetail);
			mv.addObject("frameTypes", frameInfoService.findAll());
			mv.addObject("frameNumbers", frameNumberService.findAll());
			mv.addObject("frameSizes", frameSizeService.findAll());
			mv.addObject("frameThickness", frameThicknessService.findAll());
			if(!frameRateType.equalsIgnoreCase("")){
				mv.addObject("frameRateType", frameRateType);
			}else{
				mv.addObject("frameRateType", "new");
			}
		}
		return mv;
	}
	
	//Save Frame_Rate
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveFrameRate(
			@ModelAttribute("frameRate") FrameRate frameRate,
			BindingResult result ,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(frameRate, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("frameRateType", "new");
			model.addAttribute("frameRates", frameRateService.findAll());
			model.addAttribute("frameDetails", frameDetailsService.findAll());
			model.addAttribute("clientTypes", frameClientTypeService.findAll());
			model.addAttribute("objFrameDetails",new FrameDetails());
			model.addAttribute("frameTypes", frameInfoService.findAll());
			model.addAttribute("frameNumbers", frameNumberService.findAll());
			model.addAttribute("frameSizes", frameSizeService.findAll());
			model.addAttribute("frameThickness", frameThicknessService.findAll());
			return "framingCenter/frameRate";
		}
		redirectAttributes.addFlashAttribute("frameRateType", "new");
		if(frameRate.getFrameRateId() == null){
			logger.info("Creating New Record...");
			frameRate.setCreatedDate(new Date());
		}else {
			logger.info("Updating Existing Record...");
			FrameRate fRate =  frameRateService.findOne(frameRate.getFrameRateId());
			frameRate.setCreatedDate(fRate.getCreatedDate());	
			frameRate.setFrameRatePhoto(fRate.getFrameRatePhoto());
		}
		frameRate.setUpdatedDate(new Date());
		frameRate.setIpAddress(request.getRemoteAddr());
		if(!file.isEmpty()) {
			frameRate.setFrameRatePhoto(CommonUtils.uploadFileHandler(file.getOriginalFilename(), file,request,"img_framerate"));
		}
		frameRateService.save(frameRate);
		redirectAttributes.addFlashAttribute("frameRate", new FrameRate());
		return "redirect:/framingCenter/frameRate/viewAllFrameRates";
	}
	
	//Delete Frame_Rate
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteFrameRate(@RequestParam("frameRateId") Long id,
			@ModelAttribute("frameRate") FrameRate frameRate,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		frameRate.setUpdatedDate(new Date());
		frameRate.setIpAddress(request.getRemoteAddr());
		frameRateService.delete(id);
		redirectAttributes.addFlashAttribute("frameRate", new FrameRate());
		redirectAttributes.addFlashAttribute("frameRateType", "new");
		//redirectAttributes.addFlashAttribute("objFrameDetails", new FrameDetails());
		return "redirect:/framingCenter/frameRate/viewAllFrameRates";
	}
	
	//Edit Frame_Rate
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public ModelAndView editFrameRate(@RequestParam("frameRateId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		ModelAndView mv = new ModelAndView("framingCenter/frameRate");
		List<FrameRate> frameRateList = frameRateService.findAll();
		FrameRate fRate =  frameRateService.findOne(id);
		mv.addObject("frameDetails", frameDetailsService.findAll());
		mv.addObject("clientTypes", frameClientTypeService.findAll());
		mv.addObject("frameTypes", frameInfoService.findAll());
		mv.addObject("frameNumbers", frameNumberService.findAll());
		mv.addObject("frameSizes", frameSizeService.findAll());
		mv.addObject("frameThickness", frameThicknessService.findAll());
		FrameDetails framedetail = frameDetailsService.findOne(fRate.getFrameDetails().getFrameQualifiedId());
		if(framedetail != null){
			fRate.getFrameDetails().setFrameDescription(framedetail.getFrameInfo().getFrameType()+","+framedetail.getFrameNumber().getFrameNumber()+","+framedetail.getFrameThickness().getFrameThicknessSize()+" "+framedetail.getFrameThickness().getFrameMeasurementsType()+","+framedetail.getFrameSize().getFrameSizeWidth()+"*"+framedetail.getFrameSize().getFrameSizeHeight()+" "+framedetail.getFrameSize().getFrameMeasurementsType());;
		}
		mv.addObject("frameRate", fRate);
		mv.addObject("frameRateType", "update");
		mv.addObject("frameRates", frameRateList);
		mv.addObject("objFrameDetails", new FrameDetails());
		return mv;
	}
	
	@RequestMapping(value=Constants.FIND_FRAMES,method = RequestMethod.GET,headers ="Accept=*/*")
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
package in.deepstudio.framingcentre.controller;

import in.deepstudio.framingcentre.service.FrameClientTypeService;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameInfoService;
import in.deepstudio.framingcentre.service.FrameNumberService;
import in.deepstudio.framingcentre.service.FrameRateService;
import in.deepstudio.framingcentre.service.FrameSizeService;
import in.deepstudio.framingcentre.service.FrameThicknessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
	
	static Logger logger = LoggerFactory.getLogger(FrameInfoController.class);
	/*Frame Type*/
	@Autowired
	private FrameInfoService frameInfoService;
	/*Frame Number*/
	@Autowired
	private FrameNumberService frameNumberService;
	/*Frame Thickness*/
	@Autowired
	private FrameThicknessService frameThicknessService;
	/*Frame Size*/
	@Autowired
	private FrameSizeService frameSizeService;
	/*Frame Client Type*/
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	/*Frame Detail*/
	@Autowired
	private FrameDetailsService frameDetailsService;
	/*Frame Rate*/
	@Autowired
	private FrameRateService frameRateService;
	
	
	@RequestMapping("/")
	public ModelAndView index() {		
		logger.info("Index.html....");
		return new ModelAndView("framingCenter/index");
	}
	
	@RequestMapping("/welcome")
	public ModelAndView welcome() {		
		logger.info("Welcome.html....");
		return new ModelAndView("framingCenter/welcome");
	}
	
	@RequestMapping("/work/photo_videography")
	public ModelAndView Photo_Videography() {		
		logger.info("Photo_Videography.html....");
		return new ModelAndView("work/Photography_Videography");
	}
	@RequestMapping("/work/religious")
	public ModelAndView religious() {		
		logger.info("Religious.html....");
		return new ModelAndView("work/Religious");
	}
	@RequestMapping("/work/framesCasting")
	public ModelAndView framesCasting() {		
		logger.info("framesCasting.html....");
		return new ModelAndView("work/FramesCasting");
	}
	@RequestMapping("/work/printingWork")
	public ModelAndView printingWork() {		
		logger.info("framesCasting.html....");
		return new ModelAndView("work/PrintingWork");
	}
	
	@RequestMapping("/about")
	public ModelAndView about() {		
		logger.info("About.html....");
		return new ModelAndView("framingCenter/about");
	}
	
}

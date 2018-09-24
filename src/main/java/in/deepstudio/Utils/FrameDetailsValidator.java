package in.deepstudio.Utils;

import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameInfoService;
import in.deepstudio.framingcentre.service.FrameNumberService;
import in.deepstudio.framingcentre.service.FrameSizeService;
import in.deepstudio.framingcentre.service.FrameThicknessService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FrameDetailsValidator implements Validator {
	static Logger logger = LoggerFactory.getLogger(FrameDetailsValidator.class);
	
	@Autowired
	private FrameInfoService frameInfoService;
	@Autowired
	private FrameNumberService frameNumberService;
	@Autowired
	private FrameSizeService frameSizeService;
	@Autowired
	private FrameThicknessService frameThicknessService;
	@Autowired
	private FrameDetailsService frameDetailsService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameDetails.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		//ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"price", "required.price"," price is Required (Default Message)");
		FrameDetails frameDetails = (FrameDetails) arg0;
		
		if(frameDetails.getFrameQualifiedId() == null){
			FrameInfo frameInfo = frameInfoService.findOne(frameDetails.getFrameInfo().getFrameInfoId());
			FrameNumber frameNumber = frameNumberService.findOne(frameDetails.getFrameNumber().getFrameNumberId());
			FrameThickness framethickness = frameThicknessService.findOne(frameDetails.getFrameThickness().getFrameThicknessId());
			FrameSize framesize = frameSizeService.findOne(frameDetails.getFrameSize().getFrameSizeId());
			if(frameDetailsService.findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(frameInfo, frameNumber, framesize,framethickness) != null){
	    			arg1.rejectValue("frameQualifiedId", "uniqueConstraintViolation.frameDetails","Frame Detail combination is already added. (Default Message)");	
	    	}
		}
	}
	
	
}


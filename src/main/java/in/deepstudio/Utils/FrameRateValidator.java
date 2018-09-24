package in.deepstudio.Utils;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameRate;
import in.deepstudio.framingcentre.service.FrameClientTypeService;
import in.deepstudio.framingcentre.service.FrameDetailsService;
import in.deepstudio.framingcentre.service.FrameRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FrameRateValidator implements Validator {
	static Logger logger = LoggerFactory.getLogger(FrameInfoValidator.class);
	
	@Autowired
	private FrameDetailsService frameDetailsService;
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	@Autowired
	private FrameRateService frameRateService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameRate.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		
		FrameRate frameRate = (FrameRate) arg0;
		if(frameRate.getFrameDetails().getFrameQualifiedId() == null){
			arg1.rejectValue("frameDetails","required.frameDetails","Frame Details Can not be unselected (Default Message)");
		}else{
			if(frameRate.getFrameRateId() == null){
				FrameDetails frameDetail = frameDetailsService.findOne(frameRate.getFrameDetails().getFrameQualifiedId());
		    	FrameClientType frameClientType = frameClientTypeService.findOne(frameRate.getFrameClientType().getClientTypeId());
		    	if(frameRateService.findByFrameDetailsAndFrameClientType(frameDetail, frameClientType) != null){
		    			arg1.rejectValue("frameRateId", "uniqueConstraintViolation.frameRate","Frame Detail and Frame Client Type combination is already added (Default Message)");	
		    	}
		    	ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"price", "required.price"," price is Required (Default Message)");
			}
		}
		
		
	}
}

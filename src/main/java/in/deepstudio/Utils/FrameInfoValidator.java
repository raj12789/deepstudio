package in.deepstudio.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.service.FrameInfoService;


@Component
public class FrameInfoValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(FrameInfoValidator.class);
	
	@Autowired
	private FrameInfoService frameInfoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameType", "required.frameType"," FrameType is Required (Default Message)");		
		
		//TODO : unique constraint not working as it also check while updating, need to update the logic with ajax call
		
		FrameInfo frameInfo = (FrameInfo) arg0;
		
		if(frameInfo.getFrameInfoId() == null &&  frameInfoService.findByFrameType(frameInfo.getFrameType()) != null){
			arg1.rejectValue("frameType", "uniqueConstraintViolation.frameType","Frame Type is already exists (Default Message)");
		}
	
	}
	
	
	
}

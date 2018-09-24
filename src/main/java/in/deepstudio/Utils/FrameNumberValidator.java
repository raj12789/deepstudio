
package in.deepstudio.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.service.FrameNumberService;
@Component
public class FrameNumberValidator implements Validator{


	static Logger logger = LoggerFactory.getLogger(FrameNumberValidator.class);
	
	@Autowired
	private FrameNumberService frameNumberService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameNumber.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameNumber", "required.frameNumber"," Frame Number is Required (Default Message)");
		
		//TODO : Need to update logic for this one because it cause problem at update time
		FrameNumber frameNumber = (FrameNumber) arg0;
		
		if(frameNumber.getFrameNumberId() == null && frameNumberService.findByFrameNumber(frameNumber.getFrameNumber()) != null){
			arg1.rejectValue("frameNumber", "uniqueConstraintViolation.frameNumber","Frame Number is already exists (Default Message)");
		}
	
	}
	
	
	
}

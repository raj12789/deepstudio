package in.deepstudio.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.service.FrameClientTypeService;


@Component
public class FrameClientTypeValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(FrameClientTypeValidator.class);
	
	@Autowired
	private FrameClientTypeService frameClientTypeService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameClientType.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"clientTypeName", "required.clientTypeName","FrameclientTypeName is Required (Default Message)");
		
		//TODO : Need to think something else instead of this because it cause error at update time
		FrameClientType frameClientType = (FrameClientType) arg0;
		
		if(frameClientType.getClientTypeId() == null && frameClientTypeService.findByClientTypeName(frameClientType.getClientTypeName()) != null){
			arg1.rejectValue("clientTypeName", "uniqueConstraintViolation.clientTypeName","FrameClientType Name is already exists (Default Message)");
		}
	
	}
	
	
	
}

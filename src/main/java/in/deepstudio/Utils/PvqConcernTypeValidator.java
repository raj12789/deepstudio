package in.deepstudio.Utils;

import in.deepstudio.pvq.domain.PvqConcernType;
import in.deepstudio.pvq.service.PvqConcernTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PvqConcernTypeValidator implements Validator{

static Logger logger = LoggerFactory.getLogger(PvqConcernTypeValidator.class);
	
	@Autowired
	private PvqConcernTypeService pvqConcernTypeService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PvqConcernType.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"pvqConcernTypeName", "required.pvqConcernTypeName","PvqConcernTypeName is Required (Default Message)");
		
		//TODO : Need to think something else instead of this because it cause error at update time
		PvqConcernType pvqConcernType = (PvqConcernType) arg0;
		
		if(pvqConcernType.getPvqConcernTypeId() == null && pvqConcernTypeService.findByPvqConcernTypeName(pvqConcernType.getPvqConcernTypeName()) != null){
			arg1.rejectValue("pvqConcernTypeName", "uniqueConstraintViolation.pvqConcernTypeName","PvqConcernTypeName Name is already exists (Default Message)");
		}
	
	}
	
}

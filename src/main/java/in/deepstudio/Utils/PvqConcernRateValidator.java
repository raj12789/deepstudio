package in.deepstudio.Utils;

import in.deepstudio.pvq.domain.PvqConcernRate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PvqConcernRateValidator implements Validator{
	
	static Logger logger = LoggerFactory.getLogger(PvqConcernRateValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PvqConcernRate.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"pvqConcernName", "required.pvqConcernName","PvqConcernName is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"pvqConcernRateAmt", "required.pvqConcernRateAmt","PvqConcernRateAmt is Required (Default Message)");
	}
	
}

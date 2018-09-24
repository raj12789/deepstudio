package in.deepstudio.Utils;

import in.deepstudio.expensemanager.domain.ExpenseType;
import in.deepstudio.expensemanager.service.ExpenseTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ExpenseTypeValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(ExpenseTypeValidator.class);
	
	@Autowired
	private ExpenseTypeService expenseTypeService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ExpenseType.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"expenseTypeName", "required.expenseTypeName","ExpenseTypeName is Required (Default Message)");
		
		//TODO : Need to think something else instead of this because it cause error at update time
		ExpenseType expenseType = (ExpenseType) arg0;
		
		if(expenseType.getExpenseTypeId() == null && expenseTypeService.findByExpenseTypeName(expenseType.getExpenseTypeName()) != null){
			arg1.rejectValue("expenseTypeName", "uniqueConstraintViolation.expenseTypeName","ExpenseTypeName Name is already exists (Default Message)");
		}
	
	}
	
	
}

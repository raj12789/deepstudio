package in.deepstudio.Utils;

import in.deepstudio.expensemanager.domain.ExpenseBillBook;
import in.deepstudio.expensemanager.service.ExpenseBillBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ExpenseBillBookValidator implements Validator{

static Logger logger = LoggerFactory.getLogger(ExpenseBillBookValidator.class);
	
	@Autowired
	private ExpenseBillBookService expenseBillBookService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ExpenseBillBook.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"expenseDate", "required.expenseDate","ExpenseDate is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"expenseAmt", "required.expenseAmt","ExpenseAmt is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"expenseDesc", "required.expenseDesc","ExpenseDesc is Required (Default Message)");
	
	}
	
}

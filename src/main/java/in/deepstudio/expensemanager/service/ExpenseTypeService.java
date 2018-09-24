package in.deepstudio.expensemanager.service;

import in.deepstudio.expensemanager.domain.ExpenseType;
import java.util.List;

public interface ExpenseTypeService {

	ExpenseType save(ExpenseType c);
	
	List<ExpenseType> findAll();
	
	void  delete(Long id);
	
	ExpenseType findOne(Long id);
	
	ExpenseType findByExpenseTypeName(String expenseTypeName);
	
}

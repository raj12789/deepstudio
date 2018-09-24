package in.deepstudio.expensemanager.service;

import in.deepstudio.expensemanager.domain.ExpenseBillBook;
import java.util.List;

public interface ExpenseBillBookService {

	ExpenseBillBook save(ExpenseBillBook c);
	
	List<ExpenseBillBook> findAll();
	
	void  delete(Long id);
	
	ExpenseBillBook findOne(Long id);
	
}

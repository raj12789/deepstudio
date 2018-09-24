package in.deepstudio.expensemanager.repository;

import in.deepstudio.expensemanager.domain.ExpenseType;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseTypeRepository extends CrudRepository<ExpenseType, Long> {

	@Override
	public <S extends ExpenseType> S save(S arg0);
	
	@Override
	public Iterable<ExpenseType> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	ExpenseType findOne(Long id);
	
	ExpenseType findByExpenseTypeName(String expenseTypeName);
	
}

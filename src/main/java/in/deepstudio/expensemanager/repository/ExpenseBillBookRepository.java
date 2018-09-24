package in.deepstudio.expensemanager.repository;

import org.springframework.data.repository.CrudRepository;

import in.deepstudio.expensemanager.domain.ExpenseBillBook;

public interface ExpenseBillBookRepository extends CrudRepository<ExpenseBillBook, Long>{

	@Override
	public <S extends ExpenseBillBook> S save(S arg0);
	
	@Override
	public Iterable<ExpenseBillBook> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	ExpenseBillBook findOne(Long id);
	
}

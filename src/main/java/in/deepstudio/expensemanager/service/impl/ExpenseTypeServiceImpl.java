package in.deepstudio.expensemanager.service.impl;

import java.util.List;

import in.deepstudio.expensemanager.domain.ExpenseType;
import in.deepstudio.expensemanager.repository.ExpenseTypeRepository;
import in.deepstudio.expensemanager.service.ExpenseTypeService;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("expenseTypeService")
@Transactional
public class ExpenseTypeServiceImpl implements ExpenseTypeService{

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	
	@Override
	public ExpenseType save(ExpenseType c) {
		return expenseTypeRepository.save(c);
	}

	@Override
	public List<ExpenseType> findAll() {
		return Lists.newArrayList(expenseTypeRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(expenseTypeRepository.exists(id)){
			expenseTypeRepository.delete(id);
		}
	}

	@Override
	public ExpenseType findOne(Long id) {
		// TODO Auto-generated method stub
		return expenseTypeRepository.findOne(id);
	}

	@Override
	public ExpenseType findByExpenseTypeName(String expenseTypeService) {
		// TODO Auto-generated method stub
		return expenseTypeRepository.findByExpenseTypeName(expenseTypeService);
	}

}

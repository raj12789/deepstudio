package in.deepstudio.expensemanager.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.expensemanager.domain.ExpenseBillBook;
import in.deepstudio.expensemanager.repository.ExpenseBillBookRepository;
import in.deepstudio.expensemanager.service.ExpenseBillBookService;

@Component("expenseBillBookService")
@Transactional
public class ExpenseBillBookServiceImpl implements ExpenseBillBookService{

	@Autowired
	private ExpenseBillBookRepository expenseBillBookRepository;
	
	@Override
	public ExpenseBillBook save(ExpenseBillBook c) {
		return expenseBillBookRepository.save(c);
	}

	@Override
	public List<ExpenseBillBook> findAll() {
		return Lists.newArrayList(expenseBillBookRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(expenseBillBookRepository.exists(id)){
			expenseBillBookRepository.delete(id);
		}
	}

	@Override
	public ExpenseBillBook findOne(Long id) {
		// TODO Auto-generated method stub
		return expenseBillBookRepository.findOne(id);
	}

	
}

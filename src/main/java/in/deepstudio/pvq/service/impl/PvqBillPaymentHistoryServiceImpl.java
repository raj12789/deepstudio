package in.deepstudio.pvq.service.impl;

import java.util.List;

import in.deepstudio.pvq.domain.PvqBillPaymentHistory;
import in.deepstudio.pvq.repository.PvqBillPaymentHistoryRepository;
import in.deepstudio.pvq.service.PvqBillPaymentHistoryService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("pvqBillPaymentHistoryService")
@Transactional
public class PvqBillPaymentHistoryServiceImpl implements PvqBillPaymentHistoryService{

	@Autowired
	private PvqBillPaymentHistoryRepository pvqBillPaymentHistoryRepository;
	
	@Override
	public PvqBillPaymentHistory save(PvqBillPaymentHistory c) {
		return pvqBillPaymentHistoryRepository.save(c);
	}

	@Override
	public List<PvqBillPaymentHistory> findAll() {
		return Lists.newArrayList(pvqBillPaymentHistoryRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqBillPaymentHistoryRepository.exists(id)){
			pvqBillPaymentHistoryRepository.delete(id);
		}
	}

	@Override
	public PvqBillPaymentHistory findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqBillPaymentHistoryRepository.findOne(id);
	}

}

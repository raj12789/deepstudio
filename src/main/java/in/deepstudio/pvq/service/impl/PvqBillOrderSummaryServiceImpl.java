package in.deepstudio.pvq.service.impl;

import in.deepstudio.pvq.domain.PvqBillOrderSummary;
import in.deepstudio.pvq.repository.PvqBillOrderSummaryrRepository;
import in.deepstudio.pvq.service.PvqBillOrderSummaryService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("pvqBillOrderSummaryService")
@Transactional
public class PvqBillOrderSummaryServiceImpl implements PvqBillOrderSummaryService{

	@Autowired
	private PvqBillOrderSummaryrRepository pvqBillOrderSummaryrRepository;
	
	@Override
	public PvqBillOrderSummary save(PvqBillOrderSummary c) {
		return pvqBillOrderSummaryrRepository.save(c);
	}

	@Override
	public List<PvqBillOrderSummary> findAll() {
		return Lists.newArrayList(pvqBillOrderSummaryrRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqBillOrderSummaryrRepository.exists(id)){
			pvqBillOrderSummaryrRepository.delete(id);
		}
	}

	@Override
	public PvqBillOrderSummary findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqBillOrderSummaryrRepository.findOne(id);
	}
}

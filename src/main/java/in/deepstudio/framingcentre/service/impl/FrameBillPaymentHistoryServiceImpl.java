package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import in.deepstudio.framingcentre.domain.FrameBillPaymentHistory;
import in.deepstudio.framingcentre.repository.FrameBillPaymentHistoryRepository;
import in.deepstudio.framingcentre.service.FrameBillPaymentHistoryService;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("frameBillPaymentHistoryService")
@Transactional
public class FrameBillPaymentHistoryServiceImpl implements
FrameBillPaymentHistoryService{

	
	@Autowired
	private FrameBillPaymentHistoryRepository frameBillPaymentHistoryRepository;
	
	@Override
	public FrameBillPaymentHistory save(FrameBillPaymentHistory c) {
		return frameBillPaymentHistoryRepository.save(c);
	}

	@Override
	public List<FrameBillPaymentHistory> findAll() {
		return Lists.newArrayList(frameBillPaymentHistoryRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameBillPaymentHistoryRepository.exists(id)){
			frameBillPaymentHistoryRepository.delete(id);
		}
	}

	@Override
	public FrameBillPaymentHistory findOne(Long id) {
		// TODO Auto-generated method stub
		return frameBillPaymentHistoryRepository.findOne(id);
	}
	
}

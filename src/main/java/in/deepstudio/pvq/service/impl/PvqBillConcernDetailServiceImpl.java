package in.deepstudio.pvq.service.impl;

import java.util.List;

import in.deepstudio.pvq.domain.PvqBillConcernDetail;
import in.deepstudio.pvq.repository.PvqBillConcernDetailRepository;
import in.deepstudio.pvq.service.PvqBillConcernDetailService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("pvqBillConcernDetailService")
@Transactional
public class PvqBillConcernDetailServiceImpl implements PvqBillConcernDetailService{

	
	@Autowired
	private PvqBillConcernDetailRepository pvqBillConcernDetailRepository;
	
	@Override
	public PvqBillConcernDetail save(PvqBillConcernDetail c) {
		return pvqBillConcernDetailRepository.save(c);
	}

	@Override
	public List<PvqBillConcernDetail> findAll() {
		return Lists.newArrayList(pvqBillConcernDetailRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqBillConcernDetailRepository.exists(id)){
			pvqBillConcernDetailRepository.delete(id);
		}
	}

	@Override
	public PvqBillConcernDetail findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqBillConcernDetailRepository.findOne(id);
	}

	
}

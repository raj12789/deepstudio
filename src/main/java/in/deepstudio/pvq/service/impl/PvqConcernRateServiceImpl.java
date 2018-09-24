package in.deepstudio.pvq.service.impl;

import java.util.List;

import in.deepstudio.pvq.domain.PvqConcernRate;
import in.deepstudio.pvq.domain.PvqConcernType;
import in.deepstudio.pvq.repository.PvqConcernRateRepository;
import in.deepstudio.pvq.service.PvqConcernRateService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("pvqConcernRateService")
@Transactional
public class PvqConcernRateServiceImpl implements PvqConcernRateService{

	@Autowired
	private PvqConcernRateRepository pvqConcernRateRepository;
	
	@Override
	public PvqConcernRate save(PvqConcernRate c) {
		return pvqConcernRateRepository.save(c);
	}

	@Override
	public List<PvqConcernRate> findAll() {
		return Lists.newArrayList(pvqConcernRateRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqConcernRateRepository.exists(id)){
			pvqConcernRateRepository.delete(id);
		}
	}

	@Override
	public PvqConcernRate findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqConcernRateRepository.findOne(id);
	}

	@Override
	public PvqConcernRate findByPvqConcernTypeAndPvqConcernName(
			PvqConcernType pvqConcernType, String pvqConcernName) {
		// TODO Auto-generated method stub
		return pvqConcernRateRepository.findByPvqConcernTypeAndPvqConcernName(pvqConcernType, pvqConcernName);
	}

	

	
	
}

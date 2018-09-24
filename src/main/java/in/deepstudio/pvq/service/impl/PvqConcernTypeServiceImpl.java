package in.deepstudio.pvq.service.impl;

import in.deepstudio.pvq.domain.PvqConcernType;
import in.deepstudio.pvq.repository.PvqConcernTypeRepository;
import in.deepstudio.pvq.service.PvqConcernTypeService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
@Component("pvqConcernTypeService")
@Transactional
public class PvqConcernTypeServiceImpl implements PvqConcernTypeService{

	@Autowired
	private PvqConcernTypeRepository pvqConcernTypeRepository;
	
	@Override
	public PvqConcernType save(PvqConcernType c) {
		return pvqConcernTypeRepository.save(c);
	}

	@Override
	public List<PvqConcernType> findAll() {
		return Lists.newArrayList(pvqConcernTypeRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqConcernTypeRepository.exists(id)){
			pvqConcernTypeRepository.delete(id);
		}
	}

	@Override
	public PvqConcernType findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqConcernTypeRepository.findOne(id);
	}

	@Override
	public PvqConcernType findByPvqConcernTypeName(String pvqConcernTypeName) {
		// TODO Auto-generated method stub
		return pvqConcernTypeRepository.findByPvqConcernTypeName(pvqConcernTypeName);
	}

	
}

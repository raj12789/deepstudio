package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqConcernRate;
import in.deepstudio.pvq.domain.PvqConcernType;

import java.util.List;

public interface PvqConcernRateService {

	PvqConcernRate save(PvqConcernRate c);
	
	List<PvqConcernRate> findAll();
	
	void  delete(Long id);
	
	PvqConcernRate findOne(Long id);
	
	PvqConcernRate findByPvqConcernTypeAndPvqConcernName(PvqConcernType pvqConcernType,String pvqConcernName);
	
}

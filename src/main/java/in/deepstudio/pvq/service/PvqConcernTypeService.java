package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqConcernType;

import java.util.List;

public interface PvqConcernTypeService {
		
	PvqConcernType save(PvqConcernType c);
	
	List<PvqConcernType> findAll();
	
	void  delete(Long id);
	
	PvqConcernType findOne(Long id);
	
	PvqConcernType findByPvqConcernTypeName(String pvqConcernTypeName);
}

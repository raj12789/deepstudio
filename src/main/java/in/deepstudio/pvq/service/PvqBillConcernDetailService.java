package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqBillConcernDetail;
import java.util.List;

public interface PvqBillConcernDetailService {

	PvqBillConcernDetail save(PvqBillConcernDetail c);
	
	List<PvqBillConcernDetail> findAll();
	
	void  delete(Long id);
	
	PvqBillConcernDetail findOne(Long id);
	
}

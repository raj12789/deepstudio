package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqBillOrderSummary;
import java.util.List;

public interface PvqBillOrderSummaryService {

	PvqBillOrderSummary save(PvqBillOrderSummary c);
	
	List<PvqBillOrderSummary> findAll();
	
	void  delete(Long id);
	
	PvqBillOrderSummary findOne(Long id);
	
}

package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqBillPaymentHistory;

import java.util.List;

public interface PvqBillPaymentHistoryService {

	PvqBillPaymentHistory save(PvqBillPaymentHistory c);
	
	List<PvqBillPaymentHistory> findAll();
	
	void  delete(Long id);
	
	PvqBillPaymentHistory findOne(Long id);
	
}

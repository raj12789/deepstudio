package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameBillPaymentHistory;
import java.util.List;

public interface FrameBillPaymentHistoryService {

	FrameBillPaymentHistory save(FrameBillPaymentHistory c);
	
	List<FrameBillPaymentHistory> findAll();
	
	void  delete(Long id);
	
	FrameBillPaymentHistory findOne(Long id);
	
}

package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import java.util.List;

public interface FrameCustBillingInfoService {
	FrameCustBillingInfo save(FrameCustBillingInfo c);
	
	List<FrameCustBillingInfo> findAll();
	
	void  delete(Long id);
	
	FrameCustBillingInfo findOne(Long id);
	
	String findByCustBillNumber(String custBillNumber);
}

package in.deepstudio.pvq.service;

import in.deepstudio.pvq.domain.PvqBillInfo;

import java.util.List;

public interface PvqBillInfoService {

	PvqBillInfo save(PvqBillInfo c);
	
	List<PvqBillInfo> findAll();
	
	void  delete(Long id);
	
	PvqBillInfo findOne(Long id);
	
	PvqBillInfo findByBillNumber(String billNumber);
}

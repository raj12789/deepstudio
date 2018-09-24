package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfoDetail;

import java.util.List;

public interface FrameCustBillingInfoDetailService {
	FrameCustBillingInfoDetail save(FrameCustBillingInfoDetail c);
	
	List<FrameCustBillingInfoDetail> findAll();
	
	void  delete(Long id);
	
	FrameCustBillingInfoDetail findOne(Long id);
}

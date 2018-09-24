package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameRate;

import java.util.List;

public interface FrameRateService {
	FrameRate save(FrameRate c);
	
	List<FrameRate> findAll();
	
	void  delete(Long id);
	
	FrameRate findOne(Long id);
	
	FrameRate findByFrameDetailsAndFrameClientType(FrameDetails frameId,FrameClientType clientTypeId);
}

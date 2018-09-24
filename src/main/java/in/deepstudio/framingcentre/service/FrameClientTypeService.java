package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameClientType;

import java.util.List;

public interface FrameClientTypeService {
	FrameClientType save(FrameClientType c);
	
	List<FrameClientType> findAll();
	
	void  delete(Long id);
	
	FrameClientType findOne(Long id);
	
	FrameClientType findByClientTypeName(String clientTypeName);
}

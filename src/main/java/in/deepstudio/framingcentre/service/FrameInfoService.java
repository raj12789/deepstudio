package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameInfo;
import java.util.List;

public interface FrameInfoService {

	FrameInfo save(FrameInfo c);
	
	List<FrameInfo> findAll();
	
	void  delete(Long id);
	
	FrameInfo findOne(Long id);
	
	boolean exists(Long uniqueKey);

	FrameInfo findByFrameType(String frameType);
}

package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameNumber;

import java.util.List;

public interface FrameNumberService {
	FrameNumber save(FrameNumber c);
	
	List<FrameNumber> findAll();
	
	void  delete(Long id);
	
	FrameNumber findOne(Long id);
	
	FrameNumber findByFrameNumber(String frameNumber);
}

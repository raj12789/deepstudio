package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;

import java.util.List;

public interface FrameDetailsService {
	FrameDetails save(FrameDetails c);
	
	List<FrameDetails> findAll();
	
	void  delete(Long id);
	
	FrameDetails findOne(Long id);
	
	FrameDetails findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(FrameInfo frameInfoId,FrameNumber frameNumberId,FrameSize framesizeId,FrameThickness framethicknessId);
}

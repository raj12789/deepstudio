package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameThickness;

import java.util.List;

public interface FrameThicknessService {
	FrameThickness save(FrameThickness c);
	
	List<FrameThickness> findAll();
	
	void  delete(Long id);
	
	FrameThickness findOne(Long id);
	
	FrameThickness findByFrameThicknessSizeAndFrameMeasurementsType(String frameThickness,String frameMeasurementsType);
}

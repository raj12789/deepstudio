package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.FrameSize;
import java.util.List;

public interface FrameSizeService {
	FrameSize save(FrameSize c);
	
	List<FrameSize> findAll();
	
	void  delete(Long id);
	
	FrameSize findOne(Long id);
	
	FrameSize findByFrameSizeWidthAndFrameSizeHeightAndFrameMeasurementsType(int width,int height,String measurementType);
	
}

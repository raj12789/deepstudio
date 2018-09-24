package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.repository.FrameThicknessRepository;
import in.deepstudio.framingcentre.service.FrameThicknessService;

@Component("frameThicknessService")
@Transactional
public class FrameThicknessServiceImpl implements FrameThicknessService {

	@Autowired
	private FrameThicknessRepository frameThicknessRepository;
	
	@Override
	public FrameThickness save(FrameThickness c) {		
		return frameThicknessRepository.save(c);
	}

	@Override
	public List<FrameThickness> findAll() {
		return Lists.newArrayList(frameThicknessRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameThicknessRepository.exists(id)){
			frameThicknessRepository.delete(id);
		}
	}

	@Override
	public FrameThickness findOne(Long id) {
		// TODO Auto-generated method stub
		return frameThicknessRepository.findOne(id);
	}

	@Override
	public FrameThickness findByFrameThicknessSizeAndFrameMeasurementsType(String frameThickness,String frameMeasurementsType) {
		// TODO Auto-generated method stub
		return frameThicknessRepository.findByFrameThicknessSizeAndFrameMeasurementsType(frameThickness,frameMeasurementsType);
	}

}

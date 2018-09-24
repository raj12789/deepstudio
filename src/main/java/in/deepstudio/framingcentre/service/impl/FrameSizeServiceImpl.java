package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.repository.FrameSizeRepository;
import in.deepstudio.framingcentre.service.FrameSizeService;

@Component("frameSizeService")
@Transactional
public class FrameSizeServiceImpl implements FrameSizeService {

	@Autowired
	private FrameSizeRepository frameSizeRepository;
	
	@Override
	public FrameSize save(FrameSize c) {
		return frameSizeRepository.save(c);
	}

	@Override
	public List<FrameSize> findAll() {
		return Lists.newArrayList(frameSizeRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameSizeRepository.exists(id)){
			frameSizeRepository.delete(id);
		}
	}

	@Override
	public FrameSize findOne(Long id) {
		// TODO Auto-generated method stub
		return frameSizeRepository.findOne(id);
	}

	@Override
	public FrameSize findByFrameSizeWidthAndFrameSizeHeightAndFrameMeasurementsType(int width,int height,String measurementType) {
		// TODO Auto-generated method stub
		return frameSizeRepository.findByFrameSizeWidthAndFrameSizeHeightAndFrameMeasurementsType(width,height,measurementType);
	}

	
	

}

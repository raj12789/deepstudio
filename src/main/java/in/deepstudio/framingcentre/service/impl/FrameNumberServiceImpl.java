package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.repository.FrameNumberRepository;
import in.deepstudio.framingcentre.service.FrameNumberService;

@Component("frameNumberService")
@Transactional
public class FrameNumberServiceImpl implements FrameNumberService {

	@Autowired
	private FrameNumberRepository frameNumberRepository;
	
	@Override
	public FrameNumber save(FrameNumber c) {		
		return frameNumberRepository.save(c);
	}

	@Override
	public List<FrameNumber> findAll() {
		return Lists.newArrayList(frameNumberRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameNumberRepository.exists(id)){
			frameNumberRepository.delete(id);
		}
	}

	@Override
	public FrameNumber findOne(Long id) {
		// TODO Auto-generated method stub
		return frameNumberRepository.findOne(id);
	}

	@Override
	public FrameNumber findByFrameNumber(String frameNumber) {
		// TODO Auto-generated method stub
		return frameNumberRepository.findByFrameNumber(frameNumber);
	}

}

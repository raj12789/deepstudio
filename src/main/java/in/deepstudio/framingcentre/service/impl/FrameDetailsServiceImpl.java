package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.repository.FrameDetailsRepository;
import in.deepstudio.framingcentre.service.FrameDetailsService;

@Component("frameDetailsService")
@Transactional
public class FrameDetailsServiceImpl implements FrameDetailsService {

	@Autowired
	private FrameDetailsRepository frameDetailsRepository;
	
	@Override
	public FrameDetails save(FrameDetails c) {
		return frameDetailsRepository.save(c);
	}

	@Override
	public List<FrameDetails> findAll() {
		return Lists.newArrayList(frameDetailsRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameDetailsRepository.exists(id)){
			frameDetailsRepository.delete(id);
		}
	}

	@Override
	public FrameDetails findOne(Long id) {
		// TODO Auto-generated method stub
		return frameDetailsRepository.findOne(id);
	}

	@Override
	public FrameDetails findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(
			FrameInfo frameInfoId, FrameNumber frameNumberId,
			FrameSize framesizeId, FrameThickness framethicknessId) {
		// TODO Auto-generated method stub
		return frameDetailsRepository.findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(frameInfoId, frameNumberId, framesizeId, framethicknessId);
	}

}

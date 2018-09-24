package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.repository.FrameInfoRepository;
import in.deepstudio.framingcentre.service.FrameInfoService;

@Component("frameInfoService")
@Transactional
public class FrameInfoServiceImpl implements FrameInfoService {

	@Autowired
	private FrameInfoRepository frameInfoRepository;
	
	@Override
	public FrameInfo save(FrameInfo c) {
		return frameInfoRepository.save(c);
	}

	@Override
	public List<FrameInfo> findAll() {		
		return Lists.newArrayList(frameInfoRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameInfoRepository.exists(id)){
			frameInfoRepository.delete(id);
		}
	}

	@Override
	public FrameInfo findOne(Long id) {
		// TODO Auto-generated method stub
		return frameInfoRepository.findOne(id);
	}

	@Override
	public boolean exists(Long uniqueKey) {
		// TODO Auto-generated method stub
		return frameInfoRepository.exists(uniqueKey);
	}

	@Override
	public FrameInfo findByFrameType(String frameType) {
		// TODO Auto-generated method stub
		return frameInfoRepository.findByFrameType(frameType);
	}
}

package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameRate;
import in.deepstudio.framingcentre.repository.FrameRateRepository;
import in.deepstudio.framingcentre.service.FrameRateService;

@Component("frameRateService")
@Transactional
public class FrameRateServiceImpl implements FrameRateService {

	@Autowired
	private FrameRateRepository frameRateRepository;
	
	@Override
	public FrameRate save(FrameRate c) {
		// TODO Auto-generated method stub
		return frameRateRepository.save(c);
	}

	@Override
	public List<FrameRate> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(frameRateRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		frameRateRepository.delete(id);

	}

	@Override
	public FrameRate findOne(Long id) {
		// TODO Auto-generated method stub
		return frameRateRepository.findOne(id);
	}

	@Override
	public FrameRate findByFrameDetailsAndFrameClientType(FrameDetails frameId,
			FrameClientType clientTypeId) {
		// TODO Auto-generated method stub
		return frameRateRepository.findByFrameDetailsAndFrameClientType(frameId,clientTypeId);
	}

}

package in.deepstudio.framingcentre.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.repository.FrameClientTypeRepository;
import in.deepstudio.framingcentre.service.FrameClientTypeService;

@Component("frameClientTypeService")
@Transactional
public class FrameClientTypeServiceImpl implements FrameClientTypeService {

	@Autowired
	private FrameClientTypeRepository frameClientTypeRepository;
	
	@Override
	public FrameClientType save(FrameClientType c) {
		return frameClientTypeRepository.save(c);
	}

	@Override
	public List<FrameClientType> findAll() {
		return Lists.newArrayList(frameClientTypeRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameClientTypeRepository.exists(id)){
			frameClientTypeRepository.delete(id);
		}
		
	}

	@Override
	public FrameClientType findOne(Long id) {
		// TODO Auto-generated method stub
		return frameClientTypeRepository.findOne(id);
	}

	@Override
	public FrameClientType findByClientTypeName(String clientTypeName) {
		// TODO Auto-generated method stub
		return frameClientTypeRepository.findByClientTypeName(clientTypeName);
	}

}

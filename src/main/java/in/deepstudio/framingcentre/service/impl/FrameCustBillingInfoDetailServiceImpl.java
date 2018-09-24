package in.deepstudio.framingcentre.service.impl;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfoDetail;
import in.deepstudio.framingcentre.repository.FrameCustBillingInfoDetailRepository;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoDetailService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("frameCustBillingInfoDetailService")
@Transactional
public class FrameCustBillingInfoDetailServiceImpl implements
		FrameCustBillingInfoDetailService {

	@Autowired
	private FrameCustBillingInfoDetailRepository frameCustBillingInfoDetailRepository;
	
	
	@Override
	public FrameCustBillingInfoDetail save(FrameCustBillingInfoDetail c) {
		// TODO Auto-generated method stub
		return frameCustBillingInfoDetailRepository.save(c);
	}

	@Override
	public List<FrameCustBillingInfoDetail> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(frameCustBillingInfoDetailRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		frameCustBillingInfoDetailRepository.delete(id);
	}

	@Override
	public FrameCustBillingInfoDetail findOne(Long id) {
		// TODO Auto-generated method stub
		return frameCustBillingInfoDetailRepository.findOne(id);
	}

}

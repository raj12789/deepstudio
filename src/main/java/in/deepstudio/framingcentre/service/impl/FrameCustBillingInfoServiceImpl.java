package in.deepstudio.framingcentre.service.impl;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import in.deepstudio.framingcentre.repository.FrameCustBillingInfoRepository;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("frameCustBillingInfoService")
@Transactional
public class FrameCustBillingInfoServiceImpl implements
		FrameCustBillingInfoService {

	@Autowired
	private FrameCustBillingInfoRepository frameCustBillingInfoRepository;
	
	@Override
	public FrameCustBillingInfo save(FrameCustBillingInfo c) {
		return frameCustBillingInfoRepository.save(c);
	}

	@Override
	public List<FrameCustBillingInfo> findAll() {
		return Lists.newArrayList(frameCustBillingInfoRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(frameCustBillingInfoRepository.exists(id)){
			frameCustBillingInfoRepository.delete(id);
		}
	}

	@Override
	public FrameCustBillingInfo findOne(Long id) {
		// TODO Auto-generated method stub
		return frameCustBillingInfoRepository.findOne(id);
	}

	@Override
	public String findByCustBillNumber(String custBillNumber) {
		// TODO Auto-generated method stub
		return frameCustBillingInfoRepository.findByCustBillNumber(custBillNumber);
	}
	
}

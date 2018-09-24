package in.deepstudio.pvq.service.impl;

import java.util.List;

import in.deepstudio.pvq.domain.PvqBillInfo;
import in.deepstudio.pvq.repository.PvqBillInfoRepository;
import in.deepstudio.pvq.service.PvqBillInfoService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("pvqBillInfoService")
@Transactional
public class PvqBillInfoServiceImpl implements PvqBillInfoService{

	@Autowired
	private PvqBillInfoRepository pvqBillInfoRepository;
	
	@Override
	public PvqBillInfo save(PvqBillInfo c) {
		return pvqBillInfoRepository.save(c);
	}

	@Override
	public List<PvqBillInfo> findAll() {
		return Lists.newArrayList(pvqBillInfoRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(pvqBillInfoRepository.exists(id)){
			pvqBillInfoRepository.delete(id);
		}
	}

	@Override
	public PvqBillInfo findOne(Long id) {
		// TODO Auto-generated method stub
		return pvqBillInfoRepository.findOne(id);
	}

	@Override
	public PvqBillInfo findByBillNumber(String billNumber) {
		// TODO Auto-generated method stub
		return pvqBillInfoRepository.findByBillNumber(billNumber);
	}
	
}

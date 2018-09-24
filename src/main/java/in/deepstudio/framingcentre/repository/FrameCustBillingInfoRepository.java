package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import org.springframework.data.repository.CrudRepository;

public interface FrameCustBillingInfoRepository extends
		CrudRepository<FrameCustBillingInfo, Long> {
	@Override
	public <S extends FrameCustBillingInfo> S save(S arg0);
	
	@Override
	public Iterable<FrameCustBillingInfo> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override	
	FrameCustBillingInfo findOne(Long id);
	
	String findByCustBillNumber(String custBillNumber);
}

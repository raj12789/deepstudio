package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfoDetail;

import org.springframework.data.repository.CrudRepository;

public interface FrameCustBillingInfoDetailRepository extends CrudRepository<FrameCustBillingInfoDetail, Long> {
	@Override
	public <S extends FrameCustBillingInfoDetail> S save(S arg0);
	
	@Override
	public Iterable<FrameCustBillingInfoDetail> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameCustBillingInfoDetail findOne(Long id);
}

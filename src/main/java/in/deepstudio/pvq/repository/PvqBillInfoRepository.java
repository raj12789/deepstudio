package in.deepstudio.pvq.repository;

import in.deepstudio.pvq.domain.PvqBillInfo;

import org.springframework.data.repository.CrudRepository;

public interface PvqBillInfoRepository extends CrudRepository<PvqBillInfo, Long> {
	@Override
	public <S extends PvqBillInfo> S save(S arg0);
	
	@Override
	public Iterable<PvqBillInfo> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	PvqBillInfo findOne(Long id);
	
	PvqBillInfo findByBillNumber(String billNumber);
}

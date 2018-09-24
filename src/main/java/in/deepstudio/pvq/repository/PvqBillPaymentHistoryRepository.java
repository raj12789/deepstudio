package in.deepstudio.pvq.repository;

import in.deepstudio.pvq.domain.PvqBillPaymentHistory;

import org.springframework.data.repository.CrudRepository;

public interface PvqBillPaymentHistoryRepository extends CrudRepository<PvqBillPaymentHistory, Long>{
	@Override
	public <S extends PvqBillPaymentHistory> S save(S arg0);
	
	@Override
	public Iterable<PvqBillPaymentHistory> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	PvqBillPaymentHistory findOne(Long id);
}

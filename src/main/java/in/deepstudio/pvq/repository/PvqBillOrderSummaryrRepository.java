package in.deepstudio.pvq.repository;

import in.deepstudio.pvq.domain.PvqBillOrderSummary;
import org.springframework.data.repository.CrudRepository;

public interface PvqBillOrderSummaryrRepository extends CrudRepository<PvqBillOrderSummary, Long> {

	@Override
	public <S extends PvqBillOrderSummary> S save(S arg0);
	
	@Override
	public Iterable<PvqBillOrderSummary> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	PvqBillOrderSummary findOne(Long id);
	
}

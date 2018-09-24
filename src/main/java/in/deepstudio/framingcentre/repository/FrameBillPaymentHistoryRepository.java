package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameBillPaymentHistory;
import org.springframework.data.repository.CrudRepository;

public interface FrameBillPaymentHistoryRepository extends
CrudRepository<FrameBillPaymentHistory, Long>{

	@Override
	public <S extends FrameBillPaymentHistory> S save(S arg0);
	
	@Override
	public Iterable<FrameBillPaymentHistory> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override	
	FrameBillPaymentHistory findOne(Long id);
	
}

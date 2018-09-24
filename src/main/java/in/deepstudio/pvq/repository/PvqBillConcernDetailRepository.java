package in.deepstudio.pvq.repository;

import in.deepstudio.pvq.domain.PvqBillConcernDetail;

import org.springframework.data.repository.CrudRepository;

public interface PvqBillConcernDetailRepository extends CrudRepository<PvqBillConcernDetail, Long>{

	@Override
	public <S extends PvqBillConcernDetail> S save(S arg0);
	
	@Override
	public Iterable<PvqBillConcernDetail> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	PvqBillConcernDetail findOne(Long id);
	
}

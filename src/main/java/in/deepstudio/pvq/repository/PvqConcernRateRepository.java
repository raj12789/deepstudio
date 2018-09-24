package in.deepstudio.pvq.repository;

import org.springframework.data.repository.CrudRepository;

import in.deepstudio.pvq.domain.PvqConcernRate;
import in.deepstudio.pvq.domain.PvqConcernType;

public interface PvqConcernRateRepository extends CrudRepository<PvqConcernRate, Long>{

	@Override
	public <S extends PvqConcernRate> S save(S arg0);
	
	@Override
	public Iterable<PvqConcernRate> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	PvqConcernRate findOne(Long id);
	
	PvqConcernRate findByPvqConcernTypeAndPvqConcernName(PvqConcernType pvqConcernType,String pvqConcernName);
	
}

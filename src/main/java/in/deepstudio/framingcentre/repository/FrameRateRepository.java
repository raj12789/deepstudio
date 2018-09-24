package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameClientType;
import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameRate;

import org.springframework.data.repository.CrudRepository;

public interface FrameRateRepository extends CrudRepository<FrameRate, Long> {
	@Override
	public <S extends FrameRate> S save(S arg0);
	
	@Override
	public Iterable<FrameRate> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameRate findOne(Long id);
	
	FrameRate findByFrameDetailsAndFrameClientType(FrameDetails frameId,FrameClientType clientTypeId);
	
}

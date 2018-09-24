package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameClientType;
import org.springframework.data.repository.CrudRepository;

public interface FrameClientTypeRepository extends
		CrudRepository<FrameClientType, Long> {
	@Override
	public <S extends FrameClientType> S save(S arg0);
	
	@Override
	public Iterable<FrameClientType> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameClientType findOne(Long id);
	
	FrameClientType findByClientTypeName(String clientTypeName);
}

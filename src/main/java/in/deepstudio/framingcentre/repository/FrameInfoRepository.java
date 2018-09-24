package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameInfo;

import org.springframework.data.repository.CrudRepository;
import java.lang.Long;

public interface FrameInfoRepository extends CrudRepository<FrameInfo, Long>  {
	@Override
	public <S extends FrameInfo> S save(S arg0);
	
	@Override
	public Iterable<FrameInfo> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameInfo findOne(Long id);
	
	FrameInfo findByFrameType(String frameType);
	
}

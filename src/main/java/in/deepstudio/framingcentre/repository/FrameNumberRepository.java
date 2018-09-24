package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameNumber;

import org.springframework.data.repository.CrudRepository;

public interface FrameNumberRepository extends
		CrudRepository<FrameNumber, Long> {
	@Override
	public <S extends FrameNumber> S save(S arg0);
	
	@Override
	public Iterable<FrameNumber> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameNumber findOne(Long id);
	
	FrameNumber findByFrameNumber(String frameNumber);
}

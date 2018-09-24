package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameSize;

import org.springframework.data.repository.CrudRepository;

public interface FrameSizeRepository extends CrudRepository<FrameSize, Long> {
	@Override
	public <S extends FrameSize> S save(S arg0);
	
	@Override
	public Iterable<FrameSize> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameSize findOne(Long id);
	
	FrameSize findByFrameSizeWidthAndFrameSizeHeightAndFrameMeasurementsType(int width,int height,String measurementType);
}

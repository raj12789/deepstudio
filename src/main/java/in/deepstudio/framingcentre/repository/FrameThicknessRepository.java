package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameThickness;

import org.springframework.data.repository.CrudRepository;

public interface FrameThicknessRepository extends
		CrudRepository<FrameThickness, Long> {
	@Override
	public <S extends FrameThickness> S save(S arg0);
	
	@Override
	public Iterable<FrameThickness> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameThickness findOne(Long id);
	
	FrameThickness findByFrameThicknessSizeAndFrameMeasurementsType(String frameThickness,String frameMeasurementsType);
}

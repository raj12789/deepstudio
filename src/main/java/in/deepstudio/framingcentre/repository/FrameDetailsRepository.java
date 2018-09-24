package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.FrameDetails;
import in.deepstudio.framingcentre.domain.FrameInfo;
import in.deepstudio.framingcentre.domain.FrameNumber;
import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.domain.FrameThickness;

import org.springframework.data.repository.CrudRepository;

public interface FrameDetailsRepository extends
		CrudRepository<FrameDetails, Long> {
	@Override
	public <S extends FrameDetails> S save(S arg0);
	
	@Override
	public Iterable<FrameDetails> findAll();
	
	@Override
	public void delete(Long arg0);
	
	@Override
	boolean exists(Long primaryKey);
	
	@Override
	FrameDetails findOne(Long id);
	
	FrameDetails findByFrameInfoAndFrameNumberAndFrameSizeAndFrameThickness(FrameInfo frameInfoId,FrameNumber frameNumberId,FrameSize framesizeId,FrameThickness framethicknessId);
}

package in.deepstudio.pvq.repository;

import in.deepstudio.pvq.domain.PvqConcernType;

import org.springframework.data.repository.CrudRepository;

public interface PvqConcernTypeRepository extends CrudRepository<PvqConcernType, Long> {
		@Override
		public <S extends PvqConcernType> S save(S arg0);
		
		@Override
		public Iterable<PvqConcernType> findAll();
		
		@Override
		public void delete(Long arg0);
		
		@Override
		boolean exists(Long primaryKey);
		
		@Override
		PvqConcernType findOne(Long id);
		
		PvqConcernType findByPvqConcernTypeName(String pvqConcernTypeName);

}
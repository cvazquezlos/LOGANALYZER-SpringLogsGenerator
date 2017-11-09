package springloggenerator.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import springloggenerator.model.Resource;
import springloggenerator.model.ResourceCopy;

public interface ResourceCopyRepository extends JpaRepository<ResourceCopy, Integer> {

	ResourceCopy findByLocationCode(String locationCode);
	
	Long countByResource(Resource resource);

	List<ResourceCopy> findByResource(Resource resource);
	
	Page<ResourceCopy> findAll(Pageable page);

}

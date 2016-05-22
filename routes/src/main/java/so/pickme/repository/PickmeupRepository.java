package so.pickme.repository;

import org.springframework.stereotype.Component;

import so.pickme.replica.domain.Pickmeup;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


@Component("pickmeupRepository")
public interface PickmeupRepository extends PagingAndSortingRepository<Pickmeup, Long>,GraphRepository<Pickmeup>{

	
	
}


package so.pickme.repository;

import org.springframework.stereotype.Component;

import so.pickme.replica.domain.Pickmeup;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


@Component("pickmeupRepository")
public interface PickmeupRepository extends PagingAndSortingRepository<Pickmeup, Long>,GraphRepository<Pickmeup>{

	@Query( "match (p:Pickmeup)-[r:FROM]-(u:User)" +
			"where u.username = {0}"+
			"with  p match (p) "+
			"where (p.tripstarttime_long > {1}) "+
			"AND (p.deadlinetocommit_long < {1})"+
			"return p")
	List<Pickmeup> checkBeforeNewPickup(String from_user_name,long datetime);
	
}


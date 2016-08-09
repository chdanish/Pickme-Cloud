package so.pickme.repository;

import org.springframework.stereotype.Component;

import so.pickme.replica.domain.Pickmeup;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


@Component("pickmeupRepository")
public interface PickmeupRepository extends PagingAndSortingRepository<Pickmeup, Long>,GraphRepository<Pickmeup>{

	@Query( "match (p:Pickmeup)-[r:FROM]-(u:User)" +
			"where u.username = {0}"+
			"with  p match (p) "+
			"where (p.deadlinetocommit_long > {1}) "+
			"AND (p.esttimetoreachdestination < {1})"+
			"return p")
	List<Pickmeup> checkBeforeNewPickup(String from_user_name,long datetime);
	
	/*@Query( "match (p:Pickmeup)-[:FROM]-(u:User) where u.username={0} return p" )*/
	@Query("match (p:Pickmeup)-[:FROM*]-(u:User)  where u.username={0} "+
			"OPTIONAL MATCH (p)-[]-(r:Route)"+
			"return p ,u,r")
	Iterable<Map<String, Object>> mysharedroutes(String username);
}


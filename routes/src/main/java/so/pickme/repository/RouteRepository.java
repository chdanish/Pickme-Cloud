package so.pickme.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import so.pickme.replica.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


@Component("routeRepository")
public interface RouteRepository extends PagingAndSortingRepository<Route, Long>,GraphRepository<Route>{
	

	/**
	 * @return the complete list of persons stored in the repository.
	 */
	 @Query("Match (route:Route) Where route.name = {0} return route")	
	Route findByRoutename(String Routename);
	
	@Query("Match (r:Route) Where id(r) = {0} return r")
    Route findByID(long id);
	
	/**
	 * @param maxResult
	 *            Max number of persons.
	 * @return a limited list of persons.
	 */
	
	public List<Route> findAll();
	 
	  /*findall routes with "OWNEDBY User"
    MATCH (:User {username:'gera'})-[:OWNEDBY]-(r:Route) return r*/
	/*@Query("MATCH (r:Route)-[:OWNEDBY]-(:User {username:'gera'})  return r")*/
	
	@Query("MATCH (r:Route)-[:OWNEDBY]-(u:User) where u.username = {0}  return r")
	List<Route> findAll(String ownedby); 
	
	Iterable<Route> findAll(Sort sort);
	
//	@Query("Match (route:Route) return route")
	Page<Route> findAll(Pageable pageable); 
	 
	 
	 
	
}


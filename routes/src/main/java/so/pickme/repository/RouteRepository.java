package so.pickme.repository;

import java.util.List;
import org.springframework.stereotype.Component;
import so.pickme.replica.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


@Component("routeRepository")
public interface RouteRepository extends PagingAndSortingRepository<Route, Long>{
	

	/**
	 * @return the complete list of persons stored in the repository.
	 */
	 @Query("Match (route:Route) Where route.name = {0} return route")	
	Route findByRoutename(String Routename);
	
	/**
	 * @param maxResult
	 *            Max number of persons.
	 * @return a limited list of persons.
	 */
//	 @Query("Match (route:Route) return route")
	public List<Route> findAll();
	 
	 
	Iterable<Route> findAll(Sort sort);
	
//	@Query("Match (route:Route) return route")
	Page<Route> findAll(Pageable pageable); 
	 
	 
	 
	
}


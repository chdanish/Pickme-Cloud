package so.pickme.repository;

import org.springframework.data.neo4j.annotation.Query;
//import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;

import so.pickme.replica.domain.User;

public interface UserRepository extends GraphRepository<User>{

	/**@Repository need's to be tested
	 * @return the complete list of persons stored in the repository.
	 */
	@Query("Match (user:User) Where user.username = {0} return user")
	User findByUsername(String username);

	/**
	 * @param maxResult
	 *            Max number of persons.
	 * @return a limited list of persons.
	 */
	// @Query("Match (user:User) return user")
	//public Result<User> findAll();
	
	
    

}

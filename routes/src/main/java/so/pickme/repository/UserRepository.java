package so.pickme.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;


@Repository
public interface UserRepository extends CrudRepository<User, Long>,
											GraphRepository<User>{

	/**@Repository need's to be tested
	 * @return the complete list of persons stored in the repository.
	 */
	@Query("Match (user:User) Where user.username = {0} return user")
	User findByUsername(String username);
	
	//MATCH (u:User) where (u.firstName +' '+ u.lastName) =~ '.*n.*' RETURN  u; add {.*} in beginning and in end of parameter
	
	@Query("MATCH (u:User) where (u.firstName +' '+ u.lastName) =~ {0} RETURN  u;")
	User findByName(String name);
	
	@Query("Match (user:User) Where user.email = {0} return user")
	User findByEmail(String email);
	
	@Query("MATCH (u:User) where ID(u) = {0} return u")
	User FinduserbyID(Long ID);


	public List<User> findAll();
	
	@Query("MATCH (u:User )-[x:FRIEND_REQUEST]-(b:User) Where u.username = {0} return b")
	public User myfriendrequest(String username);
	
	
	@Query("MATCH (u:User )-[x:FRIEND_REQUEST]->(b:User)" +
	" WHERE u.username = {0} RETURN distinct  ID(b) as UserID, b.firstName as FName, b.lastName as LName , b.username as UName, b.email as Email")
	public Iterable<UserQuery> myfriendrequestMOD(String username);
	
	@Query("MATCH (u:User )-[x:FRIEND*1]-(b:User)" +
	" WHERE u.username = {0} RETURN distinct  ID(b) as UserID, b.firstName as FName, b.lastName as LName , b.username as UName, b.email as Email")
	public Iterable<UserQuery> myfriendsMOD(String username);


	@Query( "MATCH (u:User )"+
			"where  (u.username)  = {0} and u.username <> {1}"+
			"OPTIONAL MATCH (u)-[friend:FRIEND]-(b:User ) where  b.username = {1}"+
			"OPTIONAL MATCH (u)-[friendreq:FRIEND_REQUEST]-(g:User ) where  g.username = {1}"+
			"RETURN distinct  ID(u) as UserID, u.firstName as FName, u.lastName as LName , "+
			"u.username as UName, u.email as Email ,friend is not null as Friendstatus,friendreq is not null as Friendreqstatus")
	public UserQuery findByUsernameMOD(String username,String Pusername);
	
	/*where  (u.firstName + u.lastName) =~ '.*s.*' 
	OPTIONAL MATCH (u)-[friend:FRIEND]-(b:User )
	OPTIONAL MATCH (u)-[friendreq:FRIEND_REQUEST]-(b:User )
	where  b.username = 'mano'
	RETURN distinct  ID(u) as UserID, u.firstName as FName, u.lastName as LName ,
	u.username as UName, u.email as Email ,friend is not null as Friendstatus,friendreq is not null as Friendreqstatus*/
	
	@Query( "MATCH (u:User )"+
			"where  (u.firstName + u.lastName)  =~ {0} and u.username <> {1}"+
			"OPTIONAL MATCH (u)-[friend:FRIEND]-(b:User ) where  b.username = {1}"+
			"OPTIONAL MATCH (u)-[friendreq:FRIEND_REQUEST]-(g:User ) where  g.username = {1}"+
			"RETURN distinct  ID(u) as UserID, u.firstName as FName, u.lastName as LName , "+
			"u.username as UName, u.email as Email ,friend is not null as Friendstatus,friendreq is not null as Friendreqstatus")
	public Iterable<UserQuery> findByNameMOD(String username,String Pusername);
	
}

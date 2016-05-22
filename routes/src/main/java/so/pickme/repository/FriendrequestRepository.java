package so.pickme.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import so.pickme.replica.domain.FriendRequest;

@Repository
public interface FriendrequestRepository extends CrudRepository<FriendRequest, Long>,
		PagingAndSortingRepository<FriendRequest, Long>,GraphRepository<FriendRequest> {

	@Query("MATCH (u:User )-[x:FRIEND_REQUEST]-(b:User) Where u.username={0} and b.username={1} delete x")
	public void deleteAllFriendRequestBetween(String username1,String username2);
	
}

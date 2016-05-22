package so.pickmefrnd.service;

import java.security.Principal;

import org.springframework.data.neo4j.repository.GraphRepository;

import so.pickme.replica.domain.FriendRequest;
import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;

public interface FriendRequestService   {

	
	Iterable<UserQuery> findAllfriendrequestbyusername(Principal principal);
    
    Iterable<UserQuery> addfriendrequestbyusername(String message,String  username,Principal principal);
    
    Iterable<UserQuery> deletefriendrequestbyusername(String fromusername,Principal principal);
    
    FriendRequest findOne(Long relationshipId);
    
    public  GraphRepository<User> getRepository();
	
}
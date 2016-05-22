package so.pickmefrnd.service;

import java.security.Principal;

import so.pickme.replica.domain.UserQuery;

public interface FriendService   {

	
	Iterable<UserQuery> findAllfriends(Principal principal);
    
	Iterable<UserQuery> addfriendbyusername(Principal principal,String  friendusername);
    
	Iterable<UserQuery> deletefriendbyusername(Principal principal,String  friendusername);
    
	
}
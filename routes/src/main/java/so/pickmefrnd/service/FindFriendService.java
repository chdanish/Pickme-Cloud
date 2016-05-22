package so.pickmefrnd.service;

import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;

public interface FindFriendService   {

	
	User findByUsername(String Username);
    
    //Iterable<User> findByName(String name);
    
    User findByEmail(String email);

	UserQuery findByUsername(String username, String Pusername);

	Iterable<UserQuery> findByName(String name, String Pusername);
    
	
}
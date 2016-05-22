package so.pickmefrnd.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.UserRepository;
import so.pickmefrnd.service.FindFriendService;

@Service("findfriendService")
public class FindFriendServiceImpl  implements FindFriendService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FindFriendServiceImpl.class);
	@Autowired
    UserRepository userRepository;
	
	 @Override
	    public UserQuery findByUsername(String username, String Pusername){
			return userRepository.findByUsernameMOD(username, Pusername);
	    	
	    }
	    @Override
	    public Iterable<UserQuery> findByName(String name,String Pusername){
	    	name = ".*"+ name+".*";
	    	System.out.println("find by name query: "+ name);
			return userRepository.findByNameMOD(name, Pusername);
	    	
	    }
	    @Override
	    public User findByEmail(String email){
	    	return userRepository.findByEmail(email);
	    	
	    }
		@Override
		public User findByUsername(String Username) {
			// TODO Auto-generated method stub
			return null;
		}
	    	
	
	 	
}

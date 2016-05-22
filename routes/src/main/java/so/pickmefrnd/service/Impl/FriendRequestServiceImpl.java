package so.pickmefrnd.service.Impl;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

import so.pickme.replica.domain.FriendRequest;
import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.FriendrequestRepository;
import so.pickme.repository.UserRepository;
import so.pickme.utils.SecurityUtils;
import so.pickmefrnd.service.FriendRequestService;

@Service("friendRequestService")
public class FriendRequestServiceImpl  implements FriendRequestService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FriendRequestServiceImpl.class);
	@SuppressWarnings("unused")
	private static final int FRIEND_REQUEST_LENGTH = 1000;
	
	@Autowired
	private FriendrequestRepository friendrequestRepository;
	
	@Autowired
    UserRepository userRepository;
	
	 	@Override
		public Iterable<UserQuery> findAllfriendrequestbyusername(Principal principal) {
			String username = SecurityUtils.getLoggedInUserName(principal);
			return userRepository.myfriendrequestMOD(username);
		}
	    
	    @Override
	    public Iterable<UserQuery> addfriendrequestbyusername(String message,String  username,Principal principal){
	    	String fromUsername = SecurityUtils.getLoggedInUserName(principal);
	    	User fromUser = userRepository.findByUsername(fromUsername);
	    	User toUser = userRepository.findByUsername(username);
	    	FriendRequest frndreq = new FriendRequest(message, toUser, fromUser);
	    	friendrequestRepository.save(frndreq);
			return findAllfriendrequestbyusername(principal);
	    	
	    }
	    
	    @Override
	    public Iterable<UserQuery> deletefriendrequestbyusername(String fromusername,Principal principal){
	    	User fromUser = new User();
	    	User toUser = new User();
	    	FriendRequest frndreq = new FriendRequest("",toUser,fromUser);
	    	friendrequestRepository.delete(frndreq);
			return findAllfriendrequestbyusername(principal);
	  
	    }

		@Override
		public FriendRequest findOne(Long relationshipId) {
			return friendrequestRepository.findOne(relationshipId);
		}

		@Override
		public GraphRepository<User> getRepository() {
			return userRepository;
		}

	

	
	
}

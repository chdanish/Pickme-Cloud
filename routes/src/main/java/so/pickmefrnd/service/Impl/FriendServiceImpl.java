package so.pickmefrnd.service.Impl;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.FriendrequestRepository;
import so.pickme.repository.UserRepository;
import so.pickme.utils.SecurityUtils;
import so.pickmefrnd.service.FriendService;

@Service("friendService")
public class FriendServiceImpl  implements FriendService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);
	@SuppressWarnings("unused")
	private static final int FRIEND_REQUEST_LENGTH = 1000;
	
	@Autowired
	private FriendrequestRepository friendrequestRepository;
	
	@Autowired
    UserRepository userRepository;
	
	 	@Override
	    public Iterable<UserQuery> findAllfriends(Principal principal){
	    	String pusername= SecurityUtils.getLoggedInUserName(principal);
	    	Iterable<UserQuery> loginUser = userRepository.myfriendsMOD(pusername);
			return loginUser;
	    	
	    }
	    
	    @Override
	    public Iterable<UserQuery> addfriendbyusername(Principal principal,String  friendusername){
	    	String pusername= SecurityUtils.getLoggedInUserName(principal);
	    	User loginUser = userRepository.findByUsername(pusername);
	    	User friendUser = userRepository.findByUsername(friendusername);
	    	clearFriendrequest(pusername,friendusername);
	    	loginUser.addFriend(friendUser);
	    	userRepository.save(loginUser);
	    	return findAllfriends(principal);
	    	
	    }
	    @Override
	    public Iterable<UserQuery> deletefriendbyusername(Principal principal,String  friendusername){
	    	String pusername= SecurityUtils.getLoggedInUserName(principal);
	    	User loginUser = userRepository.findByUsername(pusername);
	    	User friendUser = userRepository.findByUsername(friendusername);
	    	loginUser.deleteFriend(friendUser);
	    	userRepository.save(loginUser);
			return findAllfriends(principal);
	    	
	    }
	    
	    public void clearFriendrequest(String pusername,String friendusername){
	    	friendrequestRepository.deleteAllFriendRequestBetween(pusername, friendusername);	
	    }
	    
	
	 	
}

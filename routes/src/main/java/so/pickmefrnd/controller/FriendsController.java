package so.pickmefrnd.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.UserRepository;
import so.pickmefrnd.service.FriendService;

@RestController
@RequestMapping("/friends")
public class FriendsController  {
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserRepository userRepository;
	

	
	// Generates friend request after ensuring friend request does not exit already
	
	@Transactional
	@RequestMapping(value="/addfriend/{targetUserId}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addfriend(@PathVariable Long targetUserId,Principal principal) throws XhrcException {		
		Map<String, Object> map = new LinkedHashMap<>();		
		
		User friend = userRepository.FinduserbyID(targetUserId);
		Iterable<UserQuery> friends =friendService.addfriendbyusername(principal, friend.getUsername());
		/*Iterator<UserQuery> friends = friendService.addfriendbyusername(principal, friend.getUsername()).iterator();
		int i = 1;
		if(friends != null){
			while (friends.hasNext()){
				map.put("friend"+i, friends.next());
			}
		}*/
		map.put("Status", friends);
		return map;
	}
	
	@Transactional
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Map<String, Object> friendsList(Principal principal) {
		Map<String, Object> map = new LinkedHashMap<>();
		Iterable<UserQuery> friends = friendService.findAllfriends(principal);
		map.put("Status", friends);
		return map;
	}
	
	
	@Transactional
    @RequestMapping(value="/del/{id}", method=RequestMethod.DELETE     )
    public Map<String, String> deleteFriend( @PathVariable Long id) {
		Map<String, String> map = new LinkedHashMap<>();
		return map;
	}
}

package so.pickmefrnd.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.User;
import so.pickme.replica.domain.UserQuery;
import so.pickme.repository.FriendrequestRepository;
import so.pickme.repository.UserRepository;
import so.pickme.utils.SecurityUtils;
import so.pickmefrnd.service.FriendRequestService;

@RestController
@RequestMapping("/friendrequest")
public class FriendRequestController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private FriendrequestRepository friendrequestRepository;
	

	
	// Generates friend request after ensuring friend request does not exit already
	
	@Transactional
	@RequestMapping(value="/addfriendreq/{targetUserId}",method=RequestMethod.POST)
	public Map<String, String> friendrequest(@PathVariable Long targetUserId,Principal principal) throws XhrcException {		
		Map<String, String> map = new LinkedHashMap<>();
		User toUser = userRepository.FinduserbyID(targetUserId);
		System.out.println("Found User: "+ toUser.toString() );
		friendRequestService.addfriendrequestbyusername("", toUser.getUsername(), principal);
		map.put("Status", "Success");
		return map;
	}
	
	@Transactional
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Map<String, Object> friendRequestList(Principal principal) {
		String username = SecurityUtils.getLoggedInUserName(principal);
		Map<String, Object> map = new LinkedHashMap<>();
		 /*userRepository.myfriendrequestMOD(username);*/
		Iterable<UserQuery> results = userRepository.myfriendrequestMOD(username);
		System.out.println("query results are: "+results.toString());
		map.put("Status", results);
		return map;
	}
	
	
	@Transactional
    @RequestMapping(value="/del/{id}", method=RequestMethod.DELETE     )
    public Map<String, String> deleteFriendRequest( @PathVariable Long id,Principal principal) {
		String username = SecurityUtils.getLoggedInUserName(principal);
		Map<String, String> map = new LinkedHashMap<>();
		User friendreq = userRepository.FinduserbyID(id);
		
		friendrequestRepository.deleteAllFriendRequestBetween(username, friendreq.getUsername());
		map.put("Status", "Success");
		return map;
	}
}

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
import so.pickme.replica.domain.UserQuery;
import so.pickme.utils.SecurityUtils;
import so.pickmefrnd.service.FindFriendService;

@RestController
@RequestMapping("/findfriends")
public class FindFriendsController  {
	
	@Autowired
	private FindFriendService findfriendService;

	

	
	// Generates friend request after ensuring friend request does not exit already
	
	@Transactional
	@RequestMapping(value="/findfriendbyusername/{username}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> findfriendbyusername(@PathVariable("username") String username,Principal principal) throws XhrcException {		
		Map<String, Object> map = new LinkedHashMap<>();
		String pusername = SecurityUtils.getLoggedInUserName(principal);
		UserQuery userquery = findfriendService.findByUsername(username, pusername);
		System.out.println(userquery);
		map.put("status", findfriendService.findByUsername(username, pusername));
		return map;
	}
	
	@Transactional
	@RequestMapping(value="/findfriendbyemail/{email}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findfriendbyemail(@PathVariable String email) throws XhrcException {		
		Map<String, Object> map = new LinkedHashMap<>();		
		map.put("status", findfriendService.findByEmail(email));
		return map;
	}
	
	@Transactional
	@RequestMapping(value="/findfriendbyname/{name}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findfriendbyname(@PathVariable String name,Principal principal) throws XhrcException {		
		Map<String, Object> map = new LinkedHashMap<>();
		String pusername = SecurityUtils.getLoggedInUserName(principal);
		//Iterable<UserQuery> userquery = findfriendService.findByName(name, pusername);
		map.put("Status", findfriendService.findByName(name, pusername).iterator());
		return map;
	}
	

}

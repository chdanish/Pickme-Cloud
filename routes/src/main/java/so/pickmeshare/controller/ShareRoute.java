package so.pickmeshare.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.RouteRepository;
import so.pickme.repository.UserRepository;
import so.pickme.utils.SecurityUtils;
import so.pickmeshare.response.ShareRouteDTO;
import so.pickmeshare.service.ShareRouteService;


@RestController
@RequestMapping("/shareroute")
public class ShareRoute {
	
	private static final int DEPTH_ENTITY = 1;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ShareRouteService shareRouteService;
	
	@Autowired
    private RouteRepository routerepository;
	
	
	@RequestMapping(value="/sharemyroute", method = RequestMethod.POST)
	 public @ResponseBody List<String> shareroute(@RequestBody ShareRouteDTO sdto,Principal principal) {
		System.out.println("Response recieved: "+ sdto);
		
		String username = SecurityUtils.getLoggedInUserName(principal);
		
		System.out.println("User name: "+ username);
		
		if (username!= null) {
			
			User activeUser = userRepository.findByUsername(username);
			User toUser = userRepository.findOne(sdto.getToID(),DEPTH_ENTITY);
			
			Route route = getRepository().findOne(sdto.getRouteID(), DEPTH_ENTITY);
			
			System.out.println("Route selected: "+ route);
			
			shareRouteService.sharemyroute(sdto, activeUser, toUser, route);
			
		}
		return new ArrayList<String>();		
		
		
	}
	
	
	
    public GraphRepository<Route> getRepository() {
        return routerepository;
    }

}

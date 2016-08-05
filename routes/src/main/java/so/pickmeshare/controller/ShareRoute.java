package so.pickmeshare.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.replica.domain.Pickmeup;
import so.pickme.replica.domain.Route;
import so.pickme.replica.domain.User;
import so.pickme.repository.PickmeupRepository;
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
	
	@Autowired
	private PickmeupRepository pickmeupRepository;
	
	
	@RequestMapping(value="/sharemyroute", method = RequestMethod.POST)
	 public @ResponseBody Map<String, Object> shareroute(@RequestBody ShareRouteDTO sdto,Principal principal) throws ParseException {
		System.out.println("Response recieved: "+ sdto);
		Map<String, Object> map = new LinkedHashMap<>();
		
		String username = SecurityUtils.getLoggedInUserName(principal);
		
		List<Pickmeup> mypickupreqs = pickmeupRepository.checkBeforeNewPickup(username, sdto.getStartTripDateTime());
		
		if(mypickupreqs.iterator().hasNext()){
			
			for (Iterator<Pickmeup> iterator = mypickupreqs.iterator(); iterator.hasNext();) {
				Pickmeup pickmeup = (Pickmeup) iterator.next();
				System.out.println("Pickup request already setup with id: " + pickmeup.toString());
			}
			
			map.put("status", mypickupreqs);
			return map;
		}
		
		System.out.println("User name: "+ username);
		
		if (username!= null) {
			
			User activeUser = userRepository.findByUsername(username);
			
			User toUser = userRepository.findOne(sdto.getToID(),DEPTH_ENTITY);
			
			Route route = getRepository().findOne(sdto.getRouteID(), DEPTH_ENTITY);
			
			System.out.println("Route selected: "+ route);
			
			shareRouteService.sharemyroute(sdto, activeUser, toUser, route);
			
		}
		return map;		
		
		
	}
	
	 @RequestMapping(value="/mysharedroutes", method = RequestMethod.GET)
	 public @ResponseBody Map<String, Object> mysharedroutes(Principal principal) throws ParseException {
		System.out.println("Current time: " );
		Map<String, Object> map = new LinkedHashMap<>();
		
		String username = SecurityUtils.getLoggedInUserName(principal);
		Iterable<Map<String, Object>> pmu =shareRouteService.mysharedroutes(username);
		map.put("status", pmu);
		return map;
	}
	
	
	
    public GraphRepository<Route> getRepository() {
        return routerepository;
    }

}

package so.pickme.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.replica.domain.Route;
import so.pickme.service.Service;
import so.pickme.service.Impl.Myrouteservice;
import so.pickme.utils.SecurityUtils;

@RestController
@RequestMapping(value = "/getmyroutes")
public class Allroutes extends Controller<Route> {

	
    
    @Autowired
    private Myrouteservice myrouteservice;

	@Override
	public Service<Route> getService() {
		return myrouteservice;
	}

	@RequestMapping(value = "/ownedbyme")
	public List<Route> myroutelist(Principal principal) {
	   String ownedby = SecurityUtils.getLoggedInUserName(principal);
	    
	   List<Route> routes = myrouteservice.findAll(ownedby);
	   
	 
	   
	   return routes;
	       
	}

}
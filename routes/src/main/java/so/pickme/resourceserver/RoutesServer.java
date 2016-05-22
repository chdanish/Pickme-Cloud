package so.pickme.resourceserver;


import java.security.Principal;





import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.response.SaverouteDTO;
import so.pickme.service.RouteService;
import so.pickme.utils.SecurityUtils;

@EnableAutoConfiguration
@SpringBootApplication
@RestController
@EnableResourceServer
@ComponentScan({ "so.pickme", "so.pickmefrnd", "so.pickmeshare" })
public class RoutesServer {
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	UserRepository userRepository;
	
	 @RequestMapping(value="/saveroute", method = RequestMethod.POST)
	 public @ResponseBody List<String> saveroute(@RequestBody SaverouteDTO sdto,Principal principal) throws XhrcException{
		
		System.out.println("Response recieved: "+ sdto);
		
		String username = SecurityUtils.getLoggedInUserName(principal);
		
		System.out.println("User name: "+ username);
		
		if (username!= null) {
			
			User activeUser = userRepository.findByUsername(username);
			
			routeService.createRouterNode(sdto, activeUser);
			
		}
		return new ArrayList<String>();		
		
		
	}

	public static void main(String[] args) {
		SpringApplication.run(RoutesServer.class, args);
		
		
	}
}

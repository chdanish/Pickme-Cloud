package org.test;

import org.routes.response.SaverouteDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableResourceServer
public class Oauth2RoutesApplication {
	
	 @RequestMapping(value="/saveroute", method = RequestMethod.POST)
	 public @ResponseBody void saveroute(@RequestBody SaverouteDTO dto){
		
		System.out.println("Response recieved: "+ dto);
		
		
		
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2RoutesApplication.class, args);
	}
}

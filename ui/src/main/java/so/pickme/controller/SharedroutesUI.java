package so.pickme.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import so.pickme.util.SecurityUtils;



@Controller
@RequestMapping("/sharedroutes")
public class SharedroutesUI {
	
	@RequestMapping
	public String getHomePage(@ModelAttribute("model") ModelMap model,Principal principal) {
		
		String username = SecurityUtils.getLoggedInUserName(principal);
		System.out.println("Username"+username);
		
		model.addAttribute("Username", username);
		return "sharedroutes";
	}

}

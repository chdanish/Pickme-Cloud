package so.pickme.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/route")
public class Route {

	@RequestMapping
	public String getHomePage(ModelMap model) {
		/*model.addAttribute("authname", SecurityContextHolder.getContext().getAuthentication().getName());*/
		return "route";
	}
}
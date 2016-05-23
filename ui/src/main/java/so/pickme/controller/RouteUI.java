package so.pickme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/route")
public class RouteUI {

	@RequestMapping
	public String getHomePage(ModelMap model) {
		return "route";
	}
}
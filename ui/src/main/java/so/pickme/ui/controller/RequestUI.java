package so.pickme.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/request")
public class RequestUI {

	@RequestMapping
	public String getHomePage(ModelMap model) {
		return "request";
	}
}
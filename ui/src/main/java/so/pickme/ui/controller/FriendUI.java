package so.pickme.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/friend")
public class FriendUI {

	@RequestMapping
	public String getHomePage(ModelMap model) {
		return "friend";
	}
}
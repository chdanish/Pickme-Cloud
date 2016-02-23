package so.pickme.Controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import so.pickme.common.exception.XhrcException;
import so.pickme.replica.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.response.SignupDTO;
import so.pickme.service.BaseService;

@Controller
@RequestMapping("/signup")
public class SignupCon {
	
	@Resource
	BaseService baseService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public User createAccount(@RequestBody SignupDTO dto, ModelMap model) throws XhrcException {
	
		System.out.println("Entering create account" );
		System.out.println("Entering create account with:"+dto.getFirstName()+" "+dto.getLastName());
		/*User user=userRepository.findByUsername(dto.getUsername());*/
		/*if(baseService.findByUsername(dto.getUsername()) != null)*/
		if(userRepository.findByUsername(dto.getUsername()) != null)
		{
			return null;
			
		}
		else
		{
			baseService.registerUserNode(dto);
			return userRepository.findByUsername(dto.getUsername());
		}
		}
	}
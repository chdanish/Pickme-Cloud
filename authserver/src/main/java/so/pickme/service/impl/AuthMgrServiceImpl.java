package so.pickme.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import so.pickme.replica.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.service.AuthMgrService;

@Service
@Transactional
public class AuthMgrServiceImpl implements AuthMgrService {
	
	String user_role = ""; 
	
	@Resource
	private Neo4jOperations template;
	
	@Autowired
	@Lazy
	private UserRepository userRepository;
	
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	  static {
	    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	  }
	
	

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		User user = userRepository().findByUsername(auth.getName());
		if (user != null) {
			System.out.println("Input password is : "+ auth.getCredentials().toString());
			/*user.setRole("USER");*/
			System.out.println("Role as per DB : "+ user.getRole());
			for (Iterator<String> iterator = user.getRole().iterator(); iterator.hasNext();) {
				user_role =iterator.next();
				AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_"+user_role));
				System.out.println("Added auths are: "+AUTHORITIES);
			}
			bcrypt.matches(auth.getCredentials().toString(), user.getPassword());
			
			return new UsernamePasswordAuthenticationToken(auth.getName(),
			        auth.getCredentials(), AUTHORITIES);
			
		}
		
		/*For testing puporse we can enable username=passwd 
		if (auth.getName().equals(auth.getCredentials())) {
		      return new UsernamePasswordAuthenticationToken(auth.getName(),
		        auth.getCredentials(), AUTHORITIES);
		      }*/
		
		
		      throw new BadCredentialsException("Bad Credentials");
	}

	@Override
	public Neo4jOperations template() {
		return template;
	}

	public void setTemplate(Neo4jOperations template) {
		this.template = template;
	}

	@Override
	public UserRepository userRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


}

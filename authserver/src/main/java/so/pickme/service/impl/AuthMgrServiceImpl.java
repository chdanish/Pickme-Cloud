package so.pickme.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
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
import org.springframework.util.StringUtils;

import so.pickme.replica.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.service.AuthMgrService;
import so.pickme.service.GenericService;

@Service
@Transactional
public class AuthMgrServiceImpl extends GenericService<User> implements AuthMgrService {

	
	

	String user_role = ""; 
	
	@Autowired
	private Neo4jOperations template;
	
	@Autowired
	private UserRepository userRepository;
	
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	  static {
	    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	  }
	
	

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		
		if(StringUtils.isEmpty(auth.getName()) || StringUtils.isEmpty(auth.getCredentials())){throw new BadCredentialsException("Bad Credentials");}
		
		//User user = template.loadByProperty(User.class, "username", auth.getName());
		User user = userRepository.findByUsername(auth.getName());
		
		/*User user = getRepository().findBySchemaPropertyValue("username", auth.getName());*/
		/*User user = userRepository.findByUsername(auth.getName());*/
		if (user != null) {
			System.out.println("Input password is : "+ auth.getCredentials().toString());
			/*user.setRole("USER");*/
			System.out.println("Role as per DB : "+ user.getRole());
			for (Iterator<String> iterator = user.getRole().iterator(); iterator.hasNext();) {
				user_role =iterator.next();
				//AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_"+user_role));
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

	@Override
	public GraphRepository<User> getRepository() {
		
		return userRepository;
	}




}

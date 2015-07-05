package so.pickme.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import so.pickme.common.exception.XhrcException;
import so.pickme.common.exception.XhrcRuntimeException;
import so.pickme.domain.User;
import so.pickme.repository.UserRepository;
import so.pickme.response.SignupDTO;
import so.pickme.service.BaseService;

import com.alibaba.fastjson.JSON;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {
	private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Resource
	private Neo4jOperations template;
	
	@Autowired
	@Lazy
	private UserRepository userRepository;
	

	@Override
	public User saveUserNode(User userNode) throws XhrcException {
		try {
			return userRepository().save(userNode);
		} catch (Exception e) {
			logger.error("saveUserNode userNode=" + JSON.toJSONString(userNode), e);
			throw new XhrcException("20021", "saveUserNode userNode=" + JSON.toJSONString(userNode), e);
		}
	}

	@Override
	public User registerUserNode(SignupDTO dto) throws XhrcException {
		User uNode = new User();
		/*uNode.setId(dto.getId());*/
		uNode.setFirstName(dto.getFirstName());
		uNode.setLastName(dto.getLastName());
		uNode.setEmail(dto.getLastName());
		uNode.setUsername(dto.getUsername());
		uNode.setRole(dto.getRole());
		uNode.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
		
		uNode.setCreatedAt(Calendar.getInstance().getTime());
		uNode = saveUserNode(uNode);
		if (uNode == null || uNode.getId() == null || uNode.getId() <= 0 || uNode.getId() == null
				|| uNode.getId() <= 0) {
			logger.error("createUserNode userNode=" + JSON.toJSONString(uNode));
			throw new XhrcRuntimeException("20041", "createUserNode userNode=" + JSON.toJSONString(uNode));
		}
		return uNode;
	}

	@Override
	public User getUserNode(Long userId) throws XhrcException {
		if (userId == null || userId.intValue() < 0) {
			logger.error("getUserNode userId=" + userId);
			throw new XhrcRuntimeException("20041", "getUserNode userId=" + userId);
		}
		User uNode = null;
		try {
			uNode =  userRepository().findOne(userId);
					
		} catch (Exception e) {
			logger.error("getUserNode userId：" + userId, e);
			throw new XhrcRuntimeException("20021", "getUserNode userId=" + userId, e);
		}
		if (uNode == null) {
			logger.info("getUserNode userId=" + userId);
			
			return uNode;
		} else {
			return uNode;
		}
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

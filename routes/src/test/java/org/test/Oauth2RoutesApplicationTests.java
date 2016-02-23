package org.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import so.pickme.resourceserver.RoutesServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RoutesServer.class)
@WebAppConfiguration
public class Oauth2RoutesApplicationTests {

	@Test
	public void contextLoads() {
	}

}

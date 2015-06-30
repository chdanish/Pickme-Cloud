package so.pickme.config;

import javax.annotation.PostConstruct;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*Okay, so here are the relevant options:

adviceMode=proxy, proxyTargetClass=true
adviceMode=proxy, proxyTargetClass=false
adviceMode=aspectJ, compile-time weaving
adviceMode=aspectJ, load-time weaving

This configuration says how the transaction aspect will be applied. Briefly:

adviceMode=proxy, proxyTargetClass=true Cglib is used as proxy mechanism. If you use this, cglib must be on classpath, your proxied classes must have nonparametric constructor and they can't be final (cglib creates a child class as the proxy).

adviceMode=proxy, proxyTargetClass=false Jdk proxy mechanism is used. You only can proxy classes that implements a interface for methods that should be transactional. Jdk proxy can be type casted to the interfaces but can't be type casted as the original proxied class.

So, for adviceMode=proxy, the decision relies more on how are your code standards and what constraints result from used proxy mechanism.

adviceMode=aspectJ uses aspectJ library, which does byte code intrumentation instead of proxying.

adviceMode=aspectJ, compile-time weaving You should incorporate aspectJ instrumentation during a build process in your build scripts.

adviceMode=aspectJ, load-time weaving Instrumentation is performed on runtime. You have to put the aspectj agent as jvm parameter.

Using aspectJ is more powerful and probably more performant. It is also less invasive in terms of restrictions put on the classes you want to be transactional. However, proxy mode is simple Spring's out of the box solution.

More on proxies here http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch08s06.html. More on aspectJ with spring here http://docs.spring.io/spring/docs/3.0.0.M4/reference/html/ch07s08.html.

http://jexp.de/blog/2014/12/spring-data-neo4j-improving-remoting-performance/
*/

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableNeo4jRepositories(basePackages = "so.pickme.repository")
public class NeoConfig extends Neo4jConfiguration  {
	private final Logger log = LoggerFactory.getLogger(NeoConfig.class);
    public NeoConfig() {
        setBasePackage("so.pickme.repository");
    }
   @Autowired
    GraphDatabase graphDatabase;

    @Autowired
    private PlatformTransactionManager transactionManager;
   
    
    @Bean(destroyMethod = "shutdown")
    @Scope(value="singleton")
    GraphDatabaseService graphDatabaseService() {
    	this.log.error("Database setting up!"); 
    	return new SpringCypherRestGraphDatabase("http://localhost:7474/db/data","neo4j", "neo4j2319");
    }
    

   
   @Bean
   @PostConstruct
    public Neo4jTemplate neo4jTemplate() {
	   return new Neo4jTemplate(graphDatabase, transactionManager);
    /*return new Neo4jTemplate(graphDatabaseService());
     * Was causing connection timeouts at the first load attempt of application
     * */
    }
	

}  
